package com.jrmall.cloud.common.excel.listener;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson2.JSON;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Getter
@Slf4j
public class EasyExcelListener<T> extends AnalysisEventListener<T> {

    /**
     * 解析出来的数据
     */
    private final List<T> dataList = new ArrayList<>();

    /**
     * 解析失败的数据
     */
    private final List<ErrorData> errorDataList = new ArrayList<>();

    /**
     * 通过 AnalysisContext 对象还可以获取当前 sheet，当前行等数据
     */
    @Override
    public void invoke(T object, AnalysisContext context) {
        if (!checkObjAllFieldsIsNull(object)) {
            log.debug("读取到一行数据：{}", JSON.toJSONString(object));
            dataList.add(object);
        } else {
            log.warn("读取到的数据为null");
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("解析数据完成，共{}条数据", dataList.size());
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException convertException = (ExcelDataConvertException)exception;
            CellData<?> cellData = convertException.getCellData();
            log.error("第{}行，第{}列解析异常，数据为:{}", convertException.getRowIndex(), convertException.getColumnIndex(), cellData.getStringValue());
            addToErrDataList(exception, convertException, cellData);
        }
    }

    private void addToErrDataList(Exception exception, ExcelDataConvertException convertException, CellData<?> cellData) {
        errorDataList
            .add(new ErrorData(convertException.getRowIndex(), convertException.getColumnIndex(), cellData.getStringValue(), exception.getMessage()));
    }

    private static final String SERIAL_VERSION_UID = "serialVersionUID";

    /**
     * 判断对象中属性值是否全为空
     */
    private static boolean checkObjAllFieldsIsNull(Object object) {
        if (null == object) {
            return true;
        }
        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                // 只校验带ExcelProperty注解的属性
                ExcelProperty property = f.getAnnotation(ExcelProperty.class);
                if (property == null || SERIAL_VERSION_UID.equals(f.getName())) {
                    continue;
                }
                if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
            // do something
        }
        return true;
    }

    @Data
    public static class ErrorData {
        private int rowIndex;
        private int columnIndex;
        private String errorData;
        private String errorMsg;

        public ErrorData(int rowIndex, int columnIndex, String errorData, String errorMsg) {
            this.columnIndex = columnIndex;
            this.errorData = errorData;
            this.errorMsg = errorMsg;
            this.rowIndex = rowIndex;
        }

        @Override
        public String toString() {
            return String.format("第%d行,第%d列解析异常,异常数据:%s,异常信息:%s", rowIndex, columnIndex, errorData, errorMsg);
        }
    }
}