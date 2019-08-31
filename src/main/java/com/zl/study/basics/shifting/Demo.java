package com.zl.study.basics.shifting;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 描 述: 学习位移运算符
 * 作 者: ZhouLin
 * 日 期: 创建时间: 2019/5/13
 * 版 本: v1.0
 **/
public class Demo {
    /**
     *  运算符      含义
     *  <<          左移运算符，将运算符左边的对象向左移动运算符右边指定的位数（在低位补0）
     *  >>          "有符号"右移运算 符，将运算符左边的对象向右移动运算符右边指定的位数。使用符号扩展机制，也就是说，如果值为正，则在高位补0，如果值为负，则在高位补1.
     *  >>>         "无符号"右移运算 符，将运算符左边的对象向右移动运算符右边指定的位数。采用0扩展机制，也就是说，无论值的正负，都在高位补0
     */

    @Test
    public void leftOperator () {
        /*
         * 左移运算符用“<<”表示，是将运算符左边的对象，向左移动运算符右边指定的位数，并且在低位补零。其实，向左移n 位，就相当于乘上2 的n 次方
         */
        int a = 2, b = 2, c;
        c = a << b;
        System.out.println("a 移位的结果是"+c);
    }
    @Test
    public void unsignedRightOperator () {
        /*
         * 右移运算符无符号用“>>>”表示，是将运算符左边的对象向右移动运算符右边指定的位数，并且在高位补0，其实右移n 位，就相当于除上2 的n 次方
         */
        int a = 16, b = 2, c;
        c = a >>> b;
        System.out.println("a 移位的结果是"+c);
    }

    @Test
    public void list () {
        List<String> list = new ArrayList<>();
        list.add("《1》");
        list.add("《2》");
        list.add("《3》");
//        list.
    }

}
