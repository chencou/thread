package org.example.excutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**、
 * 线程池钩子方法 terminated  beforeExecute afterExecute
 */
public class CreateThreadPoolDemo6 {

    public static  String  getCurThreadName(){ //获取当前线程名称
        return  Thread.currentThread().getName();
    }

    static class TargetTask implements  Runnable{

        @Override
        public void run() {
            System.out.println(getCurThreadName()+"：hello word！");
        }
    }

    public static void main(String[] args) {

        ExecutorService pool  = new ThreadPoolExecutor(2, 4,
                60, TimeUnit.SECONDS,new LinkedBlockingDeque<>(2)
        ){
            AtomicLong startTime =new AtomicLong(0);

            //调度器终止钩子
            @Override
            protected void terminated() {
                System.out.println("调度器已经终止！");
            }

            //执行前钩子

            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println(r+"前钩子被执行");
                startTime.set(System.currentTimeMillis());
                super.beforeExecute(t, r);
            }

            //执行后钩子

            @Override
            protected void  afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                long  time = (System.currentTimeMillis()-startTime.get());
                System.out.println(r+"后钩子执行任务 时长："+time);
            }
        };
        for (int i = 0; i < 5; i++) {
            pool.execute(new TargetTask());
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
