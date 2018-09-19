package com.example.gc_hank.techstudy2.m;

/**
 * M层，负责，而且只负责数据的更新。
 *
 * 具体点说就是，数据更新的发起，当我们需要去更新数据时，使用M层的实现类
 */
public interface IDataManager<T> {
    long delayTime = 500;

    T queryData();
}
