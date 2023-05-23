package org.example.threadLock.dome1;

public class NoSafePlus {

    private  int amount =0;

//    public  void  selfPlus(){
//        amount++;
//    }
    //解决线程安全的问题
    public  synchronized void  selfPlus(){
        amount++;
    }

    public  Integer getAmount(){
        return amount;
    }
}
