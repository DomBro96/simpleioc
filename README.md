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

你或许会问，这怎么可能？ Service 并没有被实例化，它现在只是一个空的引用。确实，代码中 Service 是空的，但如果可以通过某种手段，** 把 Service 这个依赖非显示的实例化，即把 Service 属性的属性值设置为一个被实例化的 Service 对象，就好像把这个依赖的实例注射进来，我们叫他依赖注入！**








 





