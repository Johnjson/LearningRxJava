package com.click.guide.learningrxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    Observer<String> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        creatObserver();
        creatObservable();
    }


    // 创建被观察者
    private void creatObservable() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("张三来了");
                emitter.onComplete();
            }
        });

        //建立关系
        observable.subscribe(observer);
    }

    // 创建观察者
    private void creatObserver() {

        observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("observer", "observer onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.e("observer", "observer onNext " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("observer", "observer onError " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("observer", "observer onComplete");
            }
        };
    }

}
