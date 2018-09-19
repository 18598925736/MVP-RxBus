package com.example.gc_hank.techstudy2.v.impl;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gc_hank.techstudy2.R;
import com.example.gc_hank.techstudy2.m.leader.LeaderManager;
import com.example.gc_hank.techstudy2.m.student.Student;
import com.example.gc_hank.techstudy2.m.student.StudentManager;
import com.example.gc_hank.techstudy2.m.teacher.TeacherManager;
import com.example.gc_hank.techstudy2.p.impl.LeaderPresenter;
import com.example.gc_hank.techstudy2.p.impl.StudentPresenter;
import com.example.gc_hank.techstudy2.p.impl.TeacherPresenter;
import com.example.gc_hank.techstudy2.v.IView;

/**
 * 只有一个Activity太单调了，试试多个Fragment组合
 */
public class MainActivity extends AppCompatActivity implements IView<Student> {

    private StudentPresenter studentPresenter;//放一个P层的引用
    private TeacherPresenter teacherPresenter;
    private LeaderPresenter leaderPresenter;

    private TextView tv_click_to_update, tv_name, tv_sex, tv_age;
    private View loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        studentPresenter = new StudentPresenter(new StudentManager(), this);//p层对象
        studentPresenter.register();//注册订阅,那么在你注册之后，如果总线发出消息，你就能收到，收到之后，就会执行之前的回调

        teacherPresenter = new TeacherPresenter(new TeacherManager(), null);
        leaderPresenter = new LeaderPresenter(new LeaderManager(), null);
        // 这里并没有去注册订阅，是因为，我不需要消息总线通知这个P实例，我创建这个P实例，仅仅是为了触发"数据更新的动作"
    }

    private void initView() {
        tv_click_to_update = findViewById(R.id.tv_click_to_update);
        tv_click_to_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                new Thread() {
                    @Override
                    public void run() {
                        studentPresenter.updateStudent();//执行业务逻辑,更新学生数据
                    }
                }.start();

                new Thread() {
                    @Override
                    public void run() {
                        teacherPresenter.updateTeacher();//执行业务逻辑，更新老师数据-"数据更新的动作"
                    }
                }.start();

                new Thread() {
                    @Override
                    public void run() {
                        leaderPresenter.updateLeader();//执行业务逻辑，更新领导数据-"数据更新的动作"
                    }
                }.start();
            }
        });

        tv_name = findViewById(R.id.tv_name);
        tv_sex = findViewById(R.id.tv_sex);
        tv_age = findViewById(R.id.tv_age);
        loading = findViewById(R.id.loading);

        //增加一个fragment到布局中
        addFragment(R.id.fragment_teacher, new FragmentTeacher());
        addFragment(R.id.fragment_leader, new FragmentLeader());
    }

    private void addFragment(int fragmentLayoutId, Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(fragmentLayoutId, fragment);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        studentPresenter.unregister();//activity销毁时，必须注销订阅，否则 间接引用会导致activity无法回收
    }

    @Override
    public void updateOnUi(Student student) {
        //当学生数据改变时，应该在这里写 UI更新的逻辑
        loading.setVisibility(View.INVISIBLE);
        tv_age.setText(student.age);
        tv_sex.setText(student.sex);
        tv_name.setText(student.name);
    }

    @Override
    public void errOnUi(Throwable e) {
    }
}
