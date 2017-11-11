package cn.dombro.simpleioc.helper;

import cn.dombro.simpleioc.factory.BeanFactory;
import cn.dombro.simpleioc.util.ClassUtil;

public class IocLoader {

    /**
     * 初始化 ioc
     */
    public static void init(){
        Class<?> [] classList = {ClassHelper.class,BeanFactory.class,IocHelper.class};
        for (Class<?> cls : classList){
            ClassUtil.loadClass(cls.getName(),true);
        }
    }
}
