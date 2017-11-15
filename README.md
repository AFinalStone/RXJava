### 一、前言

RxJava等编程思想正在Android开发者中变的越来越流行。唯一的问题就是上手不容易，尤其是大部分人之前都是使用命令式编程语言。

首先要先理清这么一个问题：Rxjava和我们平时写的程序有什么不同。相信稍微对Rxjava有点认知的朋友都会深深感受到用这种方式写的程序和我们一般写的程序有很明显的不同。我们一般写的程序 统称为命令式程序，是以流程为核心的，每一行代码实际上都是机器实际上要执行的指令。而Rxjava这样的编程风格，称为函数响应式编程。函数响应式编程是以数据流为核心，处理数据的输入，处理以及输出的。这种思路写出来的代码就会跟机器实际执行的指令大相径庭。所以对于已经习惯命令式编程的我们来说，刚开始接触Rxjava的时候必然会很不适应，而且也不太符合我们平时的思维习惯。但是久而久之你会发现这个框架的精髓，尤其是你运用到大项目中的时候，简直爱不释手，随着程序逻辑变得越来越复杂，它依然能够保持代码简洁。


### 二、RxJava是什么

>a library for composing asynchronous and event-based programs using observable sequences for the Java VM
 解释：一个对于构成使用的Java虚拟机观察序列异步和基于事件的程序库

 RxJava 是一个响应式编程框架，采用观察者设计模式。所以自然少不了 Observable 和 Subscriber 这两个东东了。
 RxJava 是一个开源项目，地址：https://github.com/ReactiveX/RxJava
 RxAndroid，用于 Android 开发，添加了 Android 用的接口。地址： https://github.com/ReactiveX/RxAndroid

### 三、基本概念

网上关于RxJava的博文也有很多，我也看过许多，其中不乏有优秀的文章，但绝大部分文章都有一个共同点，就是侧重于讲RxJava中各种强大的操作符，而忽略了最基本的东西——概念，所以一开始我也看的一脸懵逼，看到后面又忘了前面的，脑子里全是问号，这个是什么，那个又是什么，这两个长得怎么那么像。举个不太恰当的例子，概念之于初学者，就像食物之于人，当你饿了，你会想吃面包、牛奶，那你为什么不去吃土呢，因为你知道面包牛奶是用来干嘛的，土是用来干嘛的。同理，前面已经说过，RxJava无非是发送数据与接收数据，那么什么是发射源，什么是接收源，这就是你应该明确的事，也是RxJava的入门条件之一，下面就依我个人理解，对发射源和接收源做个归类，以及RxJava中频繁出现的几个“单词”解释一通;

#### 1、Observable：发射源，英文释义“可观察的”，在观察者模式中称为“被观察者”或“可观察对象”；

#### 2、Observer：接收源，英文释义“观察者”，没错！就是观察者模式中的“观察者”，可接收Observable、Subject发射的数据；

```java
public interface Observer<T> {
    void onCompleted();
    void onError(Throwable e);
    void onNext(T t);
}
```

#### 3、Subscription ：Observable调用subscribe( )方法返回的对象，同样有unsubscribe( )方法，可以用来取消订阅事件；

```java
public interface Subscription {
    void unsubscribe();
    boolean isUnsubscribed();
}
```

#### 4、Subscriber：“订阅者”，也是接收源，那它跟Observer有什么区别呢？Subscriber实现了Observer接口，比Observer多了一个最重要的方法unsubscribe( )，用来取消订阅，当你不再想接收数据了，可以调用unsubscribe( )方法停止接收，Observer 在 subscribe() 过程中,最终也会被转换成 Subscriber 对象，一般情况下，建议使用Subscriber作为接收源；

```java
public abstract class Subscriber<T> implements Observer<T>, Subscription {}
```

#### 3、Subject：Subject是一个比较特殊的对象，既可充当发射源，也可充当接收源，为避免初学者被混淆，本章将不对Subject做过多的解释和使用，重点放在Observable和Observer上，先把最基本方法的使用学会，后面再学其他的都不是什么问题；

```java
public abstract class Subject<T, R> extends Observable<R> implements Observer<T> {}
```

#### 6、Action0：RxJava中的一个接口，它只有一个无参call（）方法，且无返回值，同样还有Action1，Action2…Action9等，Action1封装了含有 1 个参的call（）方法，即call（T t），Action2封装了含有 2 个参数的call方法，即call（T1 t1，T2 t2），以此类推；

```java

public interface Action0 extends Action {
    void call();
}

public interface Action extends Function {

}

public interface Function {

}
```

#### 7、Func0：与Action0非常相似，也有call（）方法，但是它是有返回值的，同样也有Func0、Func1…Func9;

```java
public interface Func0<R> extends Function, Callable<R> {
    @Override
    R call();
}

public interface Function {

}

@FunctionalInterface
public interface Callable<V> {
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    V call() throws Exception;
}
```









