package com.github.yoshi10321.todoapp;

/**
 * Created by mitsui yoshito on 2015/07/02.
 */
public enum IntentKey {
    TASK_TEXT("text"),
    TASK_CHECKED("checked"),
    POSITION("position");

    private String mString;

    IntentKey(String str) {
        mString = str;
    }

    @Override
    public String toString() {
        return mString;
    }

}
