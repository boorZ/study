package com.zl.study.basics.multithreading;

/**
 * @author 周林
 * @Description 银行卡负责存钱
 * @email prometheus@noask-ai.com
 * @date 2019/12/9 15:35
 */
public class Card implements Runnable {
    private String name;
    private Account account = new Account();

    public Card(String name, Account account) {
        this.account = account;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            account.addAccount(name, 100);
        }
    }
}
