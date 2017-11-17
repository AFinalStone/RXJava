package com.example.appbyrxjava20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    public MainContract.MainModule mainModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainModule = new MainModuleRxJava2(this);
    }

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_test01ObservableAndObserver:
                mainModule.test01_ObservableAndObserver();
                break;

            case R.id.btn_test02Disposable:
                mainModule.test02_Disposable();
                break;

            case R.id.btn_test03Consumer:
                mainModule.test03_Consumer();
                break;

            case R.id.btn_test04Scheduler:
                mainModule.test04_Scheduler();
                break;
            case R.id.btn_test05Flowable:
                mainModule.test05_Flowable();
                break;
            case R.id.btn_test06Single:
                mainModule.test06_Single();
                break;
            case R.id.btn_test07Completable:
                mainModule.test07_Completable();
                break;
            default:
        }
    }
}
