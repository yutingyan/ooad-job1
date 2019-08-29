package com.bfbm.test;
import com.bfbm.annotation.BfbmScope;
import com.bfbm.annotation.BfbmService;
import com.bfbm.demo.ShirtService;
import com.bfbm.demo.SkirtService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class MainService {
    private Properties properties=new Properties();
    private List<String> classNames=new ArrayList<String>();
    private Map<String,Object> singletonIoc=new HashMap<String,Object>();
    private Map<String,String> prototypeIoc=new HashMap<String,String>();


    public void init(){

        //加载配置文件内容
        loadResource();

        //扫描注释类
        scan(properties.getProperty("scanPackage"));

        //初始化对象
        instance();


    }



    private void loadResource() {

        InputStream is=this.getClass().getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null!=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //默认的类名首字母小写
    private String tolowerFirstCase(String simpleName){
            char[] chars=simpleName.toCharArray();
            chars[0]+=32;
            return String.valueOf(chars);
    }

    private void scan(String scanPackage) {
        URL url=this.getClass().getClassLoader().getResource(scanPackage.replaceAll("\\.","/"));
        File calssPath=new File(url.getFile());
        for (File file : calssPath.listFiles()) {
            if(file.isDirectory()){
                scan(scanPackage+"."+file.getName());
            }else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                String className = (scanPackage + "." + file.getName()).replace(".class", "");
                classNames.add(className);
            }
        }

    }

    private void instance() {
        if(classNames.isEmpty()){return;}
        try {
            for (String className : classNames) {
                Class<?> clazz=Class.forName(className);
                if(clazz.isAnnotationPresent(BfbmService.class)) {
                    //默认的类名首字母小写
                    String beanName = tolowerFirstCase(clazz.getSimpleName());
                    Object instance = clazz.newInstance();

                    if(clazz.isAnnotationPresent(BfbmScope.class)) {
                        BfbmScope bfbmScope=clazz.getAnnotation(BfbmScope.class);
                        if("prototype".equals(bfbmScope.value())){
                            prototypeIoc.put(beanName,className);
                            continue;
                        }
                    }

                    //如果自定义beanName,采用自定义的beanName
                    BfbmService bfbmService=clazz.getAnnotation(BfbmService.class);
                    if(!"".equals(bfbmService.value())) {
                        beanName=bfbmService.value();
                        singletonIoc.put(beanName, instance);
                    }
                    //默认的情况下，取类的前一个字母小写组合，作为对象名
                   if(!singletonIoc.containsValue(instance)){
                       singletonIoc.put(beanName,instance);
                    }
                }else{
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public Object getBean(String beanName){
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
        return singletonIoc.get(beanName);
    };





    public static void main(String[] args) {
        MainService main=new MainService();
        main.init();
        SkirtService skirtService1= (SkirtService) main.getBean("skirtService");
        SkirtService skirtService2= (SkirtService) main.getBean("skirtService");
        System.out.println("skirtService1:"+skirtService1);
        System.out.println("skirtService2:"+skirtService2);
        skirtService1.show();
        skirtService2.show();
        ShirtService shirtService1= (ShirtService) main.getBean("shirtService");
        ShirtService shirtService2= (ShirtService) main.getBean("shirtService");
        System.out.println("shirtService1:"+shirtService1);
        System.out.println("shirtService2:"+shirtService2);
        shirtService1.show();
        shirtService2.show();

    }
}


