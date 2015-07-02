package com.github.yoshi10321.todoapp.model.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.github.yoshi10321.todoapp.ui.bind.Item;

import java.util.ArrayList;

/**
 * Created by mitsui yoshito on 2015/07/01.
 */
@Table(name = "Task")
public class TaskDto extends Model {

    @Column(name = "value")
    public String json;

    public TaskDto() {
        super();
    }
}
