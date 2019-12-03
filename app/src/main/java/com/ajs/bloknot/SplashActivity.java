package com.ajs.bloknot;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.os.Parcelable;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Setup db
        AppDatabase database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "db").build();
        TaskDao dao = database.taskDao();

        // Hand off control to load data
        new DatabaseLoad(this).execute(dao);

    }

    protected void transition(List<Task> taskList) {
        Intent intent = new Intent(this, TaskListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("data", new ArrayList<Parcelable>(taskList));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private static class DatabaseLoad extends AsyncTask<TaskDao, Void, List<Task>> {

        private WeakReference<SplashActivity> weakReference;

        DatabaseLoad(SplashActivity context) {
            weakReference = new WeakReference<>(context);
        }

        @Override
        protected List<Task> doInBackground(TaskDao... taskDaos) {
            return taskDaos[0].getAll();
        }

        @Override
        protected void onPostExecute(List<Task> tasks) {
            weakReference.get().transition(tasks);
        }

    }
}
