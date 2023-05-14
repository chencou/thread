package org.example.sleep;

public class SleepDome {

    private  static  final  Integer SLEEP_GAP=5000; //睡眠时间 5秒

    private  static  final Integer  MAX_TURN=5; //随眠最大次数

    static  class  SleepThread extends  Thread{

        static  int  threadSeqNumber=1;

        public  SleepThread(){
            super("sleepThead-"+threadSeqNumber);
            threadSeqNumber++;
        }

        @Override
        public void run() {
            try {
                System.out.println(getName()+"当前线程开始执行！");
                for (int i = 1; i < MAX_TURN; i++) {
                    System.out.println(getName()+"，睡眠轮次："+i);
                    Thread.sleep(SLEEP_GAP);
                }
            }catch (Exception e){
                throw  new RuntimeException(getName()+"线程运行错误！");
            }finally {
                System.out.println(getName()+"执行完成！");
            }
        }
    }

    private  static  String  getCurThreadName(){
        return Thread.currentThread().getName();
    }
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Thread thread=new SleepThread();
            thread.start();
        }
        System.out.println(getCurThreadName()+"运行结束。");
    }
}
