package com.zl.study.proxys;

/**
 * 描 述: 请描述功能
 * 作 者: ZhouLin
 * 日 期: 创建时间: 2019/6/7 0007
 * 版 本: v1.0
 **/
public class Customer {
    public static void main(String[] args) {
        // 程序员
        ICoder commodity = new JavaCoder("小周");
        // 产品经理
        ProductManagerProxy supermarketProxy = new ProductManagerProxy(commodity);
        // 要实现的需求
        supermarketProxy.implDemands("add根据用户心情改变天气！");
    }
}
