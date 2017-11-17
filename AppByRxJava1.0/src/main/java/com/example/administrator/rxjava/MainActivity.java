package com.example.administrator.rxjava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.RawRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

/*import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Action;
import io.reactivex.subjects.Subject;*/

public class MainActivity extends AppCompatActivity implements MainContract.MainView{

    MainContract.MainModule mainModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainModule = new MainModuleRxJava1(this);
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_test01:
                mainModule.test01_ObserverAndSubscriber();
                break;

            case R.id.btn_test02Just:
                mainModule.test02_Just();
                break;

            case R.id.btn_test03From:
                mainModule.test03_From();
                break;

            case R.id.btn_test04Defer:
                mainModule.test04_Defer();
                break;

            case R.id.btn_test05Interval:
                mainModule.test05_Interval();
                break;

            case R.id.btn_test06Range:
                mainModule.test06_Range();
                break;

            case R.id.btn_test07Repeat:
                mainModule.test07_Repeat();
                break;

            case R.id.btn_test08Subscriber:
                mainModule.test08_SubscriberAction();
                break;

            case R.id.btn_test09Lambda:
                mainModule.test09_Lambda();
                break;

            case R.id.btn_test10MapAndLambda:
                mainModule.test10_MapAndLambda();
                break;

            case R.id.btn_test11FlatMap:
                mainModule.test11_FlatMap();
                break;

            case R.id.btn_test12Filter_take_doOnNext:
                mainModule.test12_Filter_Take_DoOnNext();
                break;

            case R.id.btn_test13Scheduler:
                mainModule.test13_Scheduler();
                break;
        }

    }

    public Bitmap getBitmapFromPath(@RawRes int filePath) {
        InputStream inputStream = getResources().openRawResource(filePath);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }

    public void showBitmap(Bitmap bitmap) {
        ImageView imageView = (ImageView) findViewById(R.id.image_map);
        imageView.setImageBitmap(bitmap);
    }

    public void showMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

}
