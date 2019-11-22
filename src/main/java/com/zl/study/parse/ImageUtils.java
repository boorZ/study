package com.zl.study.parse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 周林
 * @Description 图片工具类
 * @email prometheus@noask-ai.com
 * @date 2019/11/18 10:55
 */
public class ImageUtils {

    private BufferedImage img;

    public ImageUtils(File input) {
        try {
            img = ImageIO.read(input);
        } catch (IOException e) {
            throw new RuntimeException("读取图片失败", e);
        }
    }

    public void trimFourSidesBlank(File targetFile, String imageType, Integer addToopHeight, Integer addBottonmHeight) {
        int width = img.getWidth();
        int height = img.getHeight();
        Integer bottomHeight = getTrimBottomBlankHeight(img);
        Integer topHeight = getTrimTopBlankHeight(img);
        Integer leftWidth = getTrimLeftBlankHeight(img);
        int newHight = height - bottomHeight + addBottonmHeight + addToopHeight;
        int newWidth = width - leftWidth;
        topHeight -= addToopHeight;

        BufferedImage newImg = new BufferedImage(newWidth, newHight, BufferedImage.TYPE_INT_RGB);
        Graphics g = newImg.createGraphics();
        g.drawImage(img, 0, -topHeight, null);
        img = newImg;
        try {
            ImageIO.write(img, imageType, targetFile);
        } catch (IOException e) {
            System.out.println("保存图片失败");
        }
    }

//    public static void trimCommonBlank(File sourceFile, File targetFile, String imageType,
//                                       Integer addTopHeight, Integer addBottonmHeight) {
//        BufferedImage img = null;
//        try {
//            img = ImageIO.read(sourceFile);
//        } catch (IOException e) {
//            System.out.println("读取图片失败");
//        }
//        int width = img.getWidth();
//        int height = img.getHeight();
//        Integer bottomHeight = getTrimBottomBlankHeight(img);
//        Integer y = getTrimTopBlankHeight(img) + addTopHeight;
//        int newHight = height - bottomHeight - y + addBottonmHeight;
//        BufferedImage newImg = new BufferedImage(width, newHight, BufferedImage.TYPE_INT_RGB);
//        Graphics g = newImg.createGraphics();
//        g.drawImage(img, 0, -y, null);
//        img = newImg;
//        try {
//            ImageIO.write(img, imageType, targetFile);
//        } catch (IOException e) {
//            System.out.println("保存图片失败");
//        }
//    }

    /**
     * 获取左空白最低高宽度
     *
     * @param img 图片
     * @return
     */
    private Integer getTrimLeftBlankHeight(BufferedImage img) {
//        int width = img.getWidth();
//        int height = img.getHeight();
//        List<Integer> trimLeftHeightList = new ArrayList<>();
//        for (int j = 0; j < height; j++) {
//            for (int i = 0; i < width; i++) {
//                if (img.getRGB(i, j) != Color.WHITE.getRGB()) {
//                    trimLeftHeightList.add(j);
//                }
//            }
//        }
//        // 获取图片底部最小空白高度
//        if (trimLeftHeightList.size() > 0) {
//            return trimLeftHeightList.stream().min(Integer::compareTo).get();
//        }
        return 0;
    }

    /**
     * 获取顶部空白最低高度
     *
     * @param img 图片
     * @return
     */
    private Integer getTrimTopBlankHeight(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        List<Integer> trimmedTopHeightList = new ArrayList<>();
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (img.getRGB(i, j) != Color.WHITE.getRGB()) {
                    trimmedTopHeightList.add(j);
                }
            }
        }
        // 获取图片底部最小空白高度
        if (trimmedTopHeightList.size() > 0) {
            return trimmedTopHeightList.stream().min(Integer::compareTo).get();
        }
        return 0;
    }

    /**
     * 获取底部空白最低高度
     *
     * @param img 图片
     * @return
     */
    private Integer getTrimBottomBlankHeight(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        List<Integer> trimmedBottomHeightList = new ArrayList<>();
        for (int j = height - 1; j >= 0; j--) {
            for (int i = 0; i < width; i++) {
                // 获取图片底部空白高度
                if (img.getRGB(i, j) != Color.WHITE.getRGB()) {
                    trimmedBottomHeightList.add(height - j);
                }
            }
        }
        // 获取图片底部最小空白高度
        if (trimmedBottomHeightList.size() > 0) {
            return trimmedBottomHeightList.stream().min(Integer::compareTo).get();
        }
        return 0;
    }


    public static BufferedImage mergeImage(boolean isHorizontal, List<BufferedImage> imgs) {
        // 生成新图片
        BufferedImage destImage = null;
        // 计算新图片的长和高
        int allw = 0, allh = 0, allwMax = 0, allhMax = 0;
        // 获取总长、总宽、最长、最宽
        for (int i = 0; i < imgs.size(); i++) {
            BufferedImage img = imgs.get(i);
            allw += img.getWidth();

            if (imgs.size() != i + 1) {
//                    allh += img.getHeight() + 5;
                allh += img.getHeight();
            } else {
                allh += img.getHeight();
            }

            if (img.getWidth() > allwMax) {
                allwMax = img.getWidth();
            }
            if (img.getHeight() > allhMax) {
                allhMax = img.getHeight();
            }
        }
        // 创建新图片
        if (isHorizontal) {
            destImage = new BufferedImage(allw, allhMax, BufferedImage.TYPE_INT_RGB);
        } else {
            destImage = new BufferedImage(allwMax, allh, BufferedImage.TYPE_INT_RGB);
        }
        Graphics2D g2 = (Graphics2D) destImage.getGraphics();
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, allw, allh);
        g2.setPaint(Color.RED);


        int wx = 0, wy = 0;
        for (int i = 0; i < imgs.size(); i++) {
            BufferedImage img = imgs.get(i);
            int w1 = img.getWidth();
            int h1 = img.getHeight();

            int[] ImageArrayOne = new int[w1 * h1];
            ImageArrayOne = img.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1);
            if (isHorizontal) {
                destImage.setRGB(wx, 0, w1, h1, ImageArrayOne, 0, w1);
            } else {
                destImage.setRGB(0, wy, w1, h1, ImageArrayOne, 0, w1);
            }
            wx += w1;
//                wy += h1 + 5;
            wy += h1;
        }
        return destImage;
    }
}
