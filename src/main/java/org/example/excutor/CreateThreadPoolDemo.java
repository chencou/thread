package org.example.excutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CreateThreadPoolDemo {

    public  static  final  int SLEEP_GAP =500;

    public static  String  getCurThreadName(){ //获取当前线程名称
        return  Thread.currentThread().getName();
    }
    static  class TargetTask implements Runnable{

        static AtomicInteger taskNo=new AtomicInteger(1);

        private String taskName;

        public TargetTask(){
            taskName="task"+taskNo.get();
            taskNo.incrementAndGet();
        }
        @Override
        public void run() {
            System.out.println("["+getCurThreadName()+"]任务："+taskName+"doing");
            //线程缓慢执行
            try {
                Thread.sleep(SLEEP_GAP);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("["+getCurThreadName()+"]"+taskName+"运行结束.");
        }
    }

    public static void main(String[] args) {
        //单线程线程池创建
//        ExecutorService pool= Executors.newSingleThreadExecutor();
        //固定线程数量线程池
//        ExecutorService pool= Executors.newFixedThreadPool(3);
        //可缓存的线程池
//        ExecutorService pool= Executors.newCachedThreadPool();
//        for (int i = 0; i < 5; i++) {
//            pool.execute(new TargetTask());
//            pool.submit(new TargetTask());
//        }
        //关闭线程池
//        pool.shutdown();
        ScheduledExecutorService scheduled=Executors.newScheduledThreadPool(2);
        for (int i = 0; i < 2; i++) {
            //一下参数为  线程  首个线程执行任务的时间  线程间隔任务的执行时间   时间单位毫秒
            scheduled.scheduleAtFixedRate(new TargetTask(),0,500, TimeUnit.MILLISECONDS);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //关闭线程池
        scheduled.shutdown();
    }
}
