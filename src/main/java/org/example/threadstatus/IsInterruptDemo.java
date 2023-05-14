package org.example.threadstatus;

/**
 * 线程方法： isInterrupt 判断线程中断 可用此方法处理被中断的线程！
 */
public class IsInterruptDemo {

    static class SleepThread extends Thread {
        @Override
        public void run() {

            System.out.println(getName() + "线程启动");
            while (true) {
                System.out.println(getName() + isInterrupted());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //判断线程是否被中断 中断则退出。
                if (isInterrupted()) {
                    System.out.println(getName() + "线程运行结束！");
                    return;
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Thread thread = new SleepThread();
        thread.start();
        Thread.sleep(2000);
        thread.interrupt();
        Thread.sleep(2000);
        thread.interrupt();
    }
}
