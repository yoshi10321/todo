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
        holder.mBinding.setTaskData(mTaskList.get(position));
        holder.mBinding.setOnEditClick(mOnEditClick);
        holder.mBinding.executePendingBindings();

        Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
        holder.mBinding.getRoot().startAnimation(animation);
    }

    private View.OnClickListener mOnEditClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = mRecyclerView.getChildAdapterPosition(v);
            Bundle bundle = new Bundle();
            bundle.putString(IntentKey.TASK_TEXT.getText(), mTaskList.get(position).getText());
            bundle.putInt(IntentKey.POSITION.getText(), position);

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

    public void updateItem(int position, String text) {
        TaskData task = mTaskList.get(position);
        task.setText(text);
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

        TaskData item = mTaskList.get(fromPosition);
        mTaskList.remove(fromPosition);
        mTaskList.add(targetPosition, item);
        notifyItemMoved(fromPosition, targetPosition);

        mTaskJsonDto.json = mGson.toJson(mTaskList);
        mTaskJsonDto.save();
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private ListItemBinding mBinding;

        public CustomViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
