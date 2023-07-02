package org.example.threadLock.innerLock;

import org.example.excutor.ThreadUtil;
import org.example.threadLock.jol.ObjectLock;
import org.openjdk.jol.vm.VM;

public class InnerLockTest {

    public static void main(String[] args) {
        ThreadUtil.PrintTo(VM.current().details());
        //JVM 偏向锁延迟时间
        ThreadUtil.TheadSleepSend(5000l);
        ObjectLock lock = new ObjectLock();
        ThreadUtil.PrintTo("锁抢钱前，锁状态");


    }
}
