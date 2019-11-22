package com.zl.study.parse;

import org.junit.Test;

import java.io.File;

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
        File sourceFile = new File("C:\\Users\\Administrator\\Desktop\\A01007.jpg");
        File targetFile = new File("C:\\Users\\Administrator\\Desktop\\1.png");
        ImageUtils imageUtils = new ImageUtils(sourceFile);
        imageUtils.trimFourSidesBlank(targetFile, "png", 20, 20);
    }
}
