package org.example.theadLocal;

import org.example.excutor.ThreadUtil;
import org.omg.PortableInterceptor.LOCATION_FORWARD;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SeepLog {

    /**
     * 记录调用耗时本地map变量
     */
    private static final ThreadLocal<Map<String, Long>> TIME_RECORD_LOCAL = ThreadLocal.withInitial(SeepLog::initialStartTime);

    /**
     * 记录调用耗时的本地map变量初始方法
     */
    public static Map<String, Long> initialStartTime() {
        Map<String, Long> map = new HashMap<>();
        map.put("start", System.currentTimeMillis());
        map.put("last", System.currentTimeMillis());
        return map;
    }

    /**
     * 开始耗时记录
     */
    public static final void beginSpeedLog() {
        ThreadUtil.PrintTo("开始耗时记录");
        TIME_RECORD_LOCAL.get();
    }

    /**
     * 结束耗时记录
     */
    public static final void endSpeedLog() {
        ThreadUtil.PrintTo("结束耗时纪录");
        TIME_RECORD_LOCAL.remove();
    }

    /**
     * 耗时埋点
     */
    public static final void logPoint(String  point) {
        //获取上一次时间
        Long last  =TIME_RECORD_LOCAL.get().get("start");
        //计算上一次埋点到当前埋点的耗时
        Long cost =System.currentTimeMillis()-last;
        //保存上一次埋点到当前埋点的耗时
        TIME_RECORD_LOCAL.get().put(point+"cost:",cost);
        //保存当前时间，供下一次埋点使用
        TIME_RECORD_LOCAL.get().put("last",System.currentTimeMillis());
    }

    //打印耗时时间
    public  static  void  printCost(){
        for (Map.Entry<String,Long> entry: TIME_RECORD_LOCAL.get().entrySet()) {
            System.out.println(entry.getKey()+"=>"+entry.getValue());
        }
    }
}
