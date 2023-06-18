package org.example.threadLock.consumerAndProduct;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 随机生产商品
 */
public class Goods {

    //自增编号
    private  final static AtomicInteger threadLocal =new AtomicInteger(1);



    public static IGood produceOne(){
        int  id =(int)(Math.random()*100)-1;
        return IGood.builder().id(new AtomicInteger(id)).name("第"+id+"商品").build();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            System.out.println( Goods.produceOne());
        }

    }
}
