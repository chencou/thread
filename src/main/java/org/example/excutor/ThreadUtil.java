package org.example.excutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池工具类
 * <p>优雅的关闭线程池</p>
 * @author chenfusen
 * @version 1.0
 */
public class ThreadUtil {

    /**
     * 获取线程名称
     * @return
     */
    public  static String getCurThreadName(){
        return  Thread.currentThread().getName();
    }

    /**
     * 获取当前线程
     * @return
     */
    public  static  Thread getCurThread(){
        return  Thread.currentThread();
    }

    /**
     * 线程等待时间 单位 秒
     * @param time
     */
    public  static  void  TheadSleepSend(Long  time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  线程等待时间 单位 纳秒
     * @param time
     * @param nanos
     */
    public static void TheadSleepNanos(Long  time,Integer  nanos){
        try {
            Thread.sleep(time,nanos);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 线程运行打印
     * @param message
     */
    public static void  PrintTo(String message){
        System.out.println("["+getCurThreadName()+"] ："+message);
    }


    /**
     * 如何配置合适的场景使用线程池的配置。
     */
    //获取cup核心数
    private static final int CUP_COUNT = Runtime.getRuntime().availableProcessors();
    //io处理线程数
    private static final int IO_MAX =  Math.max(2,CUP_COUNT * 2);
    //空闲保活时限，单位秒
    private static final int KEEP_ALIVE_TIME = 30;

    //有界对列
    private static final int QUEUE_SIZE = 128;

    private  static  final  String  MIXED_THREAD_AMOUNT = "mixed.thread.amount";

    public static  void  shutdownThreadPoolGracefully(ExecutorService pool){
        //判断线程池是否已关闭 已关闭者返回
        if(!(pool instanceof ExecutorService)|| pool.isShutdown()){
            return;
        }
        //关闭线程池
        try {
            //拒绝接受新任务
         pool.shutdown();
        }catch (SecurityException e){
            return;
        }catch (NullPointerException e){
            return;
        }

        try {
            //等待线程池关闭
            if(!pool.awaitTermination(60, TimeUnit.SECONDS)){
                //等待超时 强制关闭
                pool.shutdownNow();
                if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.out.println("线程池正常执行结束！");
                }
            }
        }catch (InterruptedException e){
            //等待中断 强制关闭
            pool.shutdownNow();
        }
        //判断线程池是否已关闭 已关闭者返回
        if (!pool.isTerminated()) {
            try {
                for (int i = 0; i < 1000; i++) {
                    if (pool.awaitTermination(10,TimeUnit.SECONDS)) {
                        break;
                    }
                    pool.shutdownNow();
                }
            } catch (Throwable throwable){
                System.err.println(throwable.getMessage());
            }
        }
    }
    //懒汉式单例创建线程池：用于执行定时，顺序任务
    static  class  SeqOrScheduledTargetThreadPoolLazyHolder {

        private static final ThreadPoolExecutor EXECUTOR_SERVICE=
                new ThreadPoolExecutor(
                        IO_MAX,
                        IO_MAX,
                        KEEP_ALIVE_TIME,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(QUEUE_SIZE), new ThreadFactory() {
                            private final AtomicInteger threadNumber = new AtomicInteger(1);
                            @Override
                            public Thread newThread(Runnable r) {
                                return new Thread(r, "SeqOrScheduledTargetThreadPoolLazyHolder-" + threadNumber.getAndIncrement());
                            }
                        }, new ThreadPoolExecutor.AbortPolicy());

        static {

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                ThreadUtil.shutdownThreadPoolGracefully(EXECUTOR_SERVICE);
            }));

            //关闭线程池
//            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//                ThreadUtil.shutdownThreadPoolGracefully(EXECUTOR_SERVICE);
//            }));
        }

        public static ThreadPoolExecutor getExecutorService(){
            return EXECUTOR_SERVICE;
        }
    }

    public  static ThreadPoolExecutor getSeqOrScheduledTargetThreadPoolLazyHolder(){
        return  SeqOrScheduledTargetThreadPoolLazyHolder.getExecutorService();
    }

    //懒汉式单例创建线程池: 用于混合任务
    private static  class  MixedTargetThreadPoolLazyHolder{
        //首先从换进程中获取线程数，如果没有则使用默认值
        private  static  final  int  max = (null!= System.getProperty(MIXED_THREAD_AMOUNT)?Integer.parseInt(System.getProperty(MIXED_THREAD_AMOUNT)):QUEUE_SIZE);

        private static final ThreadPoolExecutor EXECUTOR_SERVICE =
                new ThreadPoolExecutor(
                        max,
                        max,
                        KEEP_ALIVE_TIME,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(QUEUE_SIZE), new ThreadFactory() {
                    private final AtomicInteger threadNumber = new AtomicInteger(1);

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "MixedTargetThreadPoolLazyHolder-" + threadNumber.getAndIncrement());
                    }
                }, new ThreadPoolExecutor.AbortPolicy());

        static {
            EXECUTOR_SERVICE.allowCoreThreadTimeOut(true);
            //jvm关闭时关闭线程池
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                ThreadUtil.shutdownThreadPoolGracefully(EXECUTOR_SERVICE);
            }));
        }

        public static ThreadPoolExecutor getExecutorService(){
            return EXECUTOR_SERVICE;
        }
    }

    public static ThreadPoolExecutor getMixedTargetThreadPoolLazyHolder() {
        return MixedTargetThreadPoolLazyHolder.getExecutorService();
    }

    //关闭线程池
}
