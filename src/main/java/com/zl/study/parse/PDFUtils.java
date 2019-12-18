package com.zl.study.parse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 描 述: PDF文件工具类
 * 作 者: ZhouLin
 * 日 期: 创建时间: 2019/11/12
 * 版 本: v1.0
 **/
public class PDFUtils {
    public void test(){
        String filePath = "F://spring揭秘(完整).pdf";
        List<String> imageList = pdfToImagePath(filePath);
        Iterator<String> iterator = imageList.iterator();
        while(iterator.hasNext()){

            System.out.println(iterator.next());
        }
//        pdfToImage(filePath);
    }

    /**
     * 将PDF按页数每页转换成一个jpg图片
     * @param filePath
     * @return
     */
    public static List<String> pdfToImagePath(String filePath){
        List<String> list = new ArrayList<>();
        //获取去除后缀的文件路径
        String fileDirectory = filePath.substring(0,filePath.lastIndexOf("."));

        String imagePath;
        File file = new File(filePath);
        try {
            File f = new File(fileDirectory);
            if(!f.exists()){
                f.mkdir();
            }
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for(int i=0; i<pageCount; i++){
                // 方式1,第二个参数是设置缩放比(即像素)
                 BufferedImage image = renderer.renderImageWithDPI(i, 480);
                // 方式2,第二个参数是设置缩放比(即像素)
               // BufferedImage image = renderer.renderImage(i, 1.25f);  //第二个参数越大生成图片分辨率越高，转换时间也就越长
                imagePath = fileDirectory + "/"+i + ".jpg";
                ImageIO.write(image, "PNG", new File(imagePath));
                list.add(imagePath);
            }
            //关闭文件,不然该pdf文件会一直被占用。
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @Description pdf转成一张图片
     * @created 2019年4月19日 下午1:54:13
     * @param pdfFile
     * @param outpath
     */
    public static void pdf2multiImage(String pdfFile, String outpath) {
        try {
            InputStream is = new FileInputStream(pdfFile);
            PDDocument pdf = PDDocument.load(is);
            int actSize  = pdf.getNumberOfPages();
            List<BufferedImage> piclist = new ArrayList<BufferedImage>();
            for (int i = 0; i < actSize; i++) {
                BufferedImage  image = new PDFRenderer(pdf).renderImageWithDPI(i,480, ImageType.RGB);
                piclist.add(image);
            }
            yPic(piclist, outpath);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将宽度相同的图片，竖向追加在一起 ##注意：宽度必须相同
     * @param piclist  文件流数组
     * @param outPath  输出路径
     */
    public static void yPic(List<BufferedImage> piclist, String outPath) {// 纵向处理图片
        if (piclist == null || piclist.size() <= 0) {
            System.out.println("图片数组为空!");
            return;
        }
        try {
            int height = 0, // 总高度
                    width = 0, // 总宽度
                    _height = 0, // 临时的高度 , 或保存偏移高度
                    __height = 0, // 临时的高度，主要保存每个高度
                    picNum = piclist.size();// 图片的数量
            int[] heightArray = new int[picNum]; // 保存每个文件的高度
            BufferedImage buffer = null; // 保存图片流
            List<int[]> imgRGB = new ArrayList<int[]>(); // 保存所有的图片的RGB
            int[] _imgRGB; // 保存一张图片中的RGB数据
            for (int i = 0; i < picNum; i++) {
                buffer = piclist.get(i);
                heightArray[i] = _height = buffer.getHeight();// 图片高度
                if (i == 0) {
                    width = buffer.getWidth();// 图片宽度
                }
                height += _height; // 获取总高度
                _imgRGB = new int[width * _height];// 从图片中读取RGB
                _imgRGB = buffer.getRGB(0, 0, width, _height, _imgRGB, 0, width);
                imgRGB.add(_imgRGB);
            }
            _height = 0; // 设置偏移高度为0
            // 生成新图片
            BufferedImage imageResult = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < picNum; i++) {
                __height = heightArray[i];
                if (i != 0) _height += __height; // 计算偏移高度
                imageResult.setRGB(0, _height, width, __height, imgRGB.get(i), 0, width); // 写入流中
            }
            File outFile = new File(outPath);
            ImageIO.write(imageResult, "jpg", outFile);// 写图片
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        pdf2multiImage("E:\\拆分文档（表证单书）\\问题表单解决\\pdf\\A03055《非居民纳税人税收居民身份信息报告表（企业适用）》.pdf",
                "C:\\Users\\Card\\Desktop\\4.jpg");
        //pdfToImagePath("D:\\不问科技\\开发\\图片处理\\A10095.pdf");
    }
}
