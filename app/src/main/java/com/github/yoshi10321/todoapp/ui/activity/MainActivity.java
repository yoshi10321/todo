package com.github.yoshi10321.todoapp.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.github.yoshi10321.todoapp.R;
import com.github.yoshi10321.todoapp.databinding.ActivityMainBinding;
import com.github.yoshi10321.todoapp.event.BusHolder;
import com.github.yoshi10321.todoapp.event.TextUpdateEvent;
import com.github.yoshi10321.todoapp.ui.adapter.RecyclerAdapter;
import com.github.yoshi10321.todoapp.ui.callback.TaskItemTouchHelperCallback;
import com.squareup.otto.Subscribe;



public class MainActivity extends FragmentActivity {

    private RecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setOnClick(mFabClickListener);

        binding.recyclerTodoList.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerAdapter = new RecyclerAdapter(this);
        binding.recyclerTodoList.setAdapter(mRecyclerAdapter);

        // swipe,drag処理
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TaskItemTouchHelperCallback(mRecyclerAdapter));
        itemTouchHelper.attachToRecyclerView(binding.recyclerTodoList);
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

    private View.OnClickListener mFabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mRecyclerAdapter.addItem("task");
        }
    };

    @Subscribe
    public void updateTaskText(TextUpdateEvent event) {
        mRecyclerAdapter.updateItem(event.getPosition(), event.getText());
    }

}

