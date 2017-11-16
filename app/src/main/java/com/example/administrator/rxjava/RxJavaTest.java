package com.example.administrator.rxjava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.RawRes;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/11/16.
 */

public class RxJavaTest implements MainContract.MainModule {

    MainContract.MainView mainView;
    Observer<String> myObserver;
    Subscriber<String> mySubscriber;

    public RxJavaTest(MainContract.MainView mainView) {
        this.mainView = mainView;
        initObserver();
    }

    private void initObserver() {
        //创建接口Observer的实例myObserver来作为观察者
        myObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d("myObserver", "onCompleted: 被执行");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d("myObserver", "onNext: 被执行" + s);
            }
        };
        //创建抽象对象Subscriber的实例mySubscriber来作为观察者
        mySubscriber = new Subscriber<String>() {

            @Override
            public void onCompleted() {
                Log.d("mySubscriber", "onCompleted: 被执行");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d("mySubscriber", "onNext: 被执行++++" + s);
            }
        };
    }


    /**
     * 这里创建发射源(被观察者)myObservable，然后测试了两种接受源(观察者)myObserver和mySubscriber，
     * 使用subscribe方法产生订阅关系；两个观察者都可以收到发射源的消息
     * 其中后者mySubscriber在订阅成功之后还可以取消订阅消息
     **/
    public void test01_ObserverAndSubscriber() {
        Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello, world!"); //发射一个"Hello, world!"的String
                subscriber.onCompleted();//发射完成,这种方法需要手动调用onCompleted，才会回调Observer的onCompleted方法
            }
        });
        //产生订阅关系,发射源开始发送消息
        myObservable.subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.immediate())
                .subscribe(myObserver);
        //产生订阅关系,发射源开始发送消息
        myObservable.subscribe(mySubscriber);
//        mySubscriber.unsubscribe();//这里可以取消订阅事件
    }


    public void test02_Just() {

        Observable.just("Hello World", "Secound 被执行")
                .subscribe(mySubscriber);
    }

    public void test03_From() {
        List<String> list = new ArrayList<>();
        list.add("from1");
        list.add("from2");
        list.add("from3");
        Observable.from(list)
                .subscribe(mySubscriber);
    }

    public void test04_Defer() {
        Observable deferObservable = Observable.defer(new Func0<Observable<String>>() {
            @Override
            //注意此处的call方法没有Subscriber参数
            public Observable<String> call() {
                return Observable.just("deferObservable");
            }
        });
        deferObservable.subscribe(mySubscriber);
    }

    public void test05_Interval() {
        Observable intervalObservable = Observable.interval(1, TimeUnit.SECONDS);//每隔一秒发送一次
        intervalObservable.subscribe(mySubscriber);
    }

    public void test06_Range() {
        Observable rangeObservable = Observable.range(10, 5);//将发送整数10，11，12，13，14
        rangeObservable.subscribe(mySubscriber);
    }

    public void test07_Repeat() {
        Observable repeatObservable = Observable.just("repeatObservable").repeat(3);//重复发射3次
        repeatObservable.subscribe(mySubscriber);
    }

    public void test08_SubscriberAction() {
        Observable.just("Hello, world!")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d("Action1", "call: 被执行++++" + s);
                    }
                });
    }

    public void test09_Lambda() {
        Observable.just("Hello, I Am Lambda!")
                .subscribe(hello -> Log.d("just", "call: 被执行++++" + hello));
    }


    public void test10_MapAndLambda() {
        Observable.just(R.mipmap.ic_launcher) // 输入类型 String
                .map(new Func1<Integer, Bitmap>() {
                    @Override
                    public Bitmap call(Integer filePath) { // 参数类型 String
                        return mainView.getBitmapFromPath(filePath); // 返回类型 Bitmap
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) { // 参数类型 Bitmap
                        mainView.showBitmap(bitmap);
                    }
                });
        Observable.just(R.mipmap.ic_launcher_round)
                .map(resID -> mainView.getBitmapFromPath(resID))
                .subscribe(bitmap -> mainView.showBitmap(bitmap));

        //map进阶
        Observable.just("Hello, Map!")
                .map(s -> s.hashCode())
                .map(hashCode -> Integer.toString(hashCode))
                .subscribe(s -> Log.d("just", "map: 被执行++++" + s));
    }


    public void test11_FlatMap() {
        query().flatMap(new Func1<List<String>, Observable<String>>() {
            @Override
            public Observable<String> call(List<String> urls) {
                return Observable.from(urls);
            }
        })
                .subscribe(mySubscriber);

        //使Lambda和flatMap
        query().flatMap(list -> Observable.from(list))
                .subscribe(url -> Log.d("just", "flatMap: 被执行++++" + url));


    }

    @Override
    public void test12_filter_take_doOnNext() {
        //使用filter对数据进行过滤
        query().flatMap(list -> Observable.from(list))
                .filter(s -> s != "url03")
                .subscribe(url -> Log.d("just", "Filter: 被执行++++" +url));
        //使用take限制输出数量
        query().flatMap(list -> Observable.from(list))
                .take(5)
                .subscribe(url -> Log.d("just", "Filter: 被执行++++" + url));
        //doOnNext()允许我们在每次输出一个元素之前做一些额外的事情
        query().flatMap(list -> Observable.from(list))
                .doOnNext(url -> Log.d("just", "doOnNext: 提前被执行++++" + url))
                .subscribe(url -> Log.d("just", "doOnNext_subscribe: 最终结果被执行++++" + url));
    }

    private Observable<List<String>> query() {
        List<String> list = new ArrayList<>();
        list.add("url01");
        list.add("url02");
        list.add("url03");
        list.add("url04");
        list.add("url05");
        list.add("url06");
        list.add("url07");
        list.add("url08");
        return Observable.just(list);
    }



}
