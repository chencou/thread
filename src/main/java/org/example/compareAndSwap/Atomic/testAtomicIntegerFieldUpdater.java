package org.example.compareAndSwap.Atomic;

import org.example.compareAndSwap.Atomic.pojo.User;
import org.example.excutor.ThreadUtil;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.UnaryOperator;

public class testAtomicIntegerFieldUpdater {

    public static void main(String[] args) {
        AtomicIntegerFieldUpdater<User> updater=AtomicIntegerFieldUpdater.newUpdater(User.class,"age");
        System.out.println("========AtomicIntegerField=========");
        User user=new User("1","张三");
        ThreadUtil.PrintTo(String.valueOf(updater.getAndIncrement(user)));
        ThreadUtil.PrintTo(String.valueOf(updater.getAndAdd(user,100)));
        ThreadUtil.PrintTo(String.valueOf(updater.get(user)));


        AtomicLongFieldUpdater<User> updaterLong=AtomicLongFieldUpdater.newUpdater(User.class,"index");
        System.out.println("========AtomicLongField=========");
        User userLong=new User("1","张三");
        ThreadUtil.PrintTo(String.valueOf(updater.getAndIncrement(userLong)));
        ThreadUtil.PrintTo(String.valueOf(updater.getAndAdd(userLong,100)));
        ThreadUtil.PrintTo(String.valueOf(updater.get(userLong)));

        UnaryOperator<Long>  value=UnaryOperator.identity();
        value.apply(100L);
        AtomicReferenceFieldUpdater<User,Long> updateReferent=AtomicReferenceFieldUpdater.newUpdater(User.class,Long.class,"next");
        System.out.println("========AtomicReferentField=========");
        User userRef=new User("1","张三");
        updateReferent.set(userRef, 0L);
        ThreadUtil.PrintTo(String.valueOf(updateReferent.getAndUpdate(userRef,value)));
        ThreadUtil.PrintTo(String.valueOf(updateReferent.get(userRef)));
    }
}
