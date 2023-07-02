package org.example.ThreadComm;

import jdk.nashorn.internal.codegen.CompilerConstants;
import org.example.excutor.ThreadUtil;
import org.example.threadLock.consumerAndProduct.Consumer;
import org.example.threadLock.consumerAndProduct.Goods;
import org.example.threadLock.consumerAndProduct.IGood;
import org.example.threadLock.consumerAndProduct.Producer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 生产者消费哦案例 线程通讯 版
 */
public class CommunicatePetData {

    //数据缓冲区最大长度
    public  static  final int  MAX_MOUNT=10;

    //数据缓冲
    static class DataBuffer<T>{
        //保存数据
        private List<T> dataList =new ArrayList<>();

        //数据缓冲长度
        public  Integer amount=0;

        private final   Object LOCK_OBJECT =new Object();
        private final   Object NOT_FULL =new Object();
        private final   Object NOT_EMPTY =new Object();


        //向数据缓冲区添加元素
        public  void  add(T element)throws  Exception{
            while (amount>MAX_MOUNT){
                synchronized (NOT_FULL){
                    ThreadUtil.PrintTo("队列已满请等待！");
                    NOT_FULL.wait();
                }
            }
            synchronized (LOCK_OBJECT){
                dataList.add(element);
                amount++;
            }
            synchronized (NOT_EMPTY){
                //发送未空通知
                NOT_EMPTY.notify();
            }
        }
        //像数据缓冲区拿取元素
        public T fetch() throws  Exception{
            while (amount<=0){
                synchronized (NOT_EMPTY){
                    ThreadUtil.PrintTo("队列已空！");
                    NOT_EMPTY.wait();
                }
            }
            T element = null;
            synchronized (LOCK_OBJECT){
                element= dataList.remove(0);
                amount--;
            }
            synchronized (NOT_FULL){
                NOT_FULL.notify();
            }
            return element;
        }
    }

    public static void main(String[] args) throws Exception{
        System.setErr(System.out);
        //共享数据区，实例对象
        DataBuffer<IGood> dataBuffer =new DataBuffer<>();
        //生产者执行动作
        Callable<IGood> produceAction =()->{
          //首先随机生产一个商品
          IGood good =Goods.produceOne();
          //将商品添加到共享数据区
           dataBuffer.add(good);
           return good;
        };
        //消费者执行动作
        Callable<IGood> consumerAction =()->{
            //获取商品
            IGood good =null;
            good = dataBuffer.fetch();
            return good;
        };
        //同步并发执行线程数量
        final int THREAD_TOTAL =20;
        //线程池
        ExecutorService  threadPool = Executors.newFixedThreadPool(THREAD_TOTAL);
        //假定共11个线程，其中10个消费者，但是只有1个生产者
        final int CONSUMER_TOTAL =11;
        final int PRODUCE_TOTAL=1;
        for (int i = 0; i < PRODUCE_TOTAL; i++) {
            //生产者线程每生产一个商品，间隔50毫秒
            threadPool.submit(new Producer(produceAction,50));
        }
        for (int i = 0; i < CONSUMER_TOTAL; i++) {
            //消费者线程每消费一个商品，间隔100毫秒
            threadPool.submit(new Consumer(consumerAction,100));
        }
    }
}
