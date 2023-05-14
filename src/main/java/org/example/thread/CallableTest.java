package org.example.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**、
 * callable： 初步认知
 */
public class CallableTest {

    private  static  int  MAX_TURN=5;
    //循环计算任务！
    private  static  int  COMPUTE_TIMES=100000000;

    /**
     * 获取线程名称！
     * @return
     */
    public  static  String  getThreadName(){
        return  Thread.currentThread().getName();
    }
    static class ReturnnableTask implements Callable<Long> {
        @Override
        public Long call() throws Exception {
            long  startTime=System.currentTimeMillis();
            System.out.println(getThreadName()+"线程开始运行.");
            for (int i = 0; i < COMPUTE_TIMES; i++) {
                int  j=i*1000;
            }
            long used=System.currentTimeMillis()-startTime;
            System.out.println(getThreadName()+": 线程运行结束！");
            return used;
        }
    }

    public static void main(String[] args) {
        try {
            ReturnnableTask returnnableTask=new ReturnnableTask();
            FutureTask<Long> futureTask=new FutureTask<Long>(returnnableTask);
            Thread thread=new Thread(futureTask,"returnThread");
            thread.start();
            Thread.sleep(500);
            System.out.println(getThreadName()+"让子弹飞一会儿！");
            System.out.println(getThreadName()+"做自己的事情！");
            for (int i = 0; i < COMPUTE_TIMES/2; i++) {
                int  j=i*1000;
            }
            System.out.println(getThreadName()+"获取并发任务的执行结果！");
            System.out.println(thread.getName()+"线程占用时间："+futureTask.get());
        }catch (Exception e){
         e.printStackTrace();
        }
    }
}
