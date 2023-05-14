package org.example.theadLocal;

import org.example.excutor.ThreadUtil;

public class ThreadLocalTest2 {

    //模拟业务方法
    public static void serviceMethod(){
        ThreadUtil.TheadSleepSend(500l);
        SeepLog.logPoint("point-1 service");
        //业务调用
        daoMethod();
        rpcMethod();
    }

    /**
     * 模拟dao业务方法
     */
    public static void  daoMethod(){
        //模拟所消耗的时间
        ThreadUtil.TheadSleepSend(400l);
        //记录上一个点point-1 到 point-2的耗时
        SeepLog.logPoint("point-2 dao");
    }

    /**
     * 模拟远程业务方法
     */
    public static void rpcMethod(){
        //模拟所消耗的时间
        ThreadUtil.TheadSleepSend(600l);
        //记录上一个点point-2 到 point-3的耗时
        SeepLog.logPoint("point-3 rpc");
    }

    public static void main(String[] args) {
        Runnable runnable =()->{
            //开始耗时记录，保存当前时间
            SeepLog.beginSpeedLog();
            //调用模拟业务方法
            serviceMethod();
            //打印耗时
            SeepLog.printCost();
           //结束耗时记录
            SeepLog.endSpeedLog();
        };
        new Thread(runnable).start();
        ThreadUtil.TheadSleepSend(10l); //
    }
}
