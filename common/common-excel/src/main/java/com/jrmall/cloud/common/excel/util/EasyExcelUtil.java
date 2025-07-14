package com.jrmall.cloud.common.excel.util;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.jrmall.cloud.common.excel.base.EasyReadResult;
import com.jrmall.cloud.common.excel.handle.AbstractMergeStrategy;
import com.jrmall.cloud.common.excel.listener.EasyExcelListener;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * ExcelUtil
 */
@Slf4j
public class EasyExcelUtil {
    /**
     * 私有化构造方法
     */
    private EasyExcelUtil() {}

    public static <T> List<T> read(String filePath, final Class<?> clazz) {
        File f = new File(filePath);
        try (FileInputStream fis = new FileInputStream(f)) {
            return read(fis, clazz);
        } catch (FileNotFoundException e) {
            log.error("文件{}不存在", filePath, e);
        } catch (IOException e) {
            log.error("文件读取出错", e);
        }

        return null;
    }

    public static <T> List<T> read(InputStream inputStream, final Class<?> clazz) {
        if (inputStream == null) {
            throw new RuntimeException("解析出错了，文件流是null");
        }

        // 有个很重要的点 DataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        EasyExcelListener<T> listener = new EasyExcelListener<>();

        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(inputStream, clazz, listener).sheet().doRead();
        return listener.getDataList();
    }

    public static <T> EasyReadResult<T> read2(MultipartFile file, final Class<?> clazz) {
        EasyExcelListener<T> listener = getEasyExcelListener(file, clazz);
        return new EasyReadResult<>(listener.getDataList(), listener.getErrorDataList());
    }

    public static <T> List<T> read(MultipartFile file, final Class<?> clazz) {
        EasyExcelListener<T> listener = getEasyExcelListener(file, clazz);
        return listener.getDataList();
    }

    private static <T> EasyExcelListener<T> getEasyExcelListener(MultipartFile file, Class<?> clazz) {
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            log.info("文件转输入流解析出错", e);
            throw new RuntimeException(e);
        } 
        // 有个很重要的点 DataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        EasyExcelListener<T> listener = new EasyExcelListener<>();

        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(inputStream, clazz, listener).sheet().doRead();
        return listener;
    }

    public static <T> List<T> read(MultipartFile file, final Class<?> clazz, EasyExcelListener<T> listener) {
        String filename = file.getOriginalFilename();
        log.info("解析Excel fileName:{}", filename);
        if (StringUtils.isBlank(filename)) {
            throw new RuntimeException("文件格式错误！");
        }
        if (!filename.toLowerCase().endsWith(ExcelTypeEnum.XLS.getValue()) && !filename.toLowerCase().endsWith(ExcelTypeEnum.XLSX.getValue())) {
            throw new RuntimeException("文件格式错误！");
        }
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            log.info("文件转输入流解析出错");
            throw new RuntimeException(e);
        }
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(inputStream, clazz, listener).sheet().doRead();
        return listener.getDataList();
    }

    public static void write(String outFile, List<?> list) {
        Class<?> clazz = list.get(0).getClass();
        // 新版本会自动关闭流，不需要自己操作
        EasyExcel.write(outFile, clazz).sheet().doWrite(list);
    }

    public static void write(String outFile, List<?> list, String sheetName) {
        Class<?> clazz = list.get(0).getClass();
        // 新版本会自动关闭流，不需要自己操作
        EasyExcel.write(outFile, clazz).sheet(sheetName).doWrite(list);
    }

    public static void write(OutputStream outputStream, List<?> list, String sheetName) {
        Class<?> clazz = list.get(0).getClass();
        // 新版本会自动关闭流，不需要自己操作
        // sheetName为sheet的名字，默认写第一个sheet
        EasyExcel.write(outputStream, clazz).sheet(sheetName).doWrite(list);
    }

    /**
     * 文件下载（失败了会返回一个有部分数据的Excel），用于直接把excel返回到浏览器下载
     */
    public static void download(HttpServletResponse response, List<?> list, String sheetName) {
        download(response, list, sheetName, null);
    }

    /**
     * 文件下载（失败了会返回一个有部分数据的Excel），用于直接把excel返回到浏览器下载
     */
    public static void download(HttpServletResponse response, List<?> list, String sheetName, AbstractMergeStrategy mergeStrategy) {
        if (CollUtil.isEmpty(list)) {
            log.error("下载数据为空,sheetName:{}", sheetName);
            throw new RuntimeException("下载数据为空");
        }
        try {
            Class<?> clazz = list.get(0).getClass();

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode(sheetName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            ExcelWriterBuilder writerBuilder = EasyExcel.write(response.getOutputStream(), clazz);
            if (mergeStrategy != null) {
                // 合并单元格策略
                writerBuilder.registerWriteHandler(mergeStrategy);
            }
            writerBuilder.sheet(sheetName).doWrite(list);
        } catch (IOException e) {
            log.error("文件下载异常,sheetName:{}", sheetName,e);
            throw new RuntimeException("下载异常");
        }
    }
}
