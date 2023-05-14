package org.example.excutor;

import java.util.Random;
import java.util.concurrent.*;

public class CreateThreadPoolDemo3 {
    /**
     * 线程池 submit 回调
     * @param args
     */
    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
        Future<Integer> future = pool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                //随机获取数字
                return new Random().nextInt();
            }
        });
        try {
            Integer result =future.get();
            System.out.println("异步执行结果是："+result);
        }catch (InterruptedException e){
            System.out.println("异步被中断");
            e.printStackTrace();
        }catch (ExecutionException e){
            System.out.println("异步调用过程中发生异常！");
            e.printStackTrace();
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
