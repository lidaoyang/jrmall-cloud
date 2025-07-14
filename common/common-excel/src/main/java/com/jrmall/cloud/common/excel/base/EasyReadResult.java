package com.jrmall.cloud.common.excel.base;

import com.jrmall.cloud.common.excel.listener.EasyExcelListener;
import lombok.Data;

import java.util.List;

/**
 * 表格读取结果对象
 *
 * @author: Dao-yang.
 * @date: Created in 2025/1/17 15:47
 */
@Data
public class EasyReadResult<T> {

    private List<T> dataList;

    private List<EasyExcelListener.ErrorData> errorDataList;

    public EasyReadResult(List<T> dataList, List<EasyExcelListener.ErrorData> errorDataList) {
        this.dataList = dataList;
        this.errorDataList = errorDataList;
    }
}
