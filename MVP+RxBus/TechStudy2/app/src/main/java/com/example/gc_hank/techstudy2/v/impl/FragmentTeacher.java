package com.example.gc_hank.techstudy2.v.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gc_hank.techstudy2.R;
import com.example.gc_hank.techstudy2.m.teacher.Teacher;
import com.example.gc_hank.techstudy2.m.teacher.TeacherManager;
import com.example.gc_hank.techstudy2.p.impl.TeacherPresenter;
import com.example.gc_hank.techstudy2.v.IView;

public class FragmentTeacher extends Fragment implements IView<Teacher> {

    private TeacherPresenter presenter;//放一个P层的引用

    private TextView tv_data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_teacher, container, false);
        init(root);
        return root;
    }

    private void init(View root) {
        tv_data = root.findViewById(R.id.tv_data);
        presenter = new TeacherPresenter(new TeacherManager(), this);//p层对象
        presenter.register();//注册订阅,那么在你注册之后，如果总线发出消息，你就能收到，收到之后，就会执行之前的回调
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unregister();
    }

    @Override
    public void updateOnUi(Teacher teacher) {
        tv_data.setText(teacher.level + " - \n" + teacher.name + "- \n" + teacher.salary);
    }

    @Override
    public void errOnUi(Throwable e) {

    }
}

