package com.example.lib;

public interface IDataBus {

    /**
     * 注册订阅者，这里订阅者的类型是Object，也就是说，不区分类型，任何对象都可以是订阅者
     *
     * @param subscriber
     */
    void register(Object subscriber);

    /**
     * 注销,用同样的引用来注销订阅者
     *
     * @param subscriber
     */
    void unRegister(Object subscriber);

    /**
     * 向订阅者发送消息的方法1
     * 参数是Object类型，具体到不同的业务场景，传递不同的class
     *
     * @param data
     */
    void send(Object data);

}