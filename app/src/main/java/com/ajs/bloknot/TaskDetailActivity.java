package com.ajs.bloknot;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

import static com.ajs.bloknot.TaskDetailFragment.ARG_ITEM_ID;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link TaskListActivity}.
 */
public class TaskDetailActivity extends AppCompatActivity {

    EditText taskDate;
    EditText taskTime;
    EditText taskDetails;
    Task task;
    Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateSetListener;
    TimePickerDialog.OnTimeSetListener timeSetListener;
    Boolean didModifyTask = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(TaskListActivity.theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ARG_ITEM_ID, String.valueOf(getIntent().getIntExtra(ARG_ITEM_ID, -1)));
            TaskDetailFragment fragment = new TaskDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.item_detail_container, fragment).commit();
        }

        // Setup Task data to be displayed
        task = getIntent().getParcelableExtra(TaskListActivity.TASK_FOR_DETAIL_VIEW);
        taskDate = findViewById(R.id.taskDateEdit);
        taskTime = findViewById(R.id.taskTimeEdit);
        taskDetails = findViewById(R.id.taskDetailsEdit);
        taskDate.setEnabled(false);
        taskTime.setEnabled(false);
        taskDetails.setEnabled(false);

        // Set date text
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        taskDate.setText(sdf.format(task.date.getTime()));

        // Set time text
        String timeFormat = "HH:mm";
        sdf.applyPattern(timeFormat);
        taskTime.setText(sdf.format(task.date.getTime()));

        // Define date and time pickers
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

        // Set detail text
        taskDetails.setText(task.details);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (didModifyTask) {
                task.date = calendar.getTime();
                task.details = taskDetails.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(TaskListActivity.TASK_FOR_UPDATE, task);
                setResult(TaskListActivity.UPDATE_TASK, intent);
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void editTask(View view) {
        taskDate.setEnabled(true);
        taskTime.setEnabled(true);
        taskDetails.setEnabled(true);
        didModifyTask = true;
    }

    public void modifyDate(View view) {
        new DatePickerDialog(
                TaskDetailActivity.this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void updateDateLabel() {
        String format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        taskDate.setText(sdf.format(calendar.getTime()));
    }

    public void modifyTime(View view) {
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
        taskTime.setText(sdf.format(calendar.getTime()));
    }

}
