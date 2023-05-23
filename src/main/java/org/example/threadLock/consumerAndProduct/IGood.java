package org.example.threadLock.consumerAndProduct;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Tolerate;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@ToString
public class IGood {

    private Integer id;

    private String  name;


    public  static  IGood productOne(){
        return new IGood();
    }
    @Tolerate
    public IGood(){}
}
