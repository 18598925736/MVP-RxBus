package com.example.gc_hank.techstudy2.v;

/**
 * UI层的接口规范，
 * <p>
 * 最好是用独立的Fragment或者自定义Layout来负责独立的模块,因为 无法多次实现同一个接口，就算是你的T 类型设置成不同的类，也会编译错误
 *
 * @param <T>
 */
public interface IView<T> {

    /**
     * 当正常更新组件时
     * @param t
     */
    void updateOnUi(T t);

    /**
     * 当出现任何异常时
     * @param t
     */
    void errOnUi(Throwable t);
}
