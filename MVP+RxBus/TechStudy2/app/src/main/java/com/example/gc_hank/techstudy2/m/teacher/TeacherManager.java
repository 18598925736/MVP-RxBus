package com.example.gc_hank.techstudy2.m.teacher;

import com.example.gc_hank.techstudy2.m.IDataManager;
import com.example.lib.RxBus;

/**
 * 专门从网络获取学生数据的数据管理类
 */
public class TeacherManager implements IDataManager<Teacher> {

    private static int count = 1;

    /**
     * 查询数据
     */
    @Override
    public Teacher queryData() {
        Teacher s = null;
        try {
            Thread.sleep(delayTime);//假装在从网络获取数据

            s = new Teacher();
            s.level = "1-" + count;
            s.name = "周周-" + count;
            s.salary = "工资-10000" + count++;
            RxBus.getInstance().send(s);//获取到数据后，通知所有订阅者

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return s;
    }
}
