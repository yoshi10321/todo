package com.github.yoshi10321.todoapp.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.github.yoshi10321.todoapp.BR;

/**
 * Created by mitsui yoshito on 2015/06/29.
 */
public class ItemDto extends BaseObservable {
    public String title;

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }
}
