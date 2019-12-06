package com.ajs.bloknot.database;

import android.os.AsyncTask;

import com.ajs.bloknot.Task;
import com.ajs.bloknot.TaskDao;
import com.ajs.bloknot.TaskListActivity;

import java.lang.ref.WeakReference;

/**
 * A simple implementation for quick and seamless deletion of database objects.
 */
public class DatabaseDelete extends AsyncTask<Task, Void, TaskDao> {

    /**
     * Interface to interpret this AsyncTask's completion from an object that uses it
     */
    public interface DbDeleteInterface {
        void completeDelete(TaskDao taskDao);
    }
    private DbDeleteInterface delegate = null;

    private WeakReference<TaskListActivity> weakReference;

    public DatabaseDelete(TaskListActivity context) {
        weakReference = new WeakReference<>(context);
        delegate = weakReference.get();
    }

    /**
     * Deletes @tasks from the application database
     */
    @Override
    protected TaskDao doInBackground(Task... tasks) {
        weakReference.get().dao.delete(tasks[0]);
        return weakReference.get().dao;
    }

    @Override
    protected void onPostExecute(TaskDao taskDao) {
        delegate.completeDelete(taskDao);
    }

}