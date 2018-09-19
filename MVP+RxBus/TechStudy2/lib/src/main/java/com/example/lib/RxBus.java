package com.example.lib;


import android.util.Log;

import com.example.lib.annotation.Succ;
import com.example.lib.util.ReflectInvokeUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 这是消息总线的实现类;
 * 此类的技术点为：
 * 1-线程安全的单例模式（同步代码块+多重判空+volatile关键字）； 消息总线类，在项目中只能存在一个，才能保证消息的统一分发
 * 2-线程安全的CopyOnWriteArraySet集合类
 * 3-注解函数+反射调用，实现“订阅者”执行函数的自动调用
 */
public class RxBus implements IDataBus {

    private final String TAG = "RxBusTag";

    //单例，我一直想知道这个volatile是神马鬼,查了一下，发散的话题太多了，后面再看
    private static volatile RxBus instance;

    public static synchronized RxBus getInstance() {

        if (instance == null) {//由于同一时间可能被多次访问，所以要加线程安全,这是第一次判空
            synchronized (RxBus.class) {//加上本类的线程同步锁
                if (instance == null) {// 这是第二次判空
                    instance = new RxBus();
                }

            }
        }
        return instance;
    }

    /**
     * 用集合保存订阅者
     */
    private Set<Object> subscribers;//用set保存所有订阅者

    private RxBus() {
        subscribers = new CopyOnWriteArraySet<>();//线程安全的set,
        // 这里解释一下，为什么CopyOnWriteArraySet是线程安全的(下面的描述可以进入源码，看add和remove方法)
        // 它在 add元素的时候，是先将原来的数组copy一份，然后在末尾加上要add的那个元素;
        // 那么删除元素的时候,它是先找出 remove的这个元素，在原来数组里面的位置index，然后把原来的数组以index为节点分为两段，每段copy一份，然后再组合。
        // 这样，保持了原来的元素顺序，又把要移除的元素给remove掉了。
        // 但是也有缺点：增加和删除元素，增删元素，都会进行元素遍历，然后复制,
        // 如果增删元素非常频繁，而元素总数又比较多的话，用这个，内存开销就比较大了。
    }

    @Override
    public void register(Object subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unRegister(Object subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void send(final Object data) {
        // 消息总线的核心类Observable
        Observable observable = Observable.create(new Observable.OnSubscribe<Object>() {// 核心类 Observable
            @Override
            public void call(Subscriber sub) {//订阅者sub
                Log.d(TAG, "send executed");// 这里会给所有的订阅者都发一次消息
                sub.onNext(data);
                        // 为观察者提供一个可观察的新项目;
                        // （核心类）Observable可能调用这个方法0次或者多次;
                        // (核心类)Observable 在执行了onCompleted或者onError之后 将不会再次调用这个方法；
                sub.unsubscribe();//取消列表中所有订阅的订阅，这将停止在相关的@code订阅者上收到通知。(暂时还没发现这个东西有什么具体作用，也许要等到具体场景才能看)
            }
        });
        observable.observeOn(AndroidSchedulers.mainThread())//  用主线程来监控,是为了保证随时可以执行UI更新吧
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError:" + e.getLocalizedMessage());//如果出错就打印日志吧
                    }

                    @Override
                    public void onNext(Object o) {
                        synchronized (RxBus.class) {
                            for (Object target : subscribers) {//循环 订阅者
                                try {
                                    ReflectInvokeUtil.callMethodByAnnotation(Succ.class, target, data);//找到方法注解相同，存在参数而且参数类型和data相同的方法，进行反射调用;
                                    //其实这个反射方法是存在于P层的,P层肯定会调用V层的UI更新代码
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });//创建一个实例


    }


}
