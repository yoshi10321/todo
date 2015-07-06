package com.github.yoshi10321.todoapp.ui.bind;

import android.databinding.BaseObservable;

import com.github.yoshi10321.todoapp.BR;


/**
 * Created by mitsui yoshito on 2015/07/01.
 */
public class TaskData extends BaseObservable {

    private String text;

    private boolean done;

    public String getText() {
        return text;
    }

    public boolean isDone() {
        return done;
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.taskData);
    }

    public void setDone(boolean done) {
        this.done = done;
        notifyPropertyChanged(BR.taskData);
    }
}
