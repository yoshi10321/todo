package com.github.yoshi10321.todoapp.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.yoshi10321.todoapp.BR;
import com.github.yoshi10321.todoapp.R;
import com.github.yoshi10321.todoapp.databinding.ListItemBinding;
import com.github.yoshi10321.todoapp.model.ItemDto;

import java.util.ArrayList;

/**
 * Created by mitsui yoshito on 2015/06/29.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<ItemDto> mItemDtoList = new ArrayList<>();

    public RecyclerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemBinding binding = DataBindingUtil.inflate(mInflater, R.layout.list_item, parent, false);

        return new CustomViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        ItemDto item = mItemDtoList.get(position);
        holder.mBinding.setVariable(BR.item, item);
        holder.mBinding.executePendingBindings();
    }

    public void addItem() {
        ItemDto itemDto = new ItemDto();
        itemDto.setTitle("test");
        mItemDtoList.add(0, itemDto);
        notifyItemInserted(0);
    }

    @Override
    public int getItemCount() {
        return mItemDtoList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private ListItemBinding mBinding;

        public CustomViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
