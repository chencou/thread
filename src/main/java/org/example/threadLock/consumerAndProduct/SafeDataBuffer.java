package org.example.threadLock.consumerAndProduct;

import org.example.excutor.ThreadUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据缓冲区 安全版本
 */
public class SafeDataBuffer<T> {

    public  static  final  int MAX_AMOUNT=10;

    private BlockingQueue<T> dataList =new LinkedBlockingQueue<>();

    //保存数量
    private AtomicInteger amount= new AtomicInteger(0);

    //向数据区增加一个元素
    public  void  add(T element) throws  Exception {
        if (amount.get() > MAX_AMOUNT) {
            ThreadUtil.PrintTo("队列已满！");
            return;
        }

        dataList.add(element);
        ThreadUtil.PrintTo(element+"");
        amount.incrementAndGet();
        //如果数据不一直就抛出异常。
        if (amount.get() != dataList.size()) {
            throw  new Exception(amount +"!=" +dataList.size());
        }
    }

    //从数据区取出一个元素
    public T  fetch() throws  Exception {
        if (amount.get() <= 0) {
            ThreadUtil.PrintTo("队列已空");
            return  null;
        }
        T element =dataList.remove();
        ThreadUtil.PrintTo(element+"");
        //如果数据不一致，就抛出异常
        if (amount.get() != dataList.size()) {
            throw new Exception( amount +"!=" +dataList.size());
        }
        return  element;
    }
}
