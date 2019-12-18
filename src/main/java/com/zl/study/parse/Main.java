package com.zl.study.parse;

import org.junit.Test;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 周林
 * @Description 解析入口
 * @email prometheus@noask-ai.com
 * @date 2019/11/18 10:38
 */
public class Main {


    /** 去除图片底部空白 **/
    @Test
    public void trimImageBottomBlank() {
//        File images = new File("E:\\拆分文档（表证单书）\\表证单书清理\\fullNewImage");
//        String newPath = "E:\\拆分文档（表证单书）\\表证单书清理\\fullNewTrimBottomBlankImage";
//        File[] files = images.listFiles();
//        for (File file : files) {
//            File newPathFile = new File(newPath + "\\" + file.getName());
//            ImageUtils imageUtils = new ImageUtils(file);
//            imageUtils.trimFourSidesBlank(newPathFile, "png", 0, 0, 0, 0);
//        }


//        File sourceFile = new File("C:\\Users\\Card\\Desktop\\0cae6186-9519-43d8-a669-380d4107a72c.jpg");
//        File targetFile = new File("C:\\Users\\Card\\Desktop\\1.jpg");
//        ImageUtils imageUtils = new ImageUtils(sourceFile);
//        imageUtils.trimFourSidesBlank(targetFile, "png", 0, 0, 0, 0);

        //开线程
        ExecutorService threadPool = Executors.newScheduledThreadPool(10);
        TrimFourSidesBlank trimFourSidesBlank = new TrimFourSidesBlank();
        threadPool.submit(trimFourSidesBlank);
        threadPool.shutdown();
    }

    static class TrimFourSidesBlank implements Runnable {
        private File images = new File("C:\\Users\\Card\\Desktop\\form");
        private String newPath = "C:\\Users\\Card\\Desktop\\newForm";

        @Override
        public void run() {
            File[] files = images.listFiles();
            int length = files.length;
//            File newPathFile = new File(newPath + "\\" + file.getName());
//            ImageUtils imageUtils = new ImageUtils(file);
//            imageUtils.trimFourSidesBlank(newPathFile, "png", 0, 0, 0, 0);
            synchronized (this) {
                if (length > 0) {
                    System.out.println("长度：" + length);
                    length--;
                }
            }
        }
    }
}
