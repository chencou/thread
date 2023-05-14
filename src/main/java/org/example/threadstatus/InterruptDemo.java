package org.example.threadstatus;

/**
 * interrupt 使用实例类
 */
public class InterruptDemo {


    public  static final  int   MAX_TURN=50; //程序运行

    public  static final  int   SEEP_GAP=5000; //随眠时间单位 毫秒

    public static  String  getCurThreadName(){ //获取当前线程名称
        return  Thread.currentThread().getName();
    }


    static  class  SleepThread extends  Thread{

        public  static int threadNumber=1; //线程编号

        public SleepThread (){
            super("[thread-"+threadNumber+"]");
            threadNumber++;
        }

        @Override
        public void run() {
            try {
                System.out.println(getName()+"线程运行！");
                Thread.sleep(SEEP_GAP);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(getName()+"异常打断");
                return;
            }
            System.out.println(getName()+"运行结束！");
        }
    }

    public static void main(String[] args) throws Exception{
        Thread thread1=new SleepThread();
        Thread thread2=new SleepThread();
        thread1.start();
        thread2.start();
        Thread.sleep(2000); //等待2秒
        thread1.interrupt(); //interrupt  打断线程1
        Thread.sleep(5000);
        thread2.interrupt();
        Thread.sleep(1000);
        System.out.println(getCurThreadName()+"运行结束！");
    }
}
