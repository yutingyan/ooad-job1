package com.bfbm;

import java.util.Map;

public class BeanDefinition {
    private  Map<String,String> prototypeIoc;
    private  Map<String,Object> singletonIoc;

    public  Map<String, String> getPrototypeIoc() {
        return prototypeIoc;
    }

    public void setPrototypeIoc(Map<String, String> prototypeIoc) {
        this.prototypeIoc = prototypeIoc;
    }

    public  Map<String, Object> getSingletonIoc() {
        return singletonIoc;
    }

    public void setSingletonIoc(Map<String, Object> singletonIoc) {
        this.singletonIoc = singletonIoc;
    }
}
