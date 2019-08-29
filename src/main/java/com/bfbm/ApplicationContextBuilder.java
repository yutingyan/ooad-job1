package com.bfbm;

import com.bfbm.annotation.BfbmScope;
import com.bfbm.annotation.BfbmService;
import com.bfbm.demo.ShirtService;
import com.bfbm.demo.SkirtService;
import com.bfbm.loadResource.PropertiesLoaderFactory;
import com.bfbm.loadResource.ResourceFactory;

import java.io.File;
import java.net.URL;
import java.util.*;

public class ApplicationContextBuilder implements  ContextBuilder {
    private  List<String> classNames=new ArrayList<String>();
    private Map<String,Object> singletonIoc;
    private Map<String,String> prototypeIoc;


    @Override
    public void beanParseBuild(PropertiesLoaderFactory factory) {
        Properties properties=factory.getProperties();
        scanClass(properties.getProperty("scanPackage"));
    }

    @Override
    public void instanceBuild() {
        if(classNames.isEmpty()){return;}
        prototypeIoc=new HashMap<String, String>();
        singletonIoc=new HashMap<String, Object>();
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

    //默认的类名首字母小写
    private String tolowerFirstCase(String simpleName){
        char[] chars=simpleName.toCharArray();
        chars[0]+=32;
        return String.valueOf(chars);
    }

    @Override
    public BeanDefinition getBeanDefiniton() {
        BeanDefinition beanDefinition=new BeanDefinition();
        beanDefinition.setPrototypeIoc(prototypeIoc);
        beanDefinition.setSingletonIoc(singletonIoc);
        return beanDefinition;
    }

    private void scanClass(String scanPackage){
        URL url=this.getClass().getClassLoader().getResource(scanPackage.replaceAll("\\.","/"));
        File calssPath=new File(url.getFile());
        for (File file : calssPath.listFiles()) {
            if(file.isDirectory()){
                scanClass(scanPackage+"."+file.getName());
            }else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                String className = (scanPackage + "." + file.getName()).replace(".class", "");
                classNames.add(className);
            }
        }
    }



    public Map<String, String> getPrototypeIoc() {
        return prototypeIoc;
    }

    public void setPrototypeIoc(Map<String, String> prototypeIoc) {
        this.prototypeIoc = prototypeIoc;
    }

    public Map<String, Object> getSingletonIoc() {
        return singletonIoc;
    }

    public void setSingletonIoc(Map<String, Object> singletonIoc) {
        this.singletonIoc = singletonIoc;
    }

    public List<String> getClassNames() {
        return classNames;
    }

    public void setClassNames(List<String> classNames) {
        this.classNames = classNames;
    }

    public ApplicationContextBuilder() {
    }

}
