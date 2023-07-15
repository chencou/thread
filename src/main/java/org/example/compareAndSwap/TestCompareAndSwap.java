package org.example.compareAndSwap;

import org.example.excutor.ThreadUtil;
import sun.misc.Unsafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import static sun.misc.Unsafe.getUnsafe;

//原子实现类
public class TestCompareAndSwap {
    //基于CAS无锁实现的安全自增
    static  class  OptimisticLockingPlus{

        //并发数量
        private  static  final  int THREAD_COUNT =10 ;

        //内部值，使用用volatile保证线程可见性
        private volatile  int value;
        //不安全类
        private static final Unsafe unsafe = getUnsafe();


        //value 的内存偏移（相对于对象头部的偏移2，不是绝对偏移）
        private  static final long valueOffset ;

        //统计失败的次数
        private  static final AtomicLong failure =new AtomicLong(0);

        static{
            try {
                //取得value属性的内存偏移
                valueOffset = unsafe.objectFieldOffset(
                        OptimisticLockingPlus.class.getDeclaredField("value")
                );
                ThreadUtil.PrintTo("valueOffset:="+valueOffset);
            }catch (Exception e){
                throw  new  Error(e);
            }
        }

        //通过CAS原子操作，进行"比较交换"
        public final boolean unSafeCompareAndSet(int  oldValue,int newValue){
            //原子操作：使用unsafe的"比较交换"方法进行value属性的交换
            return unsafe.compareAndSwapInt(this,valueOffset,oldValue,newValue);
        }
        //使用无锁编程实现安全自增方法
        public  void selfPlus(){
            int oldValue = value;
            //通过CAS原子操作，如果操作失败就自旋，一直到操作成功
            int i=0;
            do {
                //获取旧值
                oldValue=value;
                //统计无效自旋次数
                if (i++ >1) {
                    //记录失败的次数
                    failure.incrementAndGet();
                }
            }while (!unSafeCompareAndSet(oldValue,oldValue+1));
        }

        //测试用例入口方法
        public static void main(String[] args) throws InterruptedException {
            final  OptimisticLockingPlus  cas=  new OptimisticLockingPlus();
            //倒数闩，需要倒数THREAD_COUNT 次
            CountDownLatch latch =new CountDownLatch(THREAD_COUNT);
            for (int i = 0; i < THREAD_COUNT; i++) {
                //提交10个任务
                ThreadUtil.getMixedTargetThreadPoolLazyHolder().submit(()->{
                   //每个任务累加1000次
                    for (int j = 0; j < 1000; j++) {
                        cas.selfPlus();
                    }
                    latch.countDown(); //执行完一个任务，倒数闩减少一次
                });
            }
            latch.wait(); //主线程等待倒数闩倒数完毕
            ThreadUtil.PrintTo("累加之和："+cas.value);
            ThreadUtil.PrintTo("失败次数"+cas.failure.get());
        }

    }



}
