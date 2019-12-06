package com.ajs.bloknot.database;

import android.os.AsyncTask;

import com.ajs.bloknot.Task;
import com.ajs.bloknot.TaskListActivity;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * A simple implementation for quick and seamless fetching of database objects.
 */
public class DatabaseFetch extends AsyncTask<Integer, Integer, List<Task>> {

    /**
     * Interface to interpret this AsyncTask's completion from an object that uses it
     */
    public interface DbFetchInterface {
        void completeFetch(List<Task> fetchResults);
    }
    private DbFetchInterface delegate = null;

    private WeakReference<TaskListActivity> weakReference;

    public DatabaseFetch(TaskListActivity context) {
        weakReference = new WeakReference<>(context);
        delegate = weakReference.get();
    }

    /**
     * Fetches all Task objects from the application database
     */
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
