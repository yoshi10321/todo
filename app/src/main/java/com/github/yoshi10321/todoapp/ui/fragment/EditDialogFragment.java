package com.github.yoshi10321.todoapp.ui.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.github.yoshi10321.todoapp.IntentKey;
import com.github.yoshi10321.todoapp.R;
import com.github.yoshi10321.todoapp.databinding.DialogEditBinding;
import com.github.yoshi10321.todoapp.event.BusHolder;
import com.github.yoshi10321.todoapp.event.TaskUpdateEvent;
import com.github.yoshi10321.todoapp.ui.bind.TaskData;

/**
 * Created by mitsui yoshito on 2015/07/02.
 */
public class EditDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_edit, null);

        final DialogEditBinding binding = DataBindingUtil.bind(view);
        final TaskData task = new TaskData();
        task.setText(getArguments().getString(IntentKey.TASK_TEXT.toString()));
        task.setDone(getArguments().getBoolean(IntentKey.TASK_CHECKED.toString()));
        binding.setTaskData(task);

        builder.setView(view)
                .setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TaskUpdateEvent event = new TaskUpdateEvent(binding.text.getText().toString(),
                                binding.checkBox.isChecked(),
                                getArguments().getInt(IntentKey.POSITION.toString()));
                        BusHolder.get().post(event);
                    }
                })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}
