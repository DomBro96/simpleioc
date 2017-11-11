package cn.dombro.simpleioc.test;

import cn.dombro.simpleioc.annotation.Inject;

@cn.dombro.simpleioc.annotation.Controller
public class Controller {

    @Inject("cn.dombro.simpleioc.test.Service")
    private IService service;

    public void doService(){
        service.addService();
    }

}
