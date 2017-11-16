package com.example.administrator.rxjava;

import android.graphics.Bitmap;
import android.support.annotation.RawRes;

/**
 * Created by Administrator on 2017/11/16.
 */

public class MainContract {


    public interface MainModule {
        public void test01_ObserverAndSubscriber();

        public void test02_Just();

        public void test03_From();

        public void test04_Defer();

        public void test05_Interval();

        public void test06_Range();

        public void test07_Repeat();

        public void test08_SubscriberAction();

        public void test09_Lambda();

        public void test10_MapAndLambda();

        public void test11_FlatMap();

        public void test12_filter_take_doOnNext();

    }

    public interface MainView {

        public Bitmap getBitmapFromPath(@RawRes int filePath);

        public void showBitmap(Bitmap bitmap);
    }
}
