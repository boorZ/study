package com.zl.study.parse;

import org.junit.Test;

import java.io.File;
import java.util.concurrent.*;

/**
 * @author 周林
 * @Description 解析入口
 * @email prometheus@noask-ai.com
 * @date 2019/11/18 10:38
 */
public class Main {
    private File images = new File("C:\\Users\\Admin\\Desktop\\form");

    /**
     * 去除图片底部空白
     **/
    @Test
    public void trimImageBottomBlank() {
        ExecutorService threadPool = Executors.newScheduledThreadPool(1);
        File[] files = images.listFiles();
        if (files == null) {
            System.out.println("该目录下没有子目录！！");
            return;
        }
        int count = 0;
        for (File file : files) {
            String newPath = "C:\\Users\\Admin\\Desktop\\newForm";
            TrimFourSidesBlank trimFourSidesBlank = new TrimFourSidesBlank(file, newPath);
            threadPool.submit(trimFourSidesBlank);
            count ++;
            if (count > 0) {
                System.out.println(count);
            }
        }
        threadPool.shutdown();
    }
    static class TrimFourSidesBlank implements Runnable {
        private File sourceFile;
        private String targetFilePath;

        TrimFourSidesBlank(File sourceFile, String targetFilePath) {
            this.sourceFile = sourceFile;
            this.targetFilePath = targetFilePath;
        }

        @Override
        public void run() {
            synchronized (this) {
                try {
                    System.out.println(Thread.currentThread().getName() + "---进入线程");
                    File newPathFile = new File(targetFilePath + "\\" + sourceFile.getName());
                    ImageUtils imageUtils = new ImageUtils(sourceFile);
                    Thread.sleep(1000);
                    imageUtils.trimFourSidesBlank(newPathFile, "png", 0, 0, 0, 0);
                    System.out.println("结束线程");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error");
                } finally {
                    System.out.println("OK!!!");
                }
            }

        }
    }

    @Test
    public void trimImageBottomBlankCallable() {
        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        File[] files = images.listFiles();
        if (files == null) {
            System.out.println("该目录下没有子目录！！");
            return;
        }
        for (File file : files) {
            File newPathFile = new File("C:\\\\Users\\\\Admin\\\\Desktop\\\\newForm\\" + file.getName());
            ImageUtils imageUtils = new ImageUtils(file);
            imageUtils.trimFourSidesBlank(newPathFile, "png", 0, 0, 0, 0);
//            String newPath = "C:\\Users\\Admin\\Desktop\\newForm";
//            TrimFourSidesBlankCallable trimFourSidesBlank = new TrimFourSidesBlankCallable(file, newPath);
//            Future<Boolean> submit = threadPool.submit(trimFourSidesBlank);
//            try {
//                if (submit.get()) {
//
//                }
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            System.out.println(file.getPath());
        }
    }
    static class TrimFourSidesBlankCallable implements Callable<Boolean> {
        private File sourceFile;
        private String targetFilePath;

        TrimFourSidesBlankCallable(File sourceFile, String targetFilePath) {
            this.sourceFile = sourceFile;
            this.targetFilePath = targetFilePath;
        }
        @Override
        public Boolean call() {
            synchronized (this) {
                try {
                    System.out.println(Thread.currentThread().getName() + "---进入线程");
                    File newPathFile = new File(targetFilePath + "\\" + sourceFile.getName());
                    ImageUtils imageUtils = new ImageUtils(sourceFile);
                    imageUtils.trimFourSidesBlank(newPathFile, "png", 0, 0, 0, 0);
                    System.out.println("结束线程");
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error");
                }
            }
            return null;
        }
    }
}


