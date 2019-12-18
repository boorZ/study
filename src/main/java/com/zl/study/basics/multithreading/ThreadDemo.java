package com.zl.study.basics.multithreading;

/**
 * @author 周林
 * @Description
 * @email prometheus@noask-ai.com
 * @date 2019/12/9 15:38
 */
public class ThreadDemo {

    public static void main(String[] args) {
        // 开个银行帐号
        Account account = new Account();
        // 开银行帐号之后银行给张银行卡
        Card card = new Card("card", account);
        // 开银行帐号之后银行给张存折
        Paper paper = new Paper("存折", account);

        Thread thread1 = new Thread(card);
        Thread thread2 = new Thread(paper);

        thread1.start();
        thread2.start();
    }
}
