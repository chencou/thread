package org.example.compareAndSwap.Atomic;

import org.example.excutor.ThreadUtil;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomicInter {

    public static void main(String[] args) {
        int tempValue=0;

        AtomicInteger i =new AtomicInteger(0);
        tempValue=i.getAndSet(3); //取值，设置一个新值
        ThreadUtil.PrintTo("tempValue:"+tempValue+"; i:"+i.get());

        tempValue=i.getAndIncrement(); //取值，然后自增
        ThreadUtil.PrintTo("tempValue:"+tempValue+"; i:"+i.get());

        tempValue=i.getAndAdd(5); //取值，增加5
        ThreadUtil.PrintTo("tempValue:"+tempValue+"; i:"+i.get());

        //cas 交换
        boolean flag=i.compareAndSet(9,100);
        //输出flag: true i:100
        ThreadUtil.PrintTo("flag:"+flag+"; i:"+i.get());

    }
}
