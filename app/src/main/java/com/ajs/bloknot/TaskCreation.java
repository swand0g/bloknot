package com.ajs.bloknot;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

public class TaskCreation extends AppCompatActivity {

    TextView nameEntry;
    TextView detailsEntry;
    EditText dateEntry;
    EditText timeEntry;

    Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateSetListener;
    TimePickerDialog.OnTimeSetListener timeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(TaskListActivity.theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameEntry = findViewById(R.id.task_name_entry);
        detailsEntry = findViewById(R.id.details_entry);
        dateEntry = findViewById(R.id.date_entry);
        timeEntry = findViewById(R.id.time_entry);

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }
        };

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                updateTimeLabel();
            }
        };

    }

    public void pickDate(View view) {
        new DatePickerDialog(
                TaskCreation.this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void updateDateLabel() {
        String format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        dateEntry.setText(sdf.format(calendar.getTime()));
    }

    public void pickTime(View view) {
        new TimePickerDialog(
                this,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        ).show();
    }

    private void updateTimeLabel() {
        String format = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        timeEntry.setText(sdf.format(calendar.getTime()));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void saveTask(View view) {
        // TODO: Formalize string "nextTaskId"
        Task newTask = new Task(nameEntry.getText().toString(), getNextTaskId(), detailsEntry.getText().toString(), calendar.getTime());
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
