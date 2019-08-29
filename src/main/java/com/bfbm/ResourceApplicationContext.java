package com.bfbm;

import java.util.Map;

public class ResourceApplicationContext implements ApplicationContext {
    private BeanDefinition beanDefinition;
    @Override
    public Object getBean(String beanName) {
        Map<String, String>prototypeIoc=  beanDefinition.getPrototypeIoc();
        String className=prototypeIoc.get(beanName);
        if(className!=null && !className.isEmpty()){
            try {
                Class<?> clazz=Class.forName(className);
                Object instance=clazz.newInstance();
                return instance;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Map<String, Object> singletonIoc=  beanDefinition.getSingletonIoc();
        return singletonIoc.get(beanName);
    }

    public BeanDefinition getBeanDefinition() {
        return beanDefinition;
    }

    public void setBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinition = beanDefinition;
    }
}
