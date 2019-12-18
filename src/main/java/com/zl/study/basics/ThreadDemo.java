package com.zl.study.basics;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadDemo {
    public static void main(String[] args) {
        // t1 t2同时共享同一变量trainCount
        ThreadTrain threadTrain = new ThreadTrain();
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(threadTrain);
        cachedThreadPool.execute(threadTrain);
        cachedThreadPool.execute(threadTrain);
        Thread t1 = new Thread(threadTrain, "窗口1");
//        Thread t2 = new Thread(threadTrain, "窗口2");
//        t1.start();
//        t2.start();
    }

    @Test
    public void main1() {
        // 总共有100张火车票
        int trainCount = 100;
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.submit(new ThreadTrain());
        cachedThreadPool.shutdown();
//        for (int i = 0; i < 10; i++) {
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            cachedThreadPool.execute(() -> {
//                System.out.println(Thread.currentThread().getName() + ",出售第" + 1 + "张票");
//                // 出售火车票
////                if (trainCount[0] > 0) {
////                    System.out.println(Thread.currentThread().getName() + ",出售第" + (100 - trainCount[0] + 1) + "张票");
////                    trainCount[0]--;
////                }
//            });

//        }
    }

}

// 售票窗口
class ThreadTrain implements Runnable {
    // 总共有100张火车票
    private int trainCount = 100;

    public void run() {
        while (trainCount > 0) {
            try {
                // 休眠50秒
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 出售火车票
            synchronized (this) {
                if (trainCount > 0) {
                    System.out.println(Thread.currentThread().getName() + ",出售第" + (100 - trainCount + 1) + "张票");
                    trainCount--;
                }
            }
//            sale();
        }
    }

    // 卖票方法 同步代码块 synchronized 包裹需要线程安全的问题。
    public synchronized void sale() {
        if (trainCount > 0) {
            System.out.println(Thread.currentThread().getName() + ",出售第" + (100 - trainCount + 1) + "张票");
            trainCount--;
        }
    }
}
