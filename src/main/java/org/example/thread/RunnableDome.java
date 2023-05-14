package org.example.thread;

/**
 * 程序引入runnable 接口
 */
public class RunnableDome {
    public  static final  int   MAX_TURN=5; //程序运行

    public  static int threadNo=1; //线程编号
    public static  String  getCurThreadName(){ //获取当前线程名称
        return  Thread.currentThread().getName();
    }

    static class RunnableDo implements Runnable{ //调用runnable 接口
        @Override
        public void run() {
            for (int i = 0; i < MAX_TURN; i++) {
                System.out.println(getCurThreadName()+"线程运行--->"+i);
            }
            System.out.println(getCurThreadName()+"运行结束！");
        }
    }

    public static void main(String[] args) {
        try {
            Thread  thread=null;
            for (int i = 0; i < 2; i++) {
                Runnable runnable=new RunnableDo();
                thread=new Thread(runnable,"runnableThread"+threadNo++);
                thread.start();
            }
            System.out.println(getCurThreadName()+"执行完成！");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
