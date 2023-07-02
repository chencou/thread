package org.example.ThreadComm;

import org.example.excutor.ThreadUtil;

/**
 * wait 和notify 线程通讯测试
 */
public class CommTest {

    static  Object locko = new Object();

    static  class WaitTarget implements  Runnable{

        @Override
        public void run() {
            synchronized (locko){
                try {
                    //启动等待
                    ThreadUtil.PrintTo("启动等待");
                    //等待的通知，同时释放ocko监视器的owner权力
                    locko.wait();
                    //收到通知后，线程会进入locko监视器的EntryList
                }catch (Exception e){
                    e.printStackTrace();
                }
                ThreadUtil.PrintTo("收到通知，当前线程继续执行！");
            }
        }
    }

    //通知线程异步任务
    static class NotifyTarget implements  Runnable{
        @Override
        public void run() {
            //加锁
            synchronized (locko){
                //获取locko锁，然后进行发送
                locko.notifyAll();
                ThreadUtil.PrintTo("发送通知，但是线程还没有马上释放");
            }
        }
    }


    public static void main(String[] args) {
        //创建等待线程
        Thread waitThread =new Thread(new WaitTarget(),"WaitThread");
        //启动等待线程
        waitThread.start();
        ThreadUtil.TheadSleepSend(1000L);
        //创建通知线程
        Thread notifyThread =new Thread(new NotifyTarget(),"NotifyThread");
        notifyThread.start();
    }
}
