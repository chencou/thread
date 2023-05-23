package org.example.threadLock.dome1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 案例测试 使用类NoSafePlus
 * 测试 ++是否是线程安装
 */
public class ThreadDome {
    //10个线程
    private  static  Integer MAX_TREAD=10;
    //线程自增1000
    private  static  Integer MAX_TURN=1000;


    public static void main(String[] args) {
        CountDownLatch latch =new CountDownLatch(MAX_TURN);
        NoSafePlus safePlus=new NoSafePlus();
        for (int i = 0; i < MAX_TREAD; i++) {
            new Thread(()->{
                for (int j = 0; j < MAX_TURN; j++) {
                    safePlus.selfPlus();
                }
                latch.countDown();
            }).start();
        }
        try {
            latch.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("理论结果："+(MAX_TURN*MAX_TREAD));
        System.out.println("实际结果："+safePlus.getAmount());
        System.out.println("差距："+((MAX_TURN*MAX_TREAD)- safePlus.getAmount()));
    }

}
