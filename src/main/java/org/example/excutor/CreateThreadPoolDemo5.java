package org.example.excutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂 线程池创建
 */
public class CreateThreadPoolDemo5 {


    public static  String  getCurThreadName(){ //获取当前线程名称
        return  Thread.currentThread().getName();
    }

    static class TargetTask implements  Runnable{

        @Override
        public void run() {
            System.out.println(getCurThreadName()+"：hello word！");
        }
    }

    static  public  class  SimpleThreadFactory implements ThreadFactory{
        static AtomicInteger threadNo= new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            String  threadName="simpleThread-"+threadNo.get();
            System.out.println("创建一个线程名称为："+threadName);
            Thread thread =new Thread(r,threadName);
            thread.setDaemon(true);
            return thread;
        }
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newScheduledThreadPool(2,new SimpleThreadFactory());
        for (int i = 0; i < 5; i++) {
            pool.submit(new TargetTask());
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("线程池关闭");
        pool.shutdown();
    }
}
