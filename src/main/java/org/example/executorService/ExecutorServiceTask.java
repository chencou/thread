package org.example.executorService;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ExecutorService ：线程池操作 注:在实际的开发中线程池中不会运用此方法。
 */
public class ExecutorServiceTask {

    private  static  int  MAX_TURN=5;
    //循环计算任务！
    private  static  int  COMPUTE_TIMES=100000000;
    //创建线程池
    private static ExecutorService executorService= Executors.newFixedThreadPool(3);
    //获取线程名称
    private  static String getThreadName(){return Thread.currentThread().getName();}

    static class RunnableDome implements Runnable {
        @Override
        public void run() {
            System.out.println(getThreadName()+"当前线程运行！");
            for (int i = 0; i < MAX_TURN; i++) {
                System.out.println(getThreadName()+"轮回"+(i+1)+"次");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(getThreadName()+"线程运行结束！");
        }
    }

    static class CallableDome implements Callable<Long>{
        @Override
        public Long call() throws Exception {
            Long startTime=System.currentTimeMillis();
            System.out.println(getThreadName()+"当前线程运行！");
            for (int i = 0; i < MAX_TURN; i++) {
                System.out.println(getThreadName()+"轮回"+(i+1)+"次");
            }
            System.out.println(getThreadName()+"线程运行结束！");
            return System.currentTimeMillis()-startTime;
        }
    }

    public static void main(String[] args) {
        try {
            executorService.execute(new RunnableDome());
            executorService.execute(()->{
                System.out.println(getThreadName()+"当前线程运行！");
                for (int i = 0; i < MAX_TURN; i++) {
                    System.out.println(getThreadName()+"轮回"+(i+1)+"次");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(getThreadName()+"线程运行结束！");
            });
            //提交callable 执行的实力，有返回值。
            Future<Long> future=executorService.submit(new CallableDome());
            Long result=(Long)  future.get();
            System.out.println("异步任务的执行结果："+result);
            Thread.sleep(Integer.MAX_VALUE);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("当前线程池运行错误！");
        }
    }
}
