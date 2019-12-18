package com.zl.study;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;
/**
 * @author 周林
 * @Description
 * @email prometheus@noask-ai.com
 * @date 2019/7/25
 */
public class XlsxTest {
    public static void main(String[] args) {
        Workbook wb;
        Sheet sheet;
        Row row;
        List<Map<String,String>> list = null;
        String cellData;
//        String filePath = "C:\\Users\\Card\\Desktop\\导入模板验证.xlsx";
        String filePath = "C:\\Users\\Card\\Desktop\\test.xlsx";
        String[] columns = {"图谱名称", "标签", "节点名称", "文号", "文件编号", "发布日期", "文件有效期止", "税种"};
        wb = readExcel(filePath);
        if(wb != null){
            //用来存放表中数据
            list = new ArrayList<>();
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            for (int i = 1; i<rownum; i++) {
                Map<String,String> map = new LinkedHashMap<>();
                row = sheet.getRow(i);
                if(row !=null){
                    for (int j=0;j<colnum;j++){
                        if (Objects.equals("图谱名称", columns[j])) {
                            Cell cell = row.getCell(j);
                            String string = cell.getRichStringCellValue().getString();
                            String[] split = "\u2014\u2014".split(string);
                            cell.setCellValue(split[1]);
                        }
                        cellData = getCellFormatValue(row.getCell(j)).toString();
                        map.put(columns[j], cellData);
                    }
                }else{
                    break;
                }
                list.add(map);
            }
        }
        //遍历解析出来的list
        if (list != null && list.size() > 0) {
            for (Map<String, String> map : list) {
                for (Entry<String, String> entry : map.entrySet()) {
//                    System.out.print(entry.getKey() + ":" + entry.getValue() + ",");
                    System.out.println(entry.getValue());
                }
                System.out.println("================");
            }
        }

    }
    //读取excel
    private static Workbook readExcel(String filePath){
        if(filePath==null){
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is;
        try {
            is = new FileInputStream(filePath);
            if(Objects.equals(".xls", extString)){
                return new HSSFWorkbook(is);
            }else if(Objects.equals(".xlsx", extString)){
                return new XSSFWorkbook(is);
            }else{
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取单元格格式值
     * @param cell 单元格
     * @return 单元格格式值
     */
    private static Object getCellFormatValue(Cell cell){
        Object cellValue;
        if(cell!=null){
            //判断cell类型
            switch(cell.getCellTypeEnum()){
                // 公式型
//                case FORMULA
                // 数值型
                case NUMERIC:{
                    if (DateUtil.isCellDateFormatted(cell)){
                        cellValue = cell.getDateCellValue();
                        return cellValue;
                    }
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                // 字符串型
                case STRING:{
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        }else{
            cellValue = "";
        }
        return cellValue;
    }
}
