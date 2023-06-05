package org.example.threadLock.consumerAndProduct;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Tolerate;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@ToString
@Builder
public class IGood {

    @Builder.Default
    private AtomicInteger id=new AtomicInteger(0);

    private String  name;


    public  synchronized   IGood productOne(){
        return new IGood().builder().id(new AtomicInteger(id.getAndIncrement())).name("商品"+id).build();
    }
    @Tolerate
    public IGood(){}
}
