package org.example.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程业务并发处理
 */
public class RunnableA {

    public  static final  int   MAX_TURN=10; //程序运行

    public  static int threadNo=1; //线程编号

    public static  String  getCurThreadName(){ //获取当前线程名称
        return  Thread.currentThread().getName();
    }


    static  class RunnableDo implements Runnable{
        //原子属性 保证数据在并发环境下操作
        AtomicInteger  count =new AtomicInteger(MAX_TURN);
        @Override
        public void run() {
            for (int i = 0; i <=count.get(); i++) {
                if (count.get()>0) {
                    System.out.println("当前商品："+count.get()+"件");
                    System.out.println(getCurThreadName()+"当前线程卖出"+count.decrementAndGet()+"件商品。");
                    System.out.println("剩余商品："+count.get()+"件");
                }
            }
            System.out.println(getCurThreadName()+"运行结束！");
        }
    }

    public static void main(String[] args) {
        RunnableDo runnableDo=new RunnableDo();
        for (int i = 0; i < 3; i++) {
            Thread thread=new Thread(runnableDo,"ThreadNo:"+threadNo++);
            thread.start();
        }
        System.out.println(getCurThreadName()+"执行完成！");
    }

}
