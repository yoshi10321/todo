package com.github.yoshi10321.todoapp.ui.bind;

import android.databinding.BaseObservable;

import com.github.yoshi10321.todoapp.BR;
import com.google.gson.annotations.Expose;


/**
 * Created by mitsui yoshito on 2015/07/01.
 */
public class TaskData extends BaseObservable {
    @Expose
    private String text;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.taskData);
    }
}
