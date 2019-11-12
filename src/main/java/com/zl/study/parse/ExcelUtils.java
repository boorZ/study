package com.zl.study.parse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 描 述: Excel文件工具类
 * 作 者: ZhouLin
 * 日 期: 创建时间: 2019/11/12
 * 版 本: v1.0
 **/
public class ExcelUtils {

    /** 读取Excel数据 **/
    public Workbook readExcel(String filePath) {
        Workbook workbook = null;
        try {
            InputStream is = new FileInputStream(new File(filePath));
            if (filePath.endsWith("xlsx")) {
                //Excel 2007
                workbook = new XSSFWorkbook(is);
            } else if (filePath.endsWith("xls")) {
                //Excel 2003
                workbook = new HSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /** 获取Sheets数据 **/
    public List<Map<Integer, Map<Integer, Object>>> getExcelData(String filePath, int rowNum){
        // 循环工作表Sheet
        Workbook sheets = readExcel(filePath);
        if (sheets == null) {
            return null;
        }
        // 获取总sheets
        int numberOfSheets = sheets.getNumberOfSheets();
        List<Map<Integer, Map<Integer, Object>>> sheetsDataList = new ArrayList<>();
        for (int numSheet = 0; numSheet < numberOfSheets; numSheet++) {
            Sheet hssfSheet = sheets.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 获取总行数
            int lastRowNum = hssfSheet.getLastRowNum();
            Map<Integer, Map<Integer, Object>> rowMaps = new HashMap<>();
            for (; rowNum <= lastRowNum; rowNum++) {
                // 获取当前行
                Row row = hssfSheet.getRow(rowNum);
                // 获取总列数
                int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                Map<Integer, Object> rowMap = new HashMap<>();
                for (int i = 0; i < physicalNumberOfCells; i++) {
                    rowMap.put(i, row.getCell(i) == null ? "" : row.getCell(i));
                }
                rowMaps.put(rowNum, rowMap);
            }
            sheetsDataList.add(rowMaps);
        }
        try {
            sheets.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheetsDataList;
    }
    @Test
    public void readExcels() {
        List<Map<Integer, Map<Integer, Object>>> excelData = getExcelData("C:\\Users\\Admin\\Desktop\\高频业务文件对照表 2.0.xlsx", 3);
        Map<Object, Object> fullData = new LinkedHashMap<>();
        List<Map<Object, Object>> sonList = new ArrayList<>();
        Map<Object, Object> sonData;

        // 解析成树结构
        Object pName = "";
        Map<Integer, Map<Integer, Object>> integerMapMap = excelData.get(0);
        for (int i = 0; i < integerMapMap.size(); i++) {
            Map<Integer, Object> value = integerMapMap.get(i+3);
            Object name = value.get(0);
            if (!name.toString().equals("")) {
                pName = name;
            }
            sonData = new HashMap<>();
            sonData.put("pName", pName);
            sonData.put("name", value.get(1));
            sonData.put("url", value.get(2));
            sonList.add(sonData);
        }
        Map<Object, List<Map<Object, Object>>> pName1 =
                sonList.stream().collect(Collectors.groupingBy(da -> da.get("pName")));
        System.out.println();
    }
}
