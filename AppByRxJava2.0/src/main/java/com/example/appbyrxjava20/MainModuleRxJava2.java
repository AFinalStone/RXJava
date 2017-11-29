package com.example.appbyrxjava20;

import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.AsyncProcessor;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Administrator on 2017/11/17.
 */

public class MainModuleRxJava2 implements MainContract.MainModule {

    MainContract.MainView mainView;

    public MainModuleRxJava2(MainContract.MainView view) {
        mainView = view;
    }

    @Override
    public void test01_ObservableAndObserver() {
        //创建一个被观察者
        Observable<Integer> observable= Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d("test01", "subscribe：:" +  e);
                e.onNext(1);
                e.onNext(2);
                e.onComplete();
            }
        });
        //创建一个观察者
        Observer<Integer> observer= new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d("test01", "onSubscribe：:" + d);
            }

            @Override
            public void onNext(Integer value) {
                Log.d("test01", "onNext：:" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("test01", "onError：:" + e);
            }

            @Override
            public void onComplete() {
                Log.d("test01", "onComplete" );
            }
        };
        observable.subscribe(observer); //建立订阅关系
    }

    @Override
    public void test02_Disposable() {
        //创建一个被观察者
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d("test02", "subscribe：:" +  e);
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onComplete();
            }
        });
        Observer<Integer> observer = new Observer<Integer>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Integer value) {
                Log.d("test02", "onNext"+value.toString());
                if (value > 3) {   // >3 时为异常数据，解除订阅
                    disposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("test02", "onError"+e );
            }

            @Override
            public void onComplete() {
                Log.d("test02", "onComplete" );
            }
        };
        observable.subscribe(observer); //建立订阅关系
    }

    @Override
    public void test03_Consumer() {
        //创建一个被观察者
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d("test03", "subscribe：:" +  e);
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onComplete();
            }
        });
        Disposable disposable = observable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                //这里接收数据项
                Log.d("test02", "accept_OnNext"+integer.toString());
//                if(integer.equals(3))
//                    throw new NullPointerException();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //这里接收onError
                Log.d("test02", "accept_OnError"+throwable );
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                //这里接收onComplete。
                Log.d("test02", "onComplete" );
            }
        });
    }

    @Override
    public void test04_Scheduler() {

    }

    @Override
    public void test05_Flowable() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {

                for(int i=0;i<10;i++){
                    e.onNext(i);
                }
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR) //指定背压处理策略，抛出异常
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("Flowable", "当前Integer：" + integer.toString());
                        Thread.sleep(1000);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Flowable", "发生错误：" + throwable.toString());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("Flowable", "Action被执行");
                    }
                });

        Flowable.range(33,10)
                .onBackpressureDrop()
                .subscribe(integer -> Log.d("range",integer.toString()));

        Flowable.range(100,10).subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);//设置请求数
            }

            @Override
            public void onNext(Integer integer) {
                Log.d("Flowable", "Flowable.range(100,10)+onNext被执行+Integer:"+integer);
            }

            @Override
            public void onError(Throwable t) {
                Log.d("Flowable", "Flowable.range(100,10)+onError被执行+Throwable:"+t);
            }

            @Override
            public void onComplete() {
                Log.d("Flowable", "Flowable.range(100,10)+onComplete被执行");
            }
        });
    }

    @Override
    public void test06_Single() {

    }

    @Override
    public void test07_Completable() {

    }

    @Override
    public void test08_Subject() {
        //Subject
        AsyncSubject<String> subject = AsyncSubject.create();
        subject.subscribe(o -> Log.d("AsyncSubject",o));//three
        subject.onNext("one");
        subject.onNext("two");
        subject.onNext("three");
        subject.onComplete();

    }

    @Override
    public void test09_Processor() {
        //Processor
        AsyncProcessor<String> processor = AsyncProcessor.create();
        processor.subscribe(o -> Log.d("AsyncProcessor",o)); //three
        processor.onNext("one");
        processor.onNext("two");
        processor.onNext("three");
        processor.onComplete();
    }


}
