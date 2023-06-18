package org.example.threadLock.consumerAndProduct;


import org.example.excutor.ThreadUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通用生产者
 */
public class Producer implements Runnable{

    //成产的时间间隔。生产一次等待的时间默认为200毫秒
    public static final int PRODUCT_GAP=200;

    //总次数
    static final AtomicInteger TURN =new AtomicInteger(0);

    //生产者对象编号
    static final AtomicInteger PRODUCT_No = new AtomicInteger(1);

    //生产者名称
    String name = null;

    //生产者动作
    Callable action = null;

    int gap= PRODUCT_GAP;

    @Override
    public void run() {
      while (true){
          try {
              //执行生产动作
              Object out = action.call();
              //输出生产结果
              if (null != out) {
                  ThreadUtil.PrintTo("第"+TURN.get()+"轮生产："+out);
              }
              //每一轮生产之后，稍等一下
              ThreadUtil.TheadSleepSend(Long.valueOf(gap));
              //增加生产轮次
              TURN.incrementAndGet();
          }catch (Exception e){
              e.printStackTrace();
          }
      }
    }

    public Producer(Callable action ,int  gap){
        this.action =action;
        this.gap =gap;
        name = "生产者-"+ PRODUCT_No.incrementAndGet();
    }

}
