package com.example.appbyrxjava20;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    public MainContract.MainModule mainModule;

    @BindView(R.id.btn_test01ObservableAndObserver)
    Button btnTest01ObservableAndObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainModule = new MainModuleRxJava2(this);
        btnTest01ObservableAndObserver.setText("測試1");
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
            case R.id.btn_test08_Subject:
                mainModule.test08_Subject();
                break;
            case R.id.btn_test09_Processor:
                mainModule.test09_Processor();
                break;
            default:
        }
    }
}
