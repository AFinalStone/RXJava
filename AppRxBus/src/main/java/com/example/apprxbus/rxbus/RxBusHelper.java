package com.example.apprxbus.rxbus;

import com.example.apprxbus.bean.ErrorBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sh on 2016/10/28 14:43.
 */

public class RxBusHelper {
    /**
     * 发布消息
     *
     * @param o
     */
    public static void post(Object o) {
        RxBus.getDefault().post(o);
    }

    /**
     * 接收消息,并在主线程处理
     *
     * @param aClass
     * @param disposables 用于存放消息
     * @param listener
     * @param <T>
     */
    public static <T> void doOnMainThread(Class<T> aClass, CompositeDisposable disposables, final OnEventListener<T> listener) {
        disposables.add(RxBus.getDefault().toFlowable(aClass).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<T>() {
            @Override
            public void accept(T t) throws Exception {
                listener.onEvent(t);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_RXBUS, ErrorCode.ERROR_DESC_RXBUS));
            }
        }));
    }

    public static <T> void doOnMainThread(Class<T> aClass, final OnEventListener<T> listener) {
        RxBus.getDefault().toFlowable(aClass).observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {
                        listener.onEvent(t);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_RXBUS, ErrorCode.ERROR_DESC_RXBUS));
                    }
                });
    }

    /**
     * 接收消息,并在子线程处理
     *
     * @param aClass
     * @param disposables
     * @param listener
     * @param <T>
     */
    public static <T> void doOnChildThread(Class<T> aClass, CompositeDisposable disposables, final OnEventListener<T> listener) {
        disposables.add(RxBus.getDefault().toFlowable(aClass).subscribeOn(Schedulers.newThread()).subscribe(new Consumer<T>() {
            @Override
            public void accept(T t) throws Exception {
                listener.onEvent(t);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_RXBUS, ErrorCode.ERROR_DESC_RXBUS));
            }
        }));
    }

    public static <T> void doOnChildThread(Class<T> aClass, final OnEventListener<T> listener) {
        RxBus.getDefault().toFlowable(aClass).subscribeOn(Schedulers.newThread()).subscribe(new Consumer<T>() {
            @Override
            public void accept(T t) throws Exception {
                listener.onEvent(t);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_RXBUS, ErrorCode.ERROR_DESC_RXBUS));
            }
        });
    }

    public interface OnEventListener<T> {
        void onEvent(T t);

        void onError(ErrorBean errorBean);
    }

}