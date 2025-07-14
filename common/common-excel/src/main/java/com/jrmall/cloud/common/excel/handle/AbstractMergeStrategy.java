package com.jrmall.cloud.common.excel.handle;

import com.alibaba.excel.write.handler.CellWriteHandler;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * 合并单元格抽象类
 *
 * @author: Dao-yang.
 * @date: Created in 2025/7/3 16:08
 */
public abstract class AbstractMergeStrategy implements CellWriteHandler {

    /**
     * excel 最大行索引
     */
    public final static int EXCEL_LAST_INDEX = 1048575;

    /**
     * 默认合并起始行
     */
    public final static int DEFAULT_START_ROW_INDEX = 0;

    public abstract void merge(Sheet sheet, Cell cell);

    /**
     * 获取单元格值
     */
    public Object getCellValue(Cell cell) {
        return cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : cell.getNumericCellValue();
    }

    /**
     * 解除已合并的单元格
     */
    public void removeCellRangeAddress(Sheet sheet, int rowIndex, int columnIndex) {
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        for (int i = 0; i < mergedRegions.size(); i++) {
            CellRangeAddress cellAddresses = mergedRegions.get(i);
            // 判断上一行单元格是否已经被合并，是则先移出原有的合并单元，再重新添加合并单元
            if (cellAddresses.isInRange(rowIndex, columnIndex)) {
                sheet.removeMergedRegion(i);
                break;
            }
        }
    }
}
