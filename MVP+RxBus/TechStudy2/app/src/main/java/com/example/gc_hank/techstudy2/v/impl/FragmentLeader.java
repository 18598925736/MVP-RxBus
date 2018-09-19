package com.example.gc_hank.techstudy2.v.impl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gc_hank.techstudy2.R;
import com.example.gc_hank.techstudy2.m.leader.Leader;
import com.example.gc_hank.techstudy2.m.leader.LeaderManager;
import com.example.gc_hank.techstudy2.p.impl.LeaderPresenter;
import com.example.gc_hank.techstudy2.v.IView;

public class FragmentLeader extends Fragment implements IView<Leader> {

    private LeaderPresenter presenter;//放一个P层的引用

    private TextView tv_data, tv_load_data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_leader, container, false);
        init(root);
        return root;
    }

    private void init(View root) {
        tv_data = root.findViewById(R.id.tv_data);
        //这是普通MVP的写法，直接由V层进行获取数据以及更新数据到UI。
        //数据更新的动作只能在本类
        tv_load_data = root.findViewById(R.id.tv_load_data);
        tv_load_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Leader s = presenter.updateLeader();
                updateOnUi(s);
            }
        });
        //这是带消息总线的MVP写法，订阅消息，然后等待消息。
        // 数据更新的动作，在其他组件，或者在本类都可以
        presenter = new LeaderPresenter(new LeaderManager(), this);//p层对象
        presenter.register();//注册订阅,那么在你注册之后，如果总线发出消息，你就能收到，收到之后，就会执行之前的回调
        //经过对比，前面一种方式比较直观，但是不够灵活，如果数据在其他组件更新了，那么这个组件是无法收到更新
        //后面这种消息总线的方式，订阅消息，然后等到接收消息。消息更新可以在任何组件，不仅仅是本类。只是这种方式需要对消息总线有较深理解,整个框架有点绕，需要花时间DEBUG.
        //考虑了一下，保留两种方式,视情况而定
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unregister();
    }

    @Override
    public void updateOnUi(Leader leader) {
        tv_data.setText(leader.name + " - \n" + leader.position + "- \n");
    }

    @Override
    public void errOnUi(Throwable e) {

    }
}
