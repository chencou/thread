package org.example.excutor;

import java.util.concurrent.ExecutorService;

public class CreateThreadPoolDemo8 {

    public static  String  getCurThreadName(){ //获取当前线程名称
        return  Thread.currentThread().getName();
    }

    static class TargetTask implements  Runnable{

        @Override
        public void run() {
            System.out.println(getCurThreadName()+"：hello word！");
        }
    }

    public static void main(String[] args) {
        System.getProperties().put("mixed.thread",600);
        ExecutorService pool= ThreadUtil.getMixedTargetThreadPoolLazyHolder();
        for (int i = 0; i < 5; i++) {
            pool.execute(new TargetTask());
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("线程池关闭");
    }
}
