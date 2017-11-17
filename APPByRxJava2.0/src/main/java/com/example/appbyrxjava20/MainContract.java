package com.example.appbyrxjava20;

import android.graphics.Bitmap;
import android.support.annotation.RawRes;

/**
 * Created by Administrator on 2017/11/17.
 */

public class MainContract {

    public interface MainModule {

        public void test01_ObservableAndObserver();
        public void test02_Disposable();
        public void test03_Consumer();
        public void test04_Scheduler();
        public void test05_Flowable();
        public void test06_Single();
        public void test07_Completable();

    }

    public interface MainView {

    }
}
