package com.github.yoshi10321.todoapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by a12711 on 2015/06/29.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.CustomViewHolder> {

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public CustomViewHolder(View itemView) {
            super(itemView);

            textView = (TextView)itemView.findViewById(R.id.list_item);
        }

        public void setText(String text) {
            this.textView.setText(text);
        }
    }
}
