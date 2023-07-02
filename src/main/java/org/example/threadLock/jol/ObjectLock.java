package org.example.threadLock.jol;


/**
 * 对象布局分析
 */

public class ObjectLock {

    private Integer amount =0; //整形字段占用4字节

    public void increase(){
        synchronized (this){
            amount++;
        }
    }

    /**
     * 输出十六进制，小端模式的hashCode
     */
    public String hexHash(){
        //对象的原始hashCode，Java默认为大小端模式
        int  hashCode =this.hashCode();

        //转成小端模式的字节数组
        return  null;
    }
}
