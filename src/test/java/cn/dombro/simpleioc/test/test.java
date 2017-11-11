package cn.dombro.simpleioc.test;

import cn.dombro.simpleioc.factory.BeanFactory;
import cn.dombro.simpleioc.helper.IocLoader;
import org.junit.Test;

public class test {

    @Test
    public void iocTest(){
        IocLoader.init();
        Controller controller = BeanFactory.getBean(Controller.class);
        controller.doService();
    }
}
