package com.github.yoshi10321.todoapp.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.activeandroid.query.Select;
import com.github.yoshi10321.todoapp.IntentKey;
import com.github.yoshi10321.todoapp.R;
import com.github.yoshi10321.todoapp.databinding.ListItemBinding;
import com.github.yoshi10321.todoapp.model.dto.TaskJsonDto;
import com.github.yoshi10321.todoapp.ui.activity.MainActivity;
import com.github.yoshi10321.todoapp.ui.bind.TaskData;
import com.github.yoshi10321.todoapp.ui.bind.serialiser.TaskDataSerialiser;
import com.github.yoshi10321.todoapp.ui.fragment.EditDialogFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by mitsui yoshito on 2015/06/29.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<TaskData> mTaskList = new ArrayList<>();
    private Gson mGson;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private TaskJsonDto mTaskJsonDto;
    private CustomViewHolder mHolder;

    public RecyclerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;

        initGson();

        mTaskJsonDto = new Select().from(TaskJsonDto.class).executeSingle();
        if (null != mTaskJsonDto && null != mTaskJsonDto.json) {
            mTaskList = mGson.fromJson(mTaskJsonDto.json, new TypeToken<ArrayList<TaskData>>() {}.getType());
        } else {
            mTaskJsonDto = new TaskJsonDto();
        }

    }

    private void initGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(TaskData.class, new TaskDataSerialiser());
        gsonBuilder.setPrettyPrinting();
        mGson = gsonBuilder.create();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemBinding binding = DataBindingUtil.inflate(mInflater, R.layout.list_item, parent, false);
        return new CustomViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        mHolder = holder;
        mHolder.binding.setTaskData(mTaskList.get(position));
        mHolder.binding.setOnEditClick(mOnEditClick);
        mHolder.binding.executePendingBindings();

        Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
        mHolder.binding.getRoot().startAnimation(animation);
    }

    private View.OnClickListener mOnEditClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = mRecyclerView.getChildAdapterPosition(v);
            Bundle bundle = new Bundle();
            bundle.putString(IntentKey.TASK_TEXT.toString(), mTaskList.get(position).getText());
            bundle.putBoolean(IntentKey.TASK_CHECKED.toString(), mTaskList.get(position).isDone());
            bundle.putInt(IntentKey.POSITION.toString(), position);

            EditDialogFragment editDialogFragment = new EditDialogFragment();
            editDialogFragment.setArguments(bundle);
            editDialogFragment.show(((MainActivity) mContext).getFragmentManager(), "EditDialogFragment");
        }
    };

    public void addItem(String text) {
        TaskData task = new TaskData();
        task.setText(text);
        mTaskList.add(task);
        notifyItemInserted(mTaskList.size());

        // db save
        mTaskJsonDto.json = mGson.toJson(mTaskList);;
        mTaskJsonDto.save();
    }

    public void updateItem(int position, String text, boolean done) {
        TaskData task = mTaskList.get(position);
        task.setText(text);
        task.setDone(done);
        notifyItemChanged(position);

        mTaskJsonDto.json = mGson.toJson(mTaskList);
        mTaskJsonDto.save();
    }

    public void deleteItem(int position) {
        mTaskList.remove(position);
        notifyItemRemoved(position);

        mTaskJsonDto.json = mGson.toJson(mTaskList);
        mTaskJsonDto.save();
    }

    public void moveItemPos(int fromPosition, int targetPosition) {

        TaskData task = mTaskList.get(fromPosition);
        mTaskList.remove(fromPosition);
        mTaskList.add(targetPosition, task);
        notifyItemMoved(fromPosition, targetPosition);

        mTaskJsonDto.json = mGson.toJson(mTaskList);
        mTaskJsonDto.save();
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private ListItemBinding binding;

        public CustomViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
