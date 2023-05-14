package org.example.excutor;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 线程池 submit 返回future 异常对象
 */
public class CreateThreadPoolDemo4 {

    public static  String  getCurThreadName(){ //获取当前线程名称
        return  Thread.currentThread().getName();
    }

    static class TargetTask implements  Runnable{

        @Override
        public void run() {
            System.out.println(getCurThreadName()+"：hello word！");
        }
    }

    static class TargetTaskWithError extends  TargetTask{
        @Override
        public void run() {
            super.run();
            throw new RuntimeException("Error  from "+ getCurThreadName());
        }
    }

    public static void main(String[] args) {
        ScheduledExecutorService pool= Executors.newScheduledThreadPool(2);
        pool.execute(new TargetTaskWithError());
        Future future = pool.submit(new TargetTaskWithError());
        try {
            if (future.get() == null) System.out.println("任务完成！");
        }catch (Exception e){
            System.out.println(e.getCause().getMessage());
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //关闭线程池
        pool.shutdown();
    }

}
