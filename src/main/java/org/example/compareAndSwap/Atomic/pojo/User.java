package org.example.compareAndSwap.Atomic.pojo;

import java.io.Serializable;

public class User implements Serializable {

    String uid;

    String  nickName;

    public volatile  int  age;

    public volatile  long   index;

    public volatile  Long   next=0L;

    public  User(String  uid,String  nickName){
        this.uid=uid;
        this.nickName=nickName;
    }

    @Override
    public String toString() {
        return "User{uid="+this.uid+",nikeName="+this.nickName+"}";
    }
}
