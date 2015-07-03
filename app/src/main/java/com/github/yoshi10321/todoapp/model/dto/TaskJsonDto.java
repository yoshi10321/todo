package com.github.yoshi10321.todoapp.model.dto;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by mitsui yoshito on 2015/07/01.
 */
@Table(name = "Task")
public class TaskJsonDto extends Model {

    @Column(name = "json")
    public String json;

    public TaskJsonDto() {
        super();
    }
}
