package com.github.yoshi10321.todoapp.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;
import com.github.yoshi10321.todoapp.R;
import com.github.yoshi10321.todoapp.databinding.ListItemBinding;
import com.github.yoshi10321.todoapp.model.ItemDto;
import com.github.yoshi10321.todoapp.model.db.TaskDto;
import com.github.yoshi10321.todoapp.ui.activity.MainActivity;
import com.github.yoshi10321.todoapp.ui.bind.Item;
import com.github.yoshi10321.todoapp.ui.fragment.EditDialogFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by mitsui yoshito on 2015/06/29.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<ItemDto> mItemList = new ArrayList<>();
    private Gson gson = new Gson();
    private Context mContext;
    private RecyclerView mRecyclerView;


    public RecyclerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;

        TaskDto taskDto = new Select().from(TaskDto.class).executeSingle();
        if (null != taskDto && null != taskDto.json) {
            mItemList = gson.fromJson(taskDto.json, new TypeToken<ArrayList<ItemDto>>() {}.getType());
        }
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
        ItemDto itemDto = mItemList.get(position);
        Item item = new Item();
        item.setTitle(itemDto.title);
        holder.mBinding.setItem(item);
        holder.mBinding.setOnEditClick(mOnEditClick);
        holder.mBinding.executePendingBindings();
    }

    private View.OnClickListener mOnEditClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = mRecyclerView.getChildAdapterPosition(v);
            Bundle bundle = new Bundle();
            bundle.putString("title", mItemList.get(position).title);
            bundle.putInt("position", position);

            EditDialogFragment editDialogFragment = new EditDialogFragment();
            editDialogFragment.setArguments(bundle);
            editDialogFragment.show(((MainActivity) mContext).getFragmentManager(), "EditDialogFragment");
        }
    };

    public void addItem(String text) {
        ItemDto itemDto = new ItemDto();
        itemDto.title = text;
        mItemList.add(0, itemDto);
        notifyItemInserted(0);

        // get data
        TaskDto taskDto = new Select().from(TaskDto.class).executeSingle();
        if (null == taskDto) {
            taskDto = new TaskDto();
        }

        // db save
        String json = gson.toJson(mItemList);
        taskDto.json = json;
        taskDto.save();
    }

    public void updateItem(int position, String text) {
        ItemDto itemDto = mItemList.get(position);
        itemDto.title = text;
        notifyItemChanged(position);

        TaskDto taskDto = new Select().from(TaskDto.class).executeSingle();
        String json = gson.toJson(mItemList);
        taskDto.json = json;
        taskDto.save();
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private ListItemBinding mBinding;

        public CustomViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
