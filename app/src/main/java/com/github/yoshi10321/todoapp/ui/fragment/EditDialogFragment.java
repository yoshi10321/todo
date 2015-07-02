package com.github.yoshi10321.todoapp.ui.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.github.yoshi10321.todoapp.R;
import com.github.yoshi10321.todoapp.event.BusHolder;
import com.github.yoshi10321.todoapp.event.TextUpdateEvent;

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
        final EditText editText = (EditText) view.findViewById(R.id.text);
        editText.setText(getArguments().getString("title"));
        builder.setView(view)
                .setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextUpdateEvent event = new TextUpdateEvent(editText.getText().toString(), getArguments().getInt("position"));
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
