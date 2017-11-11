package cn.dombro.simpleioc.test;

@cn.dombro.simpleioc.annotation.Service
public class Service implements IService{

    public void addService(){
        System.out.println("这里是添加服务。。。");
    }

}
