package com.bfbm.demo;

import com.bfbm.annotation.BfbmService;

@BfbmService(value = "shirtService")
public class ShirtService {

    public void show(){
        System.out.println("调用了shirtService");
    }

}
