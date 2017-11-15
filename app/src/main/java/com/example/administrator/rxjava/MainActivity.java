package com.example.administrator.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action;
import rx.functions.Action0;
import rx.functions.Func0;
import rx.subjects.Subject;

/*import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Action;
import io.reactivex.subjects.Subject;*/

public class MainActivity extends AppCompatActivity {


    //被观察者，发射源
    Observable observable;//实体类

    //观察者，订阅者，接收源
    Observer observer;//接口
    Subscriber subscriber;//抽象类

    //Observable调用subscribe( )方法返回的对象，同样有unsubscribe( )方法，可以用来取消订阅事件；
    Subscription subscription;//接口，Subscriber有实现这个接口

    //继承自Observable(被观察者)，实现了Observer接口
    Subject subject;//抽象类

    //接口，继承自接口Action，Action继承自接口Function
    Action0 action0;

    //接口，继承自接口Function和Callable
    Func0 func0;

    //内部接口，继承自Action1
    Observable.OnSubscribe onSubscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view){
        test01_ObserverAndSubscriber();
    }

    //这里创建发射源(被观察者)myObservable，然后创建了两种接受源(观察者)myObserver和mySubscriber，
    //接着使用subscribe方法产生订阅关系；两个观察者都可以收到发射源的消息
    //其中后者mySubscriber在订阅成功之后还可以取消订阅消息
    private void test01_ObserverAndSubscriber(){
        Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello, world!"); //发射一个"Hello, world!"的String
                subscriber.onCompleted();//发射完成,这种方法需要手动调用onCompleted，才会回调Observer的onCompleted方法
            }
        });
        //创建观察者myObserver
        Observer<String> myObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d("myObserver", "onCompleted: 被执行");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d("myObserver", "onNext: 被执行"+s);
            }
        };
        //产生订阅关系,发射源开始发送消息
        myObservable.subscribe(myObserver);

        //创建观察者mySubscriber
        Subscriber<String> mySubscriber = new Subscriber<String>() {


            @Override
            public void onCompleted() {
                Log.d("mySubscriber", "onCompleted: 被执行");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d("mySubscriber", "onNext: 被执行"+s);
            }
        };
        //产生订阅关系,发射源开始发送消息
        myObservable.subscribe(mySubscriber);
        mySubscriber.unsubscribe();//这里可以取消订阅时间
    }

}
