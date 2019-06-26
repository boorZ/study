package com.zl.study.basics.shifting;

/**
 * 描 述: 学习位移运算符
 * 作 者: ZhouLin
 * 日 期: 创建时间: 2019/5/13
 * 版 本: v1.0
 **/
public class Demo {
    /**
     * 按位与&：两位全为1，结果为1
     *
     * 按位或|：两位有一个为1，结果为1
     *
     * 按位异或^：两位一个为0，一个为1，结果为1
     *
     * 按位取反：0->1，1->0
     */
    public static void main(String[] args) {
//        int a=1>>2;         // 101
//        int b=-1>>2;0
//        int c=1<<2;
//        int d=-1<<2;
//        int e=3>>>2;
//        System.out.println();
//        System.out.println(a);

//        binaryToDecimal1(10);
//        binaryToDecimal2(10);
//        binaryToDecimal3(10);

        binaryToDecimal1(10);
        binaryToDecimal2(10);
        function13(10);
    }

    /**
     * 输入一个十进制数n，每次用n除以2，把余数记下来，再用商去除以2...依次循环，直到商为0结束，把余数倒着依次排列，就构成了转换后的二进制数。
     */
    public static void binaryToDecimal1(int n){
        int t = 0;      //用来记录位数
        int bin = 0;    //用来记录最后的二进制数
        int r = 0;      //用来存储余数
        while(n != 0){
            r = n % 2;
            n = n / 2;
            bin += r * Math.pow(10,t);
            t++;
        }
        System.out.println(bin);
    }

    /**
     * 移位操作
     *      对一个十进制数进行移位操作，即：将最高位的数移至最低位（移31位），除过最低位其余位置清零，
     *      使用& 操作，可以使用和1相与（&），由于1在内存中除过最低位是1，其余31位都是零，然后把这个数按十进制输出；再移次高位，做相同的操作，直到最后一位
     **/
    public static void binaryToDecimal2(int n){
        for(int i = 31;i >= 0; i--) {
//            System.out.println((n >>> i) + "=======================");
            System.out.print(n >>> i & 1);
        }
        System.out.println();
    }

    /**
     * 调用API函数
     */
    public static void function13(int n){
        String result = Integer.toBinaryString(n);
        System.out.println(result);
    }
}
