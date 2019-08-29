package com.bfbm;

import com.bfbm.demo.ShirtService;
import com.bfbm.demo.SkirtService;


public class Main {
    public static void main(String[] args) {
       /* ContextBuilder applicationContextBuilder=new ApplicationContextBuilder();
        ContextDirector applicationContextDirector=new ApplicationContextDirector(applicationContextBuilder);
        ApplicationContext applicationContext=applicationContextDirector.makeContext();*/
        ApplicationContext applicationContext=new ApplicationContextDirector(new ApplicationContextBuilder()).makeContext();
        SkirtService skirtService1= (SkirtService) applicationContext.getBean("skirtService");
        SkirtService skirtService2= (SkirtService) applicationContext.getBean("skirtService");
        System.out.println("skirtService1:"+skirtService1);
        System.out.println("skirtService2:"+skirtService2);
        skirtService1.show();
        skirtService2.show();
        ShirtService shirtService1= (ShirtService) applicationContext.getBean("shirtService");
        ShirtService shirtService2= (ShirtService) applicationContext.getBean("shirtService");
        System.out.println("shirtService1:"+shirtService1);
        System.out.println("shirtService2:"+shirtService2);
        shirtService1.show();
        shirtService2.show();

    }

}
