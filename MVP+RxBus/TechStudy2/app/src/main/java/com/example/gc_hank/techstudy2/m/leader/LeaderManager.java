package com.example.gc_hank.techstudy2.m.leader;

import com.example.gc_hank.techstudy2.m.IDataManager;
import com.example.lib.RxBus;

public class LeaderManager implements IDataManager<Leader> {

    private static long count = 1;

    @Override
    public Leader queryData() {
        Leader s = null;
        try {
            Thread.sleep(delayTime);//假装```在从网络获取数据

            s = new Leader();
            s.position = "领导职位-" + count;
            s.name = "领导名字-" + count++;
            RxBus.getInstance().send(s);//获取到数据后，通知所有订阅者


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return s;
    }
}
