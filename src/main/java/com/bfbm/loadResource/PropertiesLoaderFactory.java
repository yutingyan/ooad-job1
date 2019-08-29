package com.bfbm.loadResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoaderFactory implements ResourceFactory {
    private  Properties properties;
    @Override
    public String resourceLoader() {

        InputStream is=this.getClass().getClassLoader().getResourceAsStream("application.properties");
        try {
            properties=new Properties();
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
        return null;
    }

    public  Properties getProperties() {
        this.resourceLoader();
        return properties;
    }

    public  void setProperties(Properties properties) {
        this.properties = properties;
    }
}
