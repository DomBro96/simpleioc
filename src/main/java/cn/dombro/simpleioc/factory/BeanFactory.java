package cn.dombro.simpleioc.factory;

import cn.dombro.simpleioc.helper.ClassHelper;
import cn.dombro.simpleioc.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanFactory {

    static final Map<Class<?>,Object> BEAN_MAP = new HashMap<>();


    /**
     * 将Bean类对象与其实例放入 Map 中
     */
    static {

        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : beanClassSet){
            Object object = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass,object);
        }
    }

    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 获取 Bean 实例
     */

    public static <T> T getBean(Class<T> cls){
        if (!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("can not get bean by class :"+cls);
        }
        return (T) BEAN_MAP.get(cls);
    }



}
