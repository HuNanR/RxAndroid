package com.example.rxandroid_master;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tv);

//        /*throttleFirst防止重复点击*/
//        Observable<Object> clicks = RxView.clicks(tv).throttleFirst(1000, TimeUnit.MILLISECONDS);
//        clicks.subscribe(new Consumer<Object>() {
//            @Override
//            public void accept(Object o) throws Exception {
//                /*响应单击click事件*/
//                Toast.makeText(MainActivity.this, "别点我！", Toast.LENGTH_SHORT).show();
//            }
//        });
//
        Observable<List<Object>> listObservable = RxView.clicks(tv)
                .buffer(1000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread(), 2);
        listObservable.subscribe(new Consumer<List<Object>>() {
            @Override
            public void accept(List<Object> objects) throws Exception {
                if (objects.size() == 2) {
                    /*响应双击click事件*/
                    Toast.makeText(MainActivity.this, "双击我！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        RxView.clicks(tv).throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Intent intent = new Intent(MainActivity.this, MessageActivity.class);
                        startActivityForResult(intent, 0);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
