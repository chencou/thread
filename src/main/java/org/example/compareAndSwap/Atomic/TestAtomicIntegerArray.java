package org.example.compareAndSwap.Atomic;

import org.example.excutor.ThreadUtil;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class TestAtomicIntegerArray {

    public static void main(String[] args) {
        int  tempvalue =0;
        //原始的数组
        int[] array={1,2,3,4,5,6};
        AtomicIntegerArray  i =new AtomicIntegerArray(array);
        //获取第0个元素，然后设置为2
        tempvalue=i.getAndSet(0,2);
        ThreadUtil.PrintTo("tempvalue:"+tempvalue+"; i:"+i);

        //获取第0个元素，然后自增
        tempvalue=i.getAndIncrement(0);
        ThreadUtil.PrintTo("tempvalue:"+tempvalue+"; i:"+i);

        //获取第0个元素，然后加一个delta 5
        tempvalue= i.getAndAdd(0,5);
        ThreadUtil.PrintTo("tempvalue:"+tempvalue+"; i:"+i);
    }
}
