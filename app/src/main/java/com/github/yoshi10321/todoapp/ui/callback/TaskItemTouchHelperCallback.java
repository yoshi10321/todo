package com.github.yoshi10321.todoapp.ui.callback;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.github.yoshi10321.todoapp.R;
import com.github.yoshi10321.todoapp.ui.adapter.RecyclerAdapter;

/**
 * Created by mitsui yoshito on 2015/07/02.
 */
public class TaskItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private RecyclerAdapter mRecyclerAdapter;

    public TaskItemTouchHelperCallback(RecyclerAdapter adapter) {
        mRecyclerAdapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFrags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFrags, swipeFlags);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);

        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            ((CardView)viewHolder.itemView.findViewById(R.id.card)).setTranslationZ(20);
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        ((CardView)viewHolder.itemView.findViewById(R.id.card)).setTranslationZ(0);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mRecyclerAdapter.moveItemPos(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        mRecyclerAdapter.deleteItem(viewHolder.getAdapterPosition());
    }
}
