package com.example.gc_hank.techstudy2.p.impl;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.gc_hank.techstudy2.m.IDataManager;
import com.example.gc_hank.techstudy2.m.leader.Leader;
import com.example.gc_hank.techstudy2.p.IPresenter;
import com.example.gc_hank.techstudy2.v.IView;
import com.example.lib.annotation.Err;
import com.example.lib.annotation.Succ;

public class LeaderPresenter extends IPresenter<Leader> {

    /**
     * @param iDataManager M层实例，让M层执行数据更新的逻辑，一般来说，这个参数不为空
     * @param iView        V层接口类的实例，对V层（比如一个Activity或Fragment的行为做出规范，必须实现该接口的方法）,这个可以为空，前提是你不需要 使用这个Presenter对象做UI操作
     */
    public LeaderPresenter(@NonNull IDataManager iDataManager, @Nullable IView iView) {
        super(iDataManager, iView);
    }

    /**
     * 此方法由V层调用，但是业务逻辑是在P层
     */
    public Leader updateLeader() {
        Leader s = (Leader) iDataManager.queryData();
        return s;
    }

    @Succ
    @Override
    public void onSuccess(Leader s) {
        if (null != iView) iView.updateOnUi(s);//这里将会由RxBus在消息发出之后， 进行反射调用，
    }

    @Err
    @Override
    public void onErr(Throwable e) {
        if (null != iView) iView.updateOnUi(e);//让界面知道，中途发生了异常，界面自行处理
    }
}
