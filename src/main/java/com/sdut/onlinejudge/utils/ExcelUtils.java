package com.sdut.onlinejudge.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 * @Author: Devhui
 * @Date: 2020/3/21 14:05
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */
public class ExcelUtils {

    public static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell.getCellTypeEnum() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }
}
