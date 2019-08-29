package com.bfbm;

import com.bfbm.loadResource.PropertiesLoaderFactory;


public interface ContextBuilder {
    void beanParseBuild(PropertiesLoaderFactory factory);
    void instanceBuild();
    BeanDefinition getBeanDefiniton();
}
