package com.ajs.bloknot.database;

import android.os.AsyncTask;

import com.ajs.bloknot.Task;
import com.ajs.bloknot.TaskListActivity;

import java.lang.ref.WeakReference;
import java.util.List;

public class DatabaseFetch extends AsyncTask<Integer, Integer, List<Task>> {

    public interface DbFetchInterface {
        void completeFetch(List<Task> fetchResults);
    }
    public DbFetchInterface delegate = null;

    private WeakReference<TaskListActivity> weakReference;

    public DatabaseFetch(TaskListActivity context) {
        weakReference = new WeakReference<>(context);
    }

    @Override
    protected List<Task> doInBackground(Integer... integers) {
        List<Task> tasks;
        if (integers.length == 1) {
            tasks = weakReference.get().dao.getTask(integers[0]);
            return tasks;
        } else {
            tasks = weakReference.get().dao.getAll();
            return tasks;
        }
    }

    @Override
    protected void onPostExecute(List<Task> tasks) {
        delegate.completeFetch(tasks);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

}
