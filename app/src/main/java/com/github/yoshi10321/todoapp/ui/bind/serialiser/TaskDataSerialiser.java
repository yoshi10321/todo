package com.github.yoshi10321.todoapp.ui.bind.serialiser;

import com.github.yoshi10321.todoapp.ui.bind.TaskData;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by mitsui yoshito on 2015/07/03.
 */
public class TaskDataSerialiser implements JsonSerializer<TaskData> {
    @Override
    public JsonElement serialize(TaskData taskData, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", taskData.getText());
        return jsonObject;
    }
}
