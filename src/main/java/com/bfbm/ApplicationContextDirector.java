package com.bfbm;


import com.bfbm.loadResource.PropertiesLoaderFactory;
import com.bfbm.loadResource.ResourceFactory;

public class ApplicationContextDirector implements ContextDirector {
    private ContextBuilder contextBuilder;

    @Override
    public ApplicationContext makeContext() {
        ResourceApplicationContext applicationContext=new ResourceApplicationContext();
        PropertiesLoaderFactory resourceFactory=new PropertiesLoaderFactory();
        contextBuilder.beanParseBuild(resourceFactory);
        contextBuilder.instanceBuild();
        BeanDefinition beanDefinition=contextBuilder.getBeanDefiniton();
        applicationContext.setBeanDefinition(beanDefinition);
        return applicationContext;
    }

    public ApplicationContextDirector(ContextBuilder contextBuilder) {
       this.contextBuilder=contextBuilder;
    }
}
