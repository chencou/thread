package org.example.excutor;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池两种两种返回方式
 * execute ：无返回状态 和  submit ： 有返回状态 用 future 接收
 */
public class CreateThreadPoolDemo2 {
    public  static  final  int SLEEP_GAP =500;

    public static  String  getCurThreadName(){ //获取当前线程名称
        return  Thread.currentThread().getName();
    }

    static  class  TargetTask implements  Runnable{
        static AtomicInteger taskNo=new AtomicInteger(1);

        protected String taskName;

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

    static  class  TargetTaskWithError extends  TargetTask{
        @Override
        public void run() {
            super.run();
            throw  new RuntimeException("Error  from "+taskName);
        }

    }

    public static void main(String[] args) {
        ScheduledExecutorService pool= Executors.newScheduledThreadPool(2);
        pool.execute(new TargetTaskWithError());

        Future future=pool.submit(new TargetTaskWithError());

        try {
            if (future.get() == null) {
                System.out.println(getCurThreadName()+"任务完成！");
            }
        }catch (Exception e){
            System.out.println(getCurThreadName()+e.getCause().getMessage());
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //关闭线程
        pool.shutdown();
    }
}
