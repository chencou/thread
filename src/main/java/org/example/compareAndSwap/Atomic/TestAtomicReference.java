package org.example.compareAndSwap.Atomic;

import org.example.compareAndSwap.Atomic.pojo.User;
import org.example.excutor.ThreadUtil;

import java.util.concurrent.atomic.AtomicReference;

public class TestAtomicReference {

    public static void main(String[] args) {
        AtomicReference<User> userRef= new AtomicReference<User>();
        //User对象
        User user=new User("1","张三");
        userRef.set(user);
        ThreadUtil.PrintTo("userRef is:"+userRef.get());
        //要使用CAS替换的user的对象
        User updateUser=new User("2","李四");
        //使用CAS替换
        boolean  success=userRef.compareAndSet(user,updateUser);
        ThreadUtil.PrintTo("cas  result  is:"+success);
        ThreadUtil.PrintTo("after cas, userRef is:"+userRef.get());
    }
}
