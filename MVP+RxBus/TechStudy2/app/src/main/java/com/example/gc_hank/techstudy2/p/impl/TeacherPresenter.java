package com.example.gc_hank.techstudy2.p.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.gc_hank.techstudy2.m.IDataManager;
import com.example.gc_hank.techstudy2.m.teacher.Teacher;
import com.example.gc_hank.techstudy2.p.IPresenter;
import com.example.gc_hank.techstudy2.v.IView;
import com.example.lib.annotation.Err;
import com.example.lib.annotation.Succ;

/**
 * P层
 */
public class TeacherPresenter extends IPresenter<Teacher> {

    /**
     * @param iDataManager M层实例，让M层执行数据更新的逻辑，一般来说，这个参数不为空
     * @param iView        V层接口类的实例，对V层（比如一个Activity或Fragment的行为做出规范，必须实现该接口的方法）,这个可以为空，前提是你不需要 使用这个Presenter对象做UI操作
     */
    public TeacherPresenter(@NonNull IDataManager iDataManager, @Nullable IView iView) {//所以说，这里的@NonNull只是起到了 提醒编程者的作用?没鸟用的东西
        super(iDataManager, iView);
    }

    /**
     * 次方法由V层调用，但是业务逻辑是在P层
     */
    public void updateTeacher() {
        iDataManager.queryData();
    }


    //*************由消息总线调用的2个方法********************

    /**
     * 当消息成功传递
     *
     * @param s
     */
    @Succ
    @Override
    public void onSuccess(Teacher s) {//这里将会由RxBus在消息发出之后， 进行反射调用，
        if (iView != null) iView.updateOnUi(s);
    }

    /**
     * 当消息传递过程中发生任何问题
     *
     * @param e
     */
    @Err
    @Override
    public void onErr(Throwable e) {//如果消息传递过程中发生任何异常，都会走这里
        if (iView != null) iView.errOnUi(e);
    }
}
