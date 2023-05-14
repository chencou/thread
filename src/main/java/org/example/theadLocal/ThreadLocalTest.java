package org.example.theadLocal;

import lombok.Data;
import org.example.excutor.ThreadUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLocalTest {


    //实例
    @Data
    static class Foo {

        static final AtomicInteger AMOUNT = new AtomicInteger(0);

        //编号
        private int index = 0;
        //内容
        private int bar = 10;

        //构造器
        public Foo() {
            index = AMOUNT.incrementAndGet();
        }

        @Override
        public String toString() {
            return index + "@Foo{bar=" + bar + "}";
        }
    }

    //定义线程本地变量
    private static final ThreadLocal<Foo> LOCAL_FOO = new ThreadLocal<>();

    public static void main(String[] args) {
        //获取自定义的混合线程池
        ThreadPoolExecutor threadPool= ThreadUtil.getMixedTargetThreadPoolLazyHolder();

        //提交5个任务，将会用到5个线程
        for (int i = 0; i < 5; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    if (LOCAL_FOO.get() == null) {
                        LOCAL_FOO.set(new Foo());
                    }
                    ThreadUtil.PrintTo("初始的本地值："+LOCAL_FOO.get().toString());
                    //每个线程执行10次
                    for (int j = 0; j < 10; j++) {
                        Foo foo=LOCAL_FOO.get();
                        foo.setBar(foo.getBar()+1);
                        ThreadUtil.TheadSleepSend(10l);
                    }
                    ThreadUtil.PrintTo("累加10次之后的本地值："+LOCAL_FOO.get().toString());
                    LOCAL_FOO.remove();
                }

            });
        }
    }
}