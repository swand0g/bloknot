package com.ajs.bloknot;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.view.MenuItem;
import android.widget.EditText;

import java.util.Locale;
import java.util.Objects;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        // Set date text
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        taskDate.setText(sdf.format(task.date.getTime()));

        // Set time text
        String timeFormat = "HH:mm";
        sdf.applyPattern(timeFormat);
        taskTime.setText(sdf.format(task.date.getTime()));

        // Set detail text
        taskDetails.setText(task.details);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteTask(View view) {
        Task taskToDelete = getIntent().getParcelableExtra(TaskListActivity.TASK_FOR_DETAIL_VIEW);
        Intent intent = new Intent();
        intent.putExtra(TaskListActivity.TASK_FOR_DELETION, taskToDelete);
        setResult(TaskListActivity.DELETE_TASK, intent);
        finish();
    }

    public void editTask(View view) {
        Log.i(this.toString(), "This button is not yet implemented.");
    }

}
