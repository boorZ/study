package com.zl.study.proxys;

/**
 * 描 述: 产品经理
 * 作 者: ZhouLin
 * 日 期: 创建时间: 2019/6/7 0007
 * 版 本: v1.0
 **/
public class ProductManagerProxy implements ICoder {

    private ICoder coder;

    ProductManagerProxy(ICoder coder) {
        this.coder = coder;
    }
    @Override
    public void implDemands(String demandName) {
        if(demandName.startsWith("add")){
            System.out.println("产品经理拒绝该需求！");
            return;
        }
        coder.implDemands(demandName);
    }
}
