package org.example.excutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池自定义拒绝策略  AbortPolicy：拒绝策略  DiscardPolicy：抛弃策略
 * DiscardOldestPolicy：抛弃最老的任务策略  CallerRunsPolicy：由调用执行策略
 */
public class CreateThreadPoolDemo7 {

    public static  String  getCurThreadName(){ //获取当前线程名称
        return  Thread.currentThread().getName();
    }

    //线程
    static class TargetTask implements  Runnable{

        @Override
        public void run() {
            System.out.println(getCurThreadName()+"：hello word！");
        }
    }

    //线程工厂
    static  public  class  SimpleThreadFactory implements ThreadFactory {
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

    public  static  class  CustomIgnorePolicy implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println(r+"rejected"+"- getTaskCount"+ executor.getTaskCount());
        }
    }

    public static void main(String[] args) {
        int  corePoolSize = 2; //核心线程数
        int maximumPoolSize = 4; //最大线程数
        long keepAliveTime = 10; //线程空闲时间
        ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.SECONDS,new ArrayBlockingQueue<>(2),
                new SimpleThreadFactory(),new CustomIgnorePolicy());
        pool.prestartAllCoreThreads(); //预启动所有核心线程
        for (int i = 0; i < 10; i++) {
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
