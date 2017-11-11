package cn.dombro.simpleioc.helper;

import cn.dombro.simpleioc.annotation.Inject;
import cn.dombro.simpleioc.factory.BeanFactory;
import cn.dombro.simpleioc.util.ClassUtil;
import cn.dombro.simpleioc.util.ReflectionUtil;

import javax.lang.model.util.ElementScanner6;
import java.lang.reflect.Field;
import java.util.Map;

public class IocHelper {



    static {
        Map<Class<?>,Object> beanMap = BeanFactory.getBeanMap();
        if (! beanMap.isEmpty()){
            //遍历 BeanMap
            for (Map.Entry<Class<?>,Object>beanEntry:beanMap.entrySet()){
                //从 beanMap 中获取 Bean 类与 Bean 实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                //获取 Bean 类中所有的成员变量
                Field[] beanFields = beanClass.getDeclaredFields();
                if (beanFields.length != 0){
                    //变历成员变量
                    for (Field field : beanFields){
                        //如果成员变量有 @Inject 注解,对其进行注入
                        if (field.isAnnotationPresent(Inject.class)){
                            Object beanFieldInstance ;
                            Class<?> beanFiledClass = field.getType();
                            //判断该成员边来那个是否为一个接口
                            if (beanFiledClass.isInterface()){
                                Inject inject = field.getAnnotation(Inject.class);
                                String imp = inject.value();
                                if (! imp.equals("")){
                                    Class<?> impClass = ClassUtil.loadClass(imp,false);
                                    //如果beanFieldClass是impClass接口，就将其实例化
                                    if (beanFiledClass.isAssignableFrom(impClass)){
                                        beanFieldInstance = beanMap.get(impClass);
                                    }else {
                                        throw new RuntimeException(impClass+"is not implements of "+beanClass);
                                    }
                                }else {
                                    throw new RuntimeException(imp+" is a interface ,can not get instance");
                                }
                            }else {
                                beanFieldInstance = beanMap.get(beanFiledClass);
                            }

                            if (beanFieldInstance != null){
                                ReflectionUtil.setFiled(beanInstance,field,beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
