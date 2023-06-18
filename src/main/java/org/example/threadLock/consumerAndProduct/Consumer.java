package org.example.threadLock.consumerAndProduct;

import org.example.excutor.ThreadUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 消费者定义
 */
public class Consumer implements Runnable{
    //消费的时间间隔，默认等待100毫秒
    public static final int CONSUMER_GAP=100;

    //消费总次数
    static final AtomicInteger TURN =new AtomicInteger(0);

    //消费者对象编号
    static final AtomicInteger CONSUMER_NO = new AtomicInteger(1);

    //消费者名称
    String name = null;

    //消费者动作
    Callable action = null;

    int gap= CONSUMER_GAP;

    @Override
    public void run() {
        while (true){
            //增加生产轮次
            TURN.incrementAndGet();
            try {
                //执行生产动作
                Object out = action.call();
                //输出生产结果
                if (null != out) {
                    ThreadUtil.PrintTo("第"+TURN.get()+"轮消费："+out);
                }
                //每一轮消费之后，稍等一下
                ThreadUtil.TheadSleepSend(Long.valueOf(gap));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public Consumer(Callable action ,int  gap){
        this.action =action;
        this.gap =gap;
        name = "消费者-"+ CONSUMER_NO.incrementAndGet();
    }
}
