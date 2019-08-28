package com.bfbm.demo;
import com.bfbm.annotation.BfbmScope;
import com.bfbm.annotation.BfbmService;

@BfbmService
@BfbmScope("prototype")
public class SkirtService {
    public void show(){
        System.out.println("调用了SkirtService");
    }
}
