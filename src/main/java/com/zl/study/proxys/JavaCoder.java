package com.zl.study.proxys;

/**
 * 描 述: 码农实现需要
 * 作 者: ZhouLin
 * 日 期: 创建时间: 2019/6/7 0007
 * 版 本: v1.0
 **/
public class JavaCoder implements ICoder {

    // 程序员名称
    private String coderName;

    JavaCoder(String coderName) {
        this.coderName = coderName;
    }

    @Override
    public void implDemands(String demandName) {
        System.out.println("程序员：" + coderName + "实现需求：" + demandName);
    }
}
