package com.zl.study.basics.multithreading;

/**
 * @author 周林
 * @Description 存折负责取钱
 * @email prometheus@noask-ai.com
 * @date 2019/12/9 15:37
 */
public class Paper implements Runnable {
    private String name;
    private Account account = new Account();

    public Paper(String name, Account account) {
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
            account.subAccount(name, 50);
        }

    }
}
