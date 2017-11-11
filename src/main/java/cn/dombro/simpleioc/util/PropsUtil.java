package cn.dombro.simpleioc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 获取属性文件 properties 工具类
 */
public class PropsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载属性文件
     */

    public static Properties loadProps(String propFileName){
        Properties properties = null;
        try(InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFileName)) {
                if (inputStream == null){
                    throw new FileNotFoundException(propFileName + "file is not found");
                }
                properties = new Properties();
                properties.load(inputStream);
        } catch (IOException e) {
             LOGGER.error("load properties file failure",e);
        }
        return properties;
    }

    public static String getString(Properties properties,String key){
        return getString(properties,key,"");
    }

    public static String getString(Properties properties, String key, String defaultValue) {
        String value = defaultValue;
        if (properties.containsKey(key)){
            value = properties.getProperty(key);
        }
        return value;
    }

    public static int getInt(Properties properties,String key){
        return getInt(properties,key,0);
    }

    public static int getInt(Properties properties,String key,int defaultValue){
        int value = defaultValue;
        if (properties.containsKey(key)){
            value = Integer.valueOf(properties.getProperty(key));
        }
        return value;
    }

    public static boolean getBoolean(Properties properties,String key){
        return getBoolean(properties,key,false);
    }

    public static boolean getBoolean(Properties properties,String key,boolean defaultValue){
        boolean value = defaultValue;
        if (properties.containsKey(key)){
            value = Boolean.valueOf(properties.getProperty(key));
        }
        return value;


    }



}
