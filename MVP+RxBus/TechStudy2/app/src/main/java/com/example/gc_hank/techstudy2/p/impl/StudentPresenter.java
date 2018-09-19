package com.example.gc_hank.techstudy2.p.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.gc_hank.techstudy2.m.IDataManager;
import com.example.gc_hank.techstudy2.m.student.Student;
import com.example.gc_hank.techstudy2.p.IPresenter;
import com.example.gc_hank.techstudy2.v.IView;
import com.example.lib.annotation.Err;
import com.example.lib.annotation.Succ;

/**
 * P层
 */
public class StudentPresenter extends IPresenter<Student> {
    
    /**
     * @param iDataManager M层实例，让M层执行数据更新的逻辑，一般来说，这个参数不为空
     * @param iView        V层接口类的实例，对V层（比如一个Activity或Fragment的行为做出规范，必须实现该接口的方法）,这个可以为空，前提是你不需要 使用这个Presenter对象做UI操作
     */
    public StudentPresenter(@NonNull IDataManager iDataManager, @Nullable IView iView) {
        super(iDataManager, iView);
    }

    /**
     * 此方法由V层调用，但是业务逻辑是在P层
     */
    public void updateStudent() {
        iDataManager.queryData();
    }

    //*******************由消息总线调用的2个方法，**************************

    /**
     * 当消息成功传递
     * 经过测试，这个注解方法的@Succ并不能抽象到父类中，所以，算了,就放在子类
     *
     * @param s
     */
    @Succ
    @Override
    public void onSuccess(Student s) {
        if (null != iView) iView.updateOnUi(s);//这里将会由RxBus在消息发出之后， 进行反射调用，
    }

    /**
     * 当消息传递过程中发生任何问题
     *
     * @param e
     */
    @Err
    @Override
    public void onErr(Throwable e) {
        if (null != iView)//如果消息传递过程中发生任何异常，都会走这里
            iView.updateOnUi(e);//让界面知道，中途发生了异常，界面自行处理
    }
}
