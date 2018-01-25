package com.zengyong.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text) TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        initObservable();
        initObserver();
    }

    private void initObservable() {
        /*
        步骤一：
        * 创建被观察者对象
        * create()是 RxJava最基本的创造事件序列的方法
         */
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            //定义需要发送的事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });

        //方法1：Just(T ...)将直接传入的参数一次发送出来
        Observable<String> observable2 = Observable.just("A", "B", "C");

        //方法二：from(T[]) /from(Iterable<? extends T>)将传入的数组/Iterable 拆分称具体的对象后，依次发送出来
        String[] words = {"A","B","C"};
        Observable observable3 = Observable.fromArray(words);


        //步骤二：
        //方法一：采用Observer接口
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Logger.d("开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer integer) {
                Logger.d("对 next 事件做出的响应"+integer);
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("对 onError 事件做出的响应");
            }

            @Override
            public void onComplete() {
                Logger.d("对 onComplete 事件做出的响应");
            }
        };
        //方式二：采用Subscriber 抽象类
        //Subscriber类 = RxJava内置的一个实现了Observer的抽象类，对Observer接口进行了扩展。
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                Logger.d("开始采用subscribe连接");
            }

            @Override
            public void onNext(String s) {
                Logger.d("对 next 事件做出的响应"+s);
            }

            @Override
            public void onError(Throwable t) {
                Logger.d("对 onError 事件做出的响应");
            }

            @Override
            public void onComplete() {
                Logger.d("对 onComplete 事件做出的响应");
            }
        };
        /*
        相同点：二者基本使用方法完全一直（实质上，在RxJava的 subscribe过程中，Observer总是会先被转换成Subscriber再使用）
        不同点：Subscriber抽象类对Observer接口进行扩展，新增了两个方法：
                1. onStart()：在还未响应时间前调用，用于做一些初始化工作。
                2. unSubscribe()：用于取消订阅。在该方法调用后，观察者不在接收和响应事件
                   调用该方法前，先使用 isUnSubscribed()判断状态，确定被观察者Observer是否还持有观察者Subscriber的引用，
                   如果引用不能及时释放，就会出现内存泄漏。
         */


        //步骤三：通过订阅（Subscribe）连接观察者和被观察者

        observable1.subscribe(observer);


    }

    private void initObserver() {
        Observable.just("hello").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Logger.d(s);
            }
        });
    }

}
