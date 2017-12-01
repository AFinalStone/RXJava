package com.example.apprxbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.apprxbus.bean.ErrorBean;
import com.example.apprxbus.bean.MyStudent;
import com.example.apprxbus.rxbus.RxBusHelper;

import io.reactivex.disposables.CompositeDisposable;

public class SecondActivity extends AppCompatActivity {
    CompositeDisposable disposables = new CompositeDisposable();

    private String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        final Button button = (Button) findViewById(R.id.b);
        RxBusHelper.doOnMainThread(MyStudent.class, disposables, new RxBusHelper.OnEventListener<MyStudent>() {
            @Override
            public void onEvent(MyStudent s) {
                button.setText(s.toString());
            }

            @Override
            public void onError(ErrorBean errorBean) {
                Log.e(TAG, "onError: "+errorBean.getDesc());
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this, ThreeActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }
}
