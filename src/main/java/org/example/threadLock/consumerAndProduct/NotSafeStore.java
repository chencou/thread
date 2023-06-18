package org.example.threadLock.consumerAndProduct;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//数据执行
public class NotSafeStore {

    //数据缓冲区静态实例
    private  static NotSafeDataBuffer<IGood> notSafeDataBuffer =new NotSafeDataBuffer<>();


    //数据缓冲区静态实例安全
    private  static SafeDataBuffer<IGood> safeDataBuffer =new SafeDataBuffer<>();
    //生产者执行动作
    static Callable<IGood> produceAction=()->{
        //首先生成一个随机商品
        IGood goods =Goods.produceOne();
        //将商品加上共享数据区
        try {
//            notSafeDataBuffer.add(goods);
            //安全版
            safeDataBuffer.add(goods);
        }catch (Exception e){
            e.printStackTrace();
        }
        return goods;
    };

    //消费之执行动作
    static  Callable<IGood> consumerAction=()->{
        //从PetStore获取商品
        IGood goods = null;
        try {
//            goods = notSafeDataBuffer.fetch();
            //安全版
            goods=safeDataBuffer.fetch();
        }catch (Exception e){
            e.printStackTrace();
        }
        return goods;
    };

    //执行
    public static void main(String[] args) {
      System.setErr(System.out);
      //同时并发执行的线程数量
       final  int  THREAD_TOTAL =20;
       //线程池,用于多线程模拟测试
        ExecutorService  threadPool = Executors.newFixedThreadPool(THREAD_TOTAL);
        for (int i = 0; i < 5; i++) {
            //生产者实例每一个商品，间隔500毫秒。
            threadPool.submit(new Producer(produceAction,500));
            //消费者实例每一个商品，间隔1500毫秒。
            threadPool.submit(new Consumer(consumerAction,1500));
        }
    }
}
