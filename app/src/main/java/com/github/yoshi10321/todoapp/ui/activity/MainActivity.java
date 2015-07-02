package com.github.yoshi10321.todoapp.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.yoshi10321.todoapp.R;
import com.github.yoshi10321.todoapp.event.BusHolder;
import com.github.yoshi10321.todoapp.event.TextUpdateEvent;
import com.github.yoshi10321.todoapp.ui.adapter.RecyclerAdapter;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends FragmentActivity {

    @InjectView(R.id.recycler_todo_list)
    RecyclerView mRecyclerView;

    @InjectView(R.id.fab)
    FloatingActionButton mFab;
    //databindingに置き換える

    private RecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerAdapter = new RecyclerAdapter(this);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusHolder.get().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusHolder.get().unregister(this);
    }

    @OnClick(R.id.fab)
    public void addItem() {
        mRecyclerAdapter.addItem("test");
    }

    // あとでbindする
    View.OnClickListener mFabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mRecyclerAdapter.addItem("test");
        }
    };

    @Subscribe
    public void updateTaskText(TextUpdateEvent event) {
        mRecyclerAdapter.updateItem(event.getPosition(), event.getText());
    }

}

