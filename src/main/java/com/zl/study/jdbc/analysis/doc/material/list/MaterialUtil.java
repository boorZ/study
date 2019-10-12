package com.zl.study.jdbc.analysis.doc.material.list;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author 周林
 * @Description Poi工具类
 * @email prometheus@noask-ai.com
 * @date 2019/10/11
 */
public class MaterialUtil {

    public static void main(String[] args) {

        List<MaterialBean> allSonFile = getAllSonFile("E:\\拆分文档");
        String filename = "【相关资料清单】.xls";
        for (MaterialBean materialBean : allSonFile) {
            String filepath = materialBean.getDoc().getPath()+"\\";
            createExcel(filepath, filename, materialBean);
        }

//        MaterialBean materialBean = readWordxMaterialList("E:\\拆分文档 - 副本\\4法律追责与救济事项\\" +
//                "4.4相互协商程序\\4.4.2特别纳税调整相互协商程序申请.docx");
    }

    private static List<MaterialBean> getAllSonFile(String path) {
        List<MaterialBean> materialBeanList = new ArrayList<>();
        // 获得当前文件夹对象
        File file = new File(path);
        List<File> fileList = new ArrayList<>();
        // 获得当前文件夹下文件夹List
        getSonFileList(file, fileList);
        fileList = fileList.stream().distinct().collect(Collectors.toList());

        for (File doc : fileList) {
            MaterialBean materialBean = readWordxMaterialList(doc + ".docx");
            if (materialBean == null) {
                continue;
            }
            materialBean.setDoc(doc);
            materialBeanList.add(materialBean);
        }
        return materialBeanList;
    }

    private static void getSonFileList(File file, List<File> fileList) {
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File sonFile : files) {
            if (sonFile.getName().split("【").length == 2) {
                break;
            }
            File[] filesEnd = sonFile.listFiles();
            if (filesEnd != null && filesEnd.length > 1) {
                for (File fileEnd : filesEnd) {
                    // 找到符号【
                    if (fileEnd.getName().contains("【")) {
                        // 获取文件的父级的父级目录
                        File parentFile = fileEnd.getParentFile();
//                        System.out.println(parentFile);
                        fileList.add(parentFile);
                        break;
                    }
                }
            }
            if (!sonFile.getName().contains(".docx")) {
                getSonFileList(sonFile, fileList);
            }
        }
    }

    private static MaterialBean readWordxMaterialList(String filePath) {
        MaterialBean materialBean = new MaterialBean();
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            // 得到word文档的信息
            XWPFDocument docx = new XWPFDocument(fis);
            // 得到word中的表格
            Iterator<XWPFTable> tablesIterator = docx.getTablesIterator();
            // 设置需要读取的表格  set是设置需要读取的第几个表格，total是文件中表格的总数
            while (tablesIterator.hasNext()) {
                XWPFTable table = tablesIterator.next();
                // 获取标题 想要的列名位置
                Integer columnIndex = null;
                List<XWPFTableCell> tableCells = table.getRow(0).getTableCells();
                for (int i = 0; i < tableCells.size(); i++) {
                    String text = tableCells.get(i).getText();
                    Pattern pattern = Pattern.compile("[名称 资料名称]");
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.find()) {
                        columnIndex = i;
                        materialBean.setMaterialName(tableCells.get(i).getText());
                    }
                }
                if (columnIndex == null) {
                    System.out.println("没有资料清单或没有资料名称");
                    return null;
                }
                // 读取每一行数据
                List<String> materialContentList = new ArrayList<>();
                for (int i = 1; i < table.getNumberOfRows(); i++) {
                    XWPFTableRow row = table.getRow(i);
                    List<XWPFTableCell> cells = row.getTableCells();
                    materialContentList.add(cells.get(columnIndex).getText());
                }
                materialBean.setMaterialContent(materialContentList);
            }

            docx.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return materialBean;
    }

    /**
     * 生成Excel 并放到指定位置
     *
     * @param filepath  文件路径(要绝对路径)
     * @param filename  文件名称 (如: demo.xls  记得加.xls哦)
     * @param titlelist 标题名称list
     * @param zdlist    字段list
     * @param datalist  数据list (这里也可以改成List<Map<String,String>>  格式的数据)
     * @return 是否正常生成
     * @author: 2018年11月24日 上午11:40:39
     * (titlelist  和  zdlist  顺序要一直, 要一一对应)
     */
    private static void createExcel(String filepath, String filename, MaterialBean materialBean) {
        try {
            // 创建HSSFWorkbook对象(excel的文档对象)
            HSSFWorkbook wb = new HSSFWorkbook();
            // 建立新的sheet对象（excel的表单）
            HSSFSheet sheet = wb.createSheet("资料清单");
            // 设置列宽
            sheet.setColumnWidth(0, 256 * 40);
            sheet.setColumnWidth(1, 256 * 20);
            sheet.setColumnWidth(2, 256 * 50);

            // 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
            HSSFRow rowOne = sheet.createRow(0);
            // 设置样式
            HSSFCellStyle titleStyle = wb.createCellStyle();
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//            titleStyle.setFillForegroundColor(HSSFColor.LIME.index);
            // 设置单元格背景颜色
            titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            HSSFFont font = wb.createFont();
            // 粗体
            font.setBold(true);
            font.setFontHeightInPoints((short)16);
            titleStyle.setFont(font);

            // 添加表头
            HSSFCell cell1 = rowOne.createCell(0);
            cell1.setCellStyle(titleStyle);
            cell1.setCellValue("文件名");

            HSSFCell cell2 = rowOne.createCell(1);
            cell2.setCellStyle(titleStyle);
            cell2.setCellValue("资料清单名称");

            HSSFCell cell3 = rowOne.createCell(2);
            cell3.setCellStyle(titleStyle);
            cell3.setCellValue("资料清单内容");


            // 添加表中内容
            List<String> materialContent = materialBean.getMaterialContent();
            if (materialContent == null) {
                return;
            }
            for (int row = 0; row < materialContent.size(); row++) {
                // 创建新行 数据从第二行开始
                HSSFRow newRow = sheet.createRow( row + 1);

                //数据从第一列开始 创建单元格并放入数据
                newRow.createCell(0).setCellValue(materialBean.getDoc().getName());
                newRow.createCell(1).setCellValue(materialBean.getMaterialName());
                newRow.createCell(2).setCellValue(materialContent.get(row));
            }

            // 判断是否存在目录. 不存在则创建
            isChartPathExist(filepath);
            // 输出Excel文件1
            FileOutputStream output = new FileOutputStream(filepath + filename);
            // 写入磁盘
            wb.write(output);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断文件夹是否存在，如果不存在则新建
     *
     * @param dirPath 文件夹路径
     */
    private static void isChartPathExist(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

}
