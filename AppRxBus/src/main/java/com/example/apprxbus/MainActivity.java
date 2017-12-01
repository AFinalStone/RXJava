package com.example.apprxbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.apprxbus.bean.ErrorBean;
import com.example.apprxbus.rxbus.RxBusHelper;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {
    CompositeDisposable disposables = new CompositeDisposable();
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = (Button) findViewById(R.id.button);
        RxBusHelper.doOnMainThread(String.class, disposables, new RxBusHelper.OnEventListener<String>() {
            @Override
            public void onEvent(String s) {
                button.setText(s);
            }

            @Override
            public void onError(ErrorBean errorBean) {
                Log.e(TAG, "onError: "+errorBean.getDesc());
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
