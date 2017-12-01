package com.example.apprxbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.apprxbus.bean.ErrorBean;
import com.example.apprxbus.bean.MyStudent;
import com.example.apprxbus.bean.School;
import com.example.apprxbus.rxbus.RxBus;
import com.example.apprxbus.rxbus.RxBusHelper;

public class ThreeActivity extends AppCompatActivity {

    private String TAG = "ThreeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        RxBusHelper.doOnMainThread(School.class, new RxBusHelper.OnEventListener<School>() {
            @Override
            public void onEvent(School school) {
                Toast.makeText(ThreeActivity.this, school.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(ErrorBean errorBean) {
                Log.e(TAG, "onError: " + errorBean.getDesc());
            }
        });
    }

    public void onClick(View view) {
        boolean flag = RxBus.getDefault().hasSubscribers();
        Log.e(TAG, "onClick: " + flag);
//        Integer integer = 1;
        RxBusHelper.post("测试消息被发送");
        RxBusHelper.post(new MyStudent("小明", "男"));
        RxBusHelper.post(new School("清华大学", 100));
    }
}
