package com.zl.study.basics.multithreading;

/**
 * @author 周林
 * @Description 银行账户
 * @email prometheus@noask-ai.com
 * @date 2019/12/9 15:34
 */
public class Account {
    private int count = 0;

    /**
     * 存钱
     *
     * @param money
     */
    public void addAccount(String name, int money) {
        // 存钱
        count += money;
        System.out.println(name + "...存入：" + money + "..." + Thread.currentThread().getName());
        SelectAccount(name);
    }

    /**
     * 取钱
     *
     * @param money
     */
    public void subAccount(String name, int money) {
        // 先判断账户现在的余额是否够取钱金额
        if (count - money < 0) {
            System.out.println("账户余额不足！");
            return;
        }
        // 取钱
        count -= money;
        System.out.println(name + "...取出：" + money + "..." + Thread.currentThread().getName());
        SelectAccount(name);
    }

    /**
     * 查询余额
     */
    public void SelectAccount(String name) {
        System.out.println(name + "...余额：" + count);
    }
}
