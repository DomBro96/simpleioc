# simpleioc 简单实现 IOC 框架

## 目录


- [1. 前言](#1)
- [2. 实现思路](#2)
  - [2.1 依赖注入](#2.1)
  - [2.2 你要掌握](#2.2)
  - [2.3 简要设计](#2.3)
- [3. 打开姿势](#3)
  - [3.1 使用姿势](#3.1)
  - [3.2 普通注入](#3.2)
  - [3.3 接口注入](#3.3)
- [4. 需要完善](#4) 
- [5. jar下载地址](#5)



<h2 id="1">1. 前言</h2>

spring 两大特性——反转控制和事务，一直对 Spring 的反转控制(IOC)很感兴趣(实际上我更愿意称呼它为依赖注入,在下面会解释原因），所以想自己写一个简单的 IOC 框架，严格来说并不能叫框架，充其量是一个IOC工具。至于为什么不写一个简单的事务工具，还用问吗？ 我不会呀！！总之，这是一个玩心很大的项目。



<h2 id="2">2. 实现思路</h2>
 

<h3 id="2.1">2.1 依赖注入</h3>



首先你得知道什么叫反转控制... 太多的教程，太多的视频把这个概念解释的模糊不清，**所以我要用反转控制(ioc)的别名———依赖注入，来解释这个概念！**


- 一个例子


```
class Controller{

	private Service service;

	public doService(){
     	service = new Service();
		service.insert();
    }
}

```

例子中，你注意到 Controller 和 Service 的关系了吗？Controller 依赖于 Service ，即 Service 是 Controller 的依赖。Controller 每次调用 Service 的方法都需要亲自实例化 Service 对象，这是我们不希望看到的。至于为什么 [我这里有篇笔记，希望可以帮到你](https://github.com/DomBro96/MyNotes/blob/master/OOP/Java%E5%B7%A5%E5%8E%82%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F.md)。

我们想到看到的是 Controller 只做它想做的事情(调用 Service 对象的方法)，而不需要在 Controller 中去实例化 Service，像这样：


```
class Controller{

	private Service service;

	public doService(){
		service.insert();
    }
}
```

你或许会问，这怎么可能？ Service 并没有被实例化，它现在只是一个空的引用。确实，代码中 Service 是空的，但如果可以通过某种手段，**把 Service 这个依赖非显示的实例化，即把 Service 属性的属性值设置为一个被实例化的 Service 对象，就好像把这个依赖的实例注射进来，我们叫他依赖注入！**






<h3 id="2.2">2.2 你要掌握</h3>


不想显示的实例化，那就需要使用 Java 反射的知识了，通过反射在程序运行时动态的为需要注入的属性实例化(newInstance)。所以要想了解依赖注入你要掌握：

- Java 反射 : 


包括加载类对象Class<?>，创建类对象实例，为类对象的属性赋值，为方法赋值等。


- Java注解

Java注解实际上就是接口，simpleioc工具 中定义了四个注解，分别是 @Controler，@Service，@Common，@Inject。通通过这是个注解，工具在初始化时判断对哪些类通过反射实例化、注入哪些属性等。


- 单例模式


simpleioc 初始化时，将通过反射得到的Class对象与对象实例放入Map中，并且将依赖的实例注入到Map的实例中，所以 simpleioc  所有的对象实例操作都是单例模式下的。



<h3 id="2.3">2.3 简要设计</h3>


- 1.首先获得指定包名下所有的类对象 Set< Class<?>>

- 2.遍历类对象 Set< Class<?>> ，将类对象上面标有 @Controller，@Service，@Common 注解的类取出，作为被 simpleioc 管理的 Bean ，即依赖注入在 Bean 上进行。

- 3.遍历 Bean 的 Set集 ，通过反射将其实例化，把 Bean 的Class<?>和实例放入BeanMap中

- 4.遍历 BeanMap ，查看每个类中的属性是否带有 @Inject 注解，如果带有说明是要注入的对象，从 BeanMap 中通过该属性的类型获得该属性的实例，即实现依赖注入。



<h2 id="3">3. 打开姿势</h2>

simpleioc 这是一个练手工具，还有很多地方需要优化。


<h3 id="3.1">3.1 使用姿势</h3>

- 1.配置项

目前工具只需要配置一下你的基础应用包名，用来加载该基础报名下的运行时类类。你必须在 `recourse` 路径下创建一个叫 `ioc.properties` 的属性文件，并在里面配置 base_package参数

```
base_package=cn.dombro.simpleioc
```

- 2.工具初始化


simpleioc 通过执行几个 Helper类 中的静态代码，将工具初始化，达到依赖注入的目的。IocLoader.init() 方法会加载 Helper 类（执行静态代码），所以只需压在项目启动时使用 IocLoader.init() 就可以达到工具初始化的目的。



<h3 id="3.2">3.2 普通注入</h3>

上面提到，simpleioc 只会注入被管理的Bean实例，也就是使用了 @Controller，@Service，@Common 注解的类，即 @Inject 注解只能注入Bean的实例。

- 例 将Service注入到Controller中


```
@Service
class Service{

public void insert(){
	balabalabala...
}

}

@Controller
class Controller{
      
	@Inject
	private Service service;

	public doService(){
		service.insert();
    }
}

``` 


至于使用哪种注解，这个看个人个人需要，只是不要影响代码的阅读性就好。

 
<h3 id="3.3">3.3 接口注入</h3>

这个功能不太完善，想到有时候可能只想依赖于某个接口，就考虑了对 @Inject 
的改良，如果注入某接口的实现类可以在接口上使用 @Inject("实现类全类名")
但后来考虑这个做法实在有点鸡肋(T T)。


- 例 

```
public interface IService {

    void addService();
}

public class Controller {

    @Inject("cn.dombro.simpleioc.test.Service")
    private IService service;

    public void doService(){
        service.addService();
    }

}

```


<h2 id="4">4. 需要完善</h2>

simpleioc 作为一个练习项目，需要完善的地方很多


- 没有考虑线程安全问题
 
<h2 id="5">5. jar下载地址</h2>

simpleioc是个练习的小程序,没有做成远程maven,想体验的同学可以[戳这里](https://github.com/DomBro96/simpleioc/blob/master/target/simpleioc-1.0.jar)下载jar包。







