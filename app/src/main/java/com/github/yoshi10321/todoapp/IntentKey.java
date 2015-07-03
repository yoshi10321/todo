package com.github.yoshi10321.todoapp;

/**
 * Created by mitsui yoshito on 2015/07/02.
 */
public enum IntentKey {
    TASK_TEXT("text"),
    POSITION("position");

    private String mText;

    IntentKey(String text) {
        mText = text;
    }

    public String getText() {
        return mText;
    }
}
