package com.ajs.bloknot;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import java.util.Date;

public class TaskCreation extends AppCompatActivity {

    TextView name_entry;
    TextView details_entry;
    // TODO: date;
    // TODO: time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name_entry = findViewById(R.id.task_name_entry);
        details_entry = findViewById(R.id.details_entry);

    }

    public void saveTask(View view) {
        TaskContent.addTask(new Task(name_entry.getText().toString(), TaskContent.TASKS.size()+1, details_entry.getText().toString(), new Date()));
        closeThis();
    }

    private void closeThis() {
        finish();
    }

}
