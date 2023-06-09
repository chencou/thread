package org.example.threadLock.consumerAndProduct;

import org.example.excutor.ThreadUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//数据缓冲区线程不安全版
public class NotSafeDataBuffer<T> {
    public  static  final  int MAX_AMOUNT=10;

    private List<T> dataList =new LinkedList<>();

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
        T element =dataList.remove(0);
        ThreadUtil.PrintTo(element+"");
        amount.decrementAndGet();
        //如果数据不一致，就抛出异常
        if (amount.get() != dataList.size()) {
            throw new Exception( amount +"!=" +dataList.size());
        }
        return  element;
    }

}
