package org.example.thread;

/**
 * 继承thread 线程写法！
 */
public class ThreadDome {

    public  static final  int   MAX_TURN=5; //程序运行

    public  static int threadNo=1; //线程编号
    public static  String  getCurThreadName(){ //获取当前线程名称
        return  Thread.currentThread().getName();
    }

    static  class  ThreadDo extends  Thread{

        public ThreadDo(){
            super("ThreadNo"+threadNo++); //当前线程名称！
        }

        @Override
        public void run() {
            for (int i = 0; i < MAX_TURN; i++) {
                System.out.println(getName()+"线程运行--->"+i);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("当前程序执行！");
        for (int i = 0; i < 2; i++) {
            Thread thread=new ThreadDo();
            thread.run();
        }
        System.out.println(getCurThreadName()+"执行完成！");
    }
}
