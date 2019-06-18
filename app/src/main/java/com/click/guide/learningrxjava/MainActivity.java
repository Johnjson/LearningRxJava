package com.click.guide.learningrxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AndroidException;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Observer<String> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        creatTest1();
//        creatTest2();



    }

    private void creatThread(){
        Observable.just("大宝来了").
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("just", "onSubscribe ");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("just", "onNext  " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("just", "onError  " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("just", "onComplete ");
                    }
                });

//        Observable.just("大宝来了").
//                subscribeOn(Schedulers.newThread()).
//                observeOn(AndroidSchedulers.mainThread()).
//                subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.e("just", "onSubscribe ");
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.e("just", "onNext  " + s);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("just", "onError  " + e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e("just", "onComplete ");
//                    }
//                });


//        Observable.just("大宝来了").
//                subscribeOn(Schedulers.single()).
//                observeOn(AndroidSchedulers.mainThread()).
//                subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.e("just", "onSubscribe ");
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.e("just", "onNext  " + s);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("just", "onError  " + e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e("just", "onComplete ");
//                    }
//                });

//        Observable.just("大宝来了").
//                subscribeOn(Schedulers.computation()).
//                observeOn(AndroidSchedulers.mainThread()).
//                subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.e("just", "onSubscribe ");
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.e("just", "onNext  " + s);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("just", "onError  " + e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e("just", "onComplete ");
//                    }
//                });

        Observable.just("大宝来了").
                subscribeOn(Schedulers.trampoline()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("just", "onSubscribe ");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("just", "onNext  " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("just", "onError  " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("just", "onComplete ");
                    }
                });
    }


    private void creatTest1() {
        // 创建观察者
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


        // 创建被观察者
        // 第一种方法
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("张三来了");
                emitter.onComplete();
            }
        });

        // 第二种方法
        Observable<String> observable1 = Observable.just("李四来了");

        //建立关系
        observable.subscribe(observer);
        observable1.subscribe(observer);
    }

    private void creatTest2() {

        Subscriber subscriber = new Subscriber() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.e("observer", "subscriber onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Object o) {
                Log.e("observer", "subscriber onNext " + o.toString());
            }

            @Override
            public void onError(Throwable t) {
                Log.e("observer", "subscriber onError");
            }

            @Override
            public void onComplete() {
                Log.e("observer", "subscriber onComplete");
            }
        };

        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                emitter.onNext("王五来了");
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);

        Flowable<String> flowable1 = (Flowable<String>) Flowable.just("赵六来了");

        flowable.subscribe(subscriber);
        flowable1.subscribe(subscriber);
    }


}
