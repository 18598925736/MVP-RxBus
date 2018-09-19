package com.example.gc_hank.techstudy2.m.student;

import com.example.gc_hank.techstudy2.m.IDataManager;
import com.example.lib.RxBus;

/**
 * 专门从网络获取学生数据的数据管理类
 */
public class StudentManager implements IDataManager<Student> {

    private static int count = 1;

    /**
     * 查询数据
     */
    @Override
    public Student queryData() {
        Student s = null;
        try {
            Thread.sleep(delayTime);//假装在从网络获取数据

            s = new Student();
            s.age = "22-" + count;
            s.name = "周周-" + count;
            s.sex = "男-" + count++;
            RxBus.getInstance().send(s);//获取到数据后，通知所有订阅者

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return s;
    }
}
