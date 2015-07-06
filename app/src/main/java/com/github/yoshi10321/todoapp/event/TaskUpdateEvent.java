package com.github.yoshi10321.todoapp.event;

/**
 * Created by mitsui yoshito on 2015/07/02.
 */
public class TaskUpdateEvent {

    private String text;
    private boolean done;
    private int position;

    public TaskUpdateEvent(String text, boolean done, int position) {
        this.text = text;
        this.done = done;
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public int getPosition() {
        return position;
    }

    public boolean isDone() {
        return done;
    }
}
