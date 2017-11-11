package cn.dombro.simpleioc.helper;

import cn.dombro.simpleioc.annotation.Common;
import cn.dombro.simpleioc.annotation.Controller;
import cn.dombro.simpleioc.annotation.Service;
import cn.dombro.simpleioc.util.ClassUtil;
import cn.dombro.simpleioc.util.PropsUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * 类对象操作助手类
 */
public class ClassHelper {

    private static Set<Class<?>> CLASS_SET ;

    static {
        String basePackage = PropsUtil.getString(PropsUtil.loadProps("ioc.properties"),"base_package");
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取包名下所有的类
     * @return
     */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * 获取包名下所有 Common
     */
    public static Set<Class<?>> getCommonClassSet(){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET){
            if (cls.isAnnotationPresent(Common.class))
                classSet.add(cls);
        }
        return classSet;
    }


    /**
     * 获取包名下所有 Service
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET){
            if (cls.isAnnotationPresent(Service.class))
                classSet.add(cls);
        }
        return classSet;
    }


    /**
     * 获取包名下所有 Controller
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET){
            if (cls.isAnnotationPresent(Controller.class))
                classSet.add(cls);
        }
        return classSet;
    }


    /**
     * 获取包名下所有 Bean
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getCommonClassSet());
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }



}
