package com.github.yoshi10321.todoapp.ui.bind;

import android.databinding.BaseObservable;

import com.github.yoshi10321.todoapp.BR;


/**
 * Created by mitsui yoshito on 2015/07/01.
 */
public class Item extends BaseObservable {
    private String title;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.item);
    }
}
