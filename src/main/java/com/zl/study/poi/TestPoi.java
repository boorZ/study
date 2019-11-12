package com.zl.study.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class TestPoi {
    public String excelHead = "图谱名称\t标签\t 节点名称\t文号\t文件编号\t发布日期\t文件有效期止\t税种\n";
    public static void main(String[] args) {
        Workbook wb;
        Sheet sheet;
        Row row;
        List<Map<String, String>> list = null;
        String cellData = null;
        String filePath = "C:\\Users\\Admin\\Desktop\\test.xlsx";
        String[] columns = {"图谱名称","标签","节点名称","文号","文件编号","发布日期","文件有效期止","税种"};
        wb = readExcel(filePath);
        if (wb != null) {
            // 用来存放表中数据
            list = new ArrayList<>();
            // 获取第一个sheet
            sheet = wb.getSheetAt(0);
            // 获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            // 获取第一行
            row = sheet.getRow(0);
            // 获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            Map<String, String> map = new LinkedHashMap<>();
            for (int i = 1; i < rownum; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    for (int j = 0; j < colnum; j++) {
                        cellData = getCellFormatValue(row.getCell(j)).toString();
                        map.put(columns[j], cellData);
//                        map.put(String.valueOf(j), cellData);
                    }
                } else {
                    break;
                }
                list.add(map);
            }
        }
        // 遍历解析出来的list
        if (list != null && list.size() > 0) {
            for (Map<String, String> map : list) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    System.out.println(entry.getKey() + ":" + entry.getValue());
                }
                System.out.println("-----------------------------------------------");
//                System.out.println(map.keySet());
//                System.out.println();
            }
        }

    }

    // 读取excel
    private static Workbook readExcel(String filePath) {
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is;
        try {
            is = new FileInputStream(filePath);
            if (Objects.equals(extString, ".xls")) {
                return new HSSFWorkbook(is);
            } else if (Objects.equals(extString, ".xlsx")) {
                return new XSSFWorkbook(is);
            } else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object getCellFormatValue(Cell cell) {
        Object cellValue;
        if (cell != null) {
            // 判断cell类型
            switch (cell.getCellTypeEnum()) {
                case NUMERIC: {
                    // 判断cell是否为日期格式
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // 转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    } else {
                        // 数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }

    /**
     * 解析头
     * @param filePath 原始头
     * @return 解析格式
     */
    private static String[] parseExcelHead(String excelHead) {
        String replace = excelHead.replace(" ", "").replace("\n", "");
        return replace.split("\t");
    }
}
