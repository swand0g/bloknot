package com.ajs.bloknot;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class DatabaseFetch extends AsyncTask<Integer, Integer, List<Task>> {

    private WeakReference<TaskListActivity> weakReference;
    private int progress;

    public DatabaseFetch(TaskListActivity taskListActivity) {
        weakReference = new WeakReference<>(taskListActivity);
    }

    @Override
    protected List<Task> doInBackground(Integer... integers) {
        List<Task> tasks;
        if (integers.length == 1) {
            tasks = weakReference.get().taskDao.getTask(integers[0]);
            publishProgress(1);
            return tasks;
        } else {
            tasks = weakReference.get().taskDao.getAll();
            publishProgress(1);
            return tasks;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (values[0] == 0) {
            progress = 1;
        }
    }

    @Override
    protected void onPostExecute(List<Task> tasks) {
        weakReference.get().databaseFetchResult = tasks;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

}
