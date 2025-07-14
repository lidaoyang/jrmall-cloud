package com.jrmall.cloud.common.excel.handle;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Excel导出单元格全量合并策略： - 该策略会左右，上下合并单元格
 *
 * @author: Dao-yang.
 * @date: Created in 2025/7/3 16:14
 */
public class FullCellMergeStrategy extends AbstractMergeStrategy {

    /**
     * 合并起始行索引
     */
    private final int mergeStartRowIndex;

    /**
     * 合并结束行索引
     */
    private final int mergeEndRowIndex;

    /**
     * 已合并的记录： - key: 对应行索引 - value： 对应该行已合并过的单元格
     */
    private final Map<Integer, List<int[]>> hadMergeRecord = MapUtil.newHashMap();

    public FullCellMergeStrategy() {
        this(DEFAULT_START_ROW_INDEX, EXCEL_LAST_INDEX);
    }

    public FullCellMergeStrategy(int mergeStartRowIndex) {
        this(mergeStartRowIndex, EXCEL_LAST_INDEX);
    }

    public FullCellMergeStrategy(int mergeStartRowIndex, int mergeEndRowIndex) {
        this.mergeStartRowIndex = mergeStartRowIndex;
        this.mergeEndRowIndex = mergeEndRowIndex;
    }

    /**
     * 在单元上的所有操作完成后调用(可以对单元格进行任何操作)
     */
    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> list, Cell cell,
        Head head, Integer integer, Boolean isHead) {
        // 头不参与合并
        if (isHead)
            return;
        // 如果当前行大于合并起始行则进行合并
        if (cell.getRowIndex() >= mergeStartRowIndex && cell.getRowIndex() <= mergeEndRowIndex) {
            // 合并单元格
            this.merge(writeSheetHolder.getSheet(), cell);
        }
    }

    /**
     * 当前单元格先向左合并，再向上合并
     * 
     * @param sheet 当前sheet
     * @param cell 当前单元格
     */
    public void merge(Sheet sheet, Cell cell) {
        // 当前单元格行、列索引
        int curRowIndex = cell.getRowIndex();
        int curColumnIndex = cell.getColumnIndex();

        // 合并区间
        int startRow = curRowIndex;
        int startCol = curColumnIndex;

        // 当前单元格的值为
        Object curCellValue = this.getCellValue(cell);

        int[] leftMergeColumn = null;
        int removeCurRowCellRange = -1;

        // 偏移量
        int displacement = 0;
        // 先向左进行合并
        while (true) {
            // 向左移动一位
            displacement++;
            // 左边单元格的索引位置
            int leftColumnIndex = curColumnIndex - displacement;
            if (leftColumnIndex < 0) {
                // 左边单元格不存在，表明是该行第一个单元格，跳过合并
                break;
            }
            // 获取左边单元格
            Cell leftCell = sheet.getRow(curRowIndex).getCell(leftColumnIndex);
            // 左边单元格的值
            Object nextCellValue = this.getCellValue(leftCell);
            // 如果相同则，则表明可以和左边的单元格进行合并
            if (Objects.equals(curCellValue, nextCellValue)) {
                // 查看当前行的所有已合并的单元格
                List<int[]> mergeColumns = hadMergeRecord.get(curRowIndex);
                if (CollUtil.isNotEmpty(mergeColumns)) {
                    // 判断左边的单元格是否处于合并状态
                    int[] lastMergeColumn = mergeColumns.get(mergeColumns.size() - 1);
                    if (leftColumnIndex >= lastMergeColumn[0] && leftColumnIndex <= lastMergeColumn[1]) {
                        // 修改单元格的合并范围
                        lastMergeColumn[1] = curColumnIndex;
                        startCol = lastMergeColumn[0];
                        // 移除左边原有的合并单元
                        removeCurRowCellRange = leftColumnIndex;
                        leftMergeColumn = lastMergeColumn;
                    } else {
                        // 左边单元格不在合并范围，则添加合并区间
                        startCol = leftColumnIndex;
                        int[] mergeColumn = {startCol, curColumnIndex};
                        mergeColumns.add(mergeColumn);
                        hadMergeRecord.put(curRowIndex, mergeColumns);
                    }
                } else {
                    // 向左进行合并
                    startCol = leftColumnIndex;
                    // 添加合并区间
                    int[] mergeColumn = {startCol, curColumnIndex};
                    mergeColumns = new ArrayList<>();
                    mergeColumns.add(mergeColumn);
                    hadMergeRecord.put(curRowIndex, mergeColumns);
                }
            } else {
                // 不同则直接跳出循环，合并终止
                break;
            }
        }

        boolean needRemoveCurRowCellRange = true;

        // 重置偏移量
        displacement = 0;
        // 再向上进行合并
        while (true) {
            // 向上移动一位
            displacement++;
            // 上一行的列位置
            int aboveRowIndex = curRowIndex - displacement;
            // 判断上一行是否合理
            if (aboveRowIndex < 0 || aboveRowIndex < mergeStartRowIndex) {
                break;
            }
            // 获取上一个单元格
            Cell aboveCell = sheet.getRow(aboveRowIndex).getCell(curColumnIndex);
            // 上一个单元格的值
            Object aboveCellValue = this.getCellValue(aboveCell);
            // 判断上一个单元格是否能合并
            if (Objects.equals(curCellValue, aboveCellValue)) {
                // 判断上一个单元格是否为合并单元格
                List<int[]> mergeColumns = hadMergeRecord.get(aboveRowIndex);
                if (CollUtil.isNotEmpty(mergeColumns)) {
                    int[] aboveMergeColumn = null;
                    for (int[] mergeColumn : mergeColumns) {
                        if (curColumnIndex >= mergeColumn[0] && curColumnIndex <= mergeColumn[1]) {
                            aboveMergeColumn = mergeColumn;
                            break;
                        }
                    }
                    if (aboveMergeColumn != null) {
                        // 表明上一个单元格为合并单元格，再判断该合并单元格的区间是否与当前一致
                        if (aboveMergeColumn[0] == startCol && aboveMergeColumn[1] == curColumnIndex) {
                            startRow = aboveRowIndex;
                            // 移除原有的单元格
                            this.removeCellRangeAddress(sheet, aboveRowIndex, curColumnIndex);
                        }
                    } else {
                        startRow = aboveRowIndex;
                    }
                } else {
                    startRow = aboveRowIndex;
                    // 移除原有的单元格
                    this.removeCellRangeAddress(sheet, aboveRowIndex, curColumnIndex);
                }
            } else {
                int leftColumnIndex = curColumnIndex - 1;
                if (leftColumnIndex < 0) {
                    break;
                }
                Cell leftAboveCell = sheet.getRow(aboveRowIndex).getCell(curColumnIndex);
                Object leftAboveCellValue = this.getCellValue(leftAboveCell);
                // 判断原左边单元格是否和左上单元格合并
                if (Objects.nonNull(leftMergeColumn) && Objects.equals(curCellValue, leftAboveCellValue)) {
                    // 撤销合并
                    needRemoveCurRowCellRange = false;
                    startCol = curColumnIndex;
                    leftMergeColumn[1] = curColumnIndex - 1;
                }
                break;
            }
        }

        // 判断是否需要删除左边原合并的单元格
        if (removeCurRowCellRange != -1 && needRemoveCurRowCellRange) {
            this.removeCellRangeAddress(sheet, curRowIndex, removeCurRowCellRange);
        }

        if (startRow != curRowIndex || startCol != curColumnIndex) {
            // 添加合并单元格
            CellRangeAddress cellAddresses = new CellRangeAddress(startRow, curRowIndex, startCol, curColumnIndex);
            sheet.addMergedRegion(cellAddresses);
        }
    }
}
