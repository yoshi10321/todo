package com.github.yoshi10321.todoapp.event;

/**
 * Created by mitsui yoshito on 2015/07/02.
 */
public class TextUpdateEvent {

    private String text;
    private int position;

    public TextUpdateEvent(String text, int position) {
        this.text = text;
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public int getPosition() {
        return position;
    }
}
