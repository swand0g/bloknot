package com.ajs.bloknot;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TaskCreation extends AppCompatActivity {

    TextView name_entry;
    TextView details_entry;
    // TODO: date
    // TODO: time

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name_entry = findViewById(R.id.task_name_entry);
        details_entry = findViewById(R.id.details_entry);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void saveTask(View view) {
        // TODO: Formalize string "nextTaskId"
        Task newTask = new Task(name_entry.getText().toString(), getNextTaskId(), details_entry.getText().toString(), new Date());
        Intent intent = new Intent();
        intent.putExtra(TaskListActivity.NEW_TASK, newTask);
        setResult(TaskListActivity.CREATE_TASK, intent);
        closeThis();
    }

    private void closeThis() {
        finish();
    }

    private int getNextTaskId() {
        // Get Task data
        return getIntent().getIntExtra(TaskListActivity.NEXT_TASK_ID, -1);
    }

}
