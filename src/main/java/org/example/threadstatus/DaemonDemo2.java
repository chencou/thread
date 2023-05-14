package org.example.threadstatus;

public class DaemonDemo2 {
    //每一次轮询睡眠时长
    public  static  final  int  SLEEP_GAP=500;
    //线程执行时长
    public  static  final  int  MAX_TURN=5;

    public static  Thread  getCurThread(){ //获取当前线程
        return  Thread.currentThread();
    }

    static  class NormalThread extends Thread{
        static  int  threadNo=1;
        public  NormalThread(){
            super("[normalThread-"+threadNo+"]");
            threadNo++;
        }

        @Override
        public void run() {
            for (int i = 0; ; i++) {
                try {
                    Thread.sleep(SLEEP_GAP);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(getName()+"，守护状态为："+isDaemon());
            }
        }
    }

    public static void main(String[] args) {
        Thread daemonThread=new Thread(()->{
            for (int i = 0; i < MAX_TURN; i++) {
                Thread normalThread=new NormalThread();
                normalThread.setDaemon(false); //注 守护线程的子 线程设置为用户线程则 不会继承父线程的守护线程。父线程的每个子线程会执行自己的任务知道结束，父线程会随子线程结束才会结束。
                normalThread.start();
            }
        },"[daemonThread]");
        daemonThread.setDaemon(true);
        daemonThread.start();
        try {
            Thread.sleep(SLEEP_GAP);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(getCurThread().getName()+"运行结束.");
    }
}
