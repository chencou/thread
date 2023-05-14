package org.example.threadstatus;

public class DaemonDemo {

    public  static final  int   MAX_TURN=4; //程序运行

    public  static final  int   SEEP_GAP=500; //随眠时间单位 毫秒

    public static  String  getCurThreadName(){ //获取当前线程名称
        return  Thread.currentThread().getName();
    }

    public static  Thread  getCurThread(){ //获取当前线程
        return  Thread.currentThread();
    }

    static  class DaemonThread extends Thread{
        public  DaemonThread(){
            super("[daemonThread]");
        }

        @Override
        public void run() {
            System.out.println("--daemon-thread 运行开始！");
            for (int i = 1; ; i++) {
                System.out.print(getName()+"--轮询："+i);
                System.out.println("--守护线程："+isDaemon());
                try {
                    Thread.sleep(SEEP_GAP);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread daemonThread=new DaemonThread(); //守护线程
        daemonThread.setDaemon(true);
        daemonThread.start();
        //用户线程
        Thread userThread=new Thread(()->{
            System.out.println("用户线程开始运行！");
            for (int i = 1; i <= MAX_TURN; i++) {
                System.out.print(getCurThreadName()+">>轮询:"+i);
                System.out.println(">>守护线程:"+ getCurThread().isDaemon());
                try {
                    Thread.sleep(SEEP_GAP);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(getCurThreadName()+">>运行结束！");
        },"[userThread]");
        //启动用户线程
        userThread.start();
        System.out.println(getCurThreadName()+"运行结束！");
    }
}
