package com.jrmall.cloud.common.excel.handle;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 合并单元格策略：适用于列合并
 *
 * @author: Dao-yang.
 * @date: Created in 2025/7/3 16:11
 */
public class ColumnMergeStrategy extends AbstractMergeStrategy {

    /**
     * 合并起始行索引
     */
    private final int mergeStartRowIndex;

    /**
     * 合并结束行索引
     */
    private final int mergeEndRowIndex;

    /**
     * 待合并的列（如果没有指定，则所有的列都会进行合并）
     */
    private final List<Integer> mergeColumnIndexList;

    /**
     * 待合并的主列
     */
    private List<Integer> mergeMainColumnIndexList = new ArrayList<>();

    /**
     * 待合并的副列
     */
    private List<Integer> mergeDeputyColumnIndexList = new ArrayList<>();

    public ColumnMergeStrategy() {
        this(DEFAULT_START_ROW_INDEX, EXCEL_LAST_INDEX);
    }

    public ColumnMergeStrategy(List<Integer> mergeColumnIndexList) {
        this(DEFAULT_START_ROW_INDEX, EXCEL_LAST_INDEX, mergeColumnIndexList);
    }

    public ColumnMergeStrategy(List<Integer> mergeMainColumnIndexList, List<Integer> mergeDeputyColumnIndexList) {
        if (CollUtil.isEmpty(mergeMainColumnIndexList)) {
            throw new RuntimeException("The main column collection is empty");
        }
        mergeColumnIndexList = new ArrayList<>(mergeMainColumnIndexList);
        if (CollUtil.isNotEmpty(mergeDeputyColumnIndexList)) {
            boolean exitSameIndex = mergeDeputyColumnIndexList.stream().filter(mergeMainColumnIndexList::contains).findAny().orElse(null) != null;
            if (exitSameIndex) {
                throw new RuntimeException("The secondary column collection has the same elements as the main column");
            }
            mergeColumnIndexList.addAll(mergeDeputyColumnIndexList);
        }
        this.mergeMainColumnIndexList = mergeMainColumnIndexList;
        this.mergeDeputyColumnIndexList = mergeDeputyColumnIndexList;
        this.mergeStartRowIndex = DEFAULT_START_ROW_INDEX;
        this.mergeEndRowIndex = EXCEL_LAST_INDEX;
    }

    public ColumnMergeStrategy(int mergeStartRowIndex) {
        this(mergeStartRowIndex, EXCEL_LAST_INDEX);
    }

    public ColumnMergeStrategy(int mergeStartRowIndex, int mergeEndRowIndex) {
        this(mergeStartRowIndex, mergeEndRowIndex, new ArrayList<>());
    }

    public ColumnMergeStrategy(int mergeStartRowIndex, int mergeEndRowIndex, List<Integer> mergeColumnIndexList) {
        this.mergeStartRowIndex = mergeStartRowIndex;
        this.mergeEndRowIndex = mergeEndRowIndex;
        this.mergeColumnIndexList = mergeColumnIndexList;
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> list, Cell cell,
        Head head, Integer integer, Boolean isHead) {
        // 头不参与合并
        if (isHead)
            return;
        // 如果当前行大于合并起始行则进行合并
        if (cell.getRowIndex() >= mergeStartRowIndex && cell.getRowIndex() <= mergeEndRowIndex) {
            // 判断是否是全列合并或者当前列在需要合并列中
            if (CollUtil.isEmpty(mergeColumnIndexList)
                || (CollUtil.isNotEmpty(mergeColumnIndexList) && mergeColumnIndexList.contains(cell.getColumnIndex()))) {
                // 合并单元格
                this.merge(writeSheetHolder.getSheet(), cell);
            }
        }
    }

    public void merge(Sheet sheet, Cell cell) {
        // 当前单元格行、列索引
        int curRowIndex = cell.getRowIndex();
        int curColumnIndex = cell.getColumnIndex();
        // 合并区间
        int startRow = curRowIndex;
        // 当前单元格的值为
        Object curCellValue = this.getCellValue(cell);
        // 偏移量
        int displacement = 0;

        // 向上进行合并
        while (true) {
            // 向上移动一位
            displacement = displacement + 1;
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
                boolean needMerge = true;
                // 判断当前列是否在副列范围内
                if (mergeDeputyColumnIndexList.contains(curColumnIndex)) {
                    // 判断其对应的主列是否与上一行全部相同
                    for (Integer mainColumnIndex : mergeMainColumnIndexList) {
                        Cell mainCell = sheet.getRow(curRowIndex).getCell(mainColumnIndex);
                        Cell aboveMainCell = sheet.getRow(aboveRowIndex).getCell(mainColumnIndex);

                        Object mainCellValue = this.getCellValue(mainCell);
                        Object aboveMainCellValue = this.getCellValue(aboveMainCell);

                        if (!Objects.equals(mainCellValue, aboveMainCellValue)) {
                            needMerge = false;
                            break;
                        }
                    }
                }
                if (needMerge) {
                    startRow = aboveRowIndex;
                    // 移除原有的单元格
                    this.removeCellRangeAddress(sheet, aboveRowIndex, curColumnIndex);
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        if (startRow != curRowIndex) {
            // 添加合并单元格
            CellRangeAddress cellAddresses = new CellRangeAddress(startRow, curRowIndex, curColumnIndex, curColumnIndex);
            sheet.addMergedRegion(cellAddresses);
        }
    }
}
