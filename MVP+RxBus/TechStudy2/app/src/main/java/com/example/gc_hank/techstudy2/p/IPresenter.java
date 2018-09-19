package com.example.gc_hank.techstudy2.p;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.gc_hank.techstudy2.m.IDataManager;
import com.example.gc_hank.techstudy2.v.IView;
import com.example.lib.RxBus;

/**
 * P层抽象
 *
 * @param <T> 泛型，指定 数据格式
 */
public abstract class IPresenter<T> {

    protected IView iView;//V层引用
    protected IDataManager iDataManager;//M层引用

    /**
     * @param iDataManager M层实例，让M层执行数据更新的逻辑，一般来说，这个参数不为空
     * @param iView        V层接口类的实例，对V层（比如一个Activity或Fragment的行为做出规范，必须实现该接口的方法）,这个可以为空，前提是你不需要 使用这个Presenter对象做UI操作
     */
    public IPresenter(@NonNull IDataManager iDataManager, @Nullable IView iView) {
        this.iDataManager = iDataManager;
        this.iView = iView;
    }

    //注册订阅以及注销订阅
    public void register() {
        RxBus.getInstance().register(this);// 所以说，订阅者其实就是P层的这个对象
    }

    public void unregister() {
        RxBus.getInstance().unRegister(this);
    }

    /**
     * 当消息成功传递
     *
     * @param s 由创建对象时指定的class类型决定这个T的类型
     */
    public abstract void onSuccess(T s);//

    /**
     * 当消息传递过程中发生任何问题
     *
     * @param e
     */
    public abstract void onErr(Throwable e);
}
