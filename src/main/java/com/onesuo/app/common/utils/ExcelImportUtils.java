package com.onesuo.app.common.utils;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExcelImportUtils {
    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    //@描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * 验证EXCEL文件
     * @param filePath
     * @return
     */
    public static boolean validateExcel(String filePath){
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))){
            return false;
        }
        return true;
    }

    /**
     * 校验当前行是否有效，规则是当前行每列都为空时则代码无效行
     * @param row
     * @param startcell
     * @param sumcell
     * @return
     */
    public static boolean isBlankRow(Row row, int startcell, int sumcell) {
        if (row == null)
            return false;
        boolean result = false;
        String value = "";
        for (int i = startcell; i < sumcell; i++) {
            Cell cell = row.getCell(i);
            if (cell != null && !cell.equals("")) {
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                value = value + cell.getStringCellValue();
            }
        }
        if (!value.equals("")) {
            result = true;
        }
        return result;
    }
}