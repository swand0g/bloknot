package com.ajs.bloknot.database;

import android.os.AsyncTask;

import com.ajs.bloknot.Task;
import com.ajs.bloknot.TaskDao;
import com.ajs.bloknot.TaskListActivity;

import java.lang.ref.WeakReference;

/**
 * A simple implementation for quick and seamless insertion of database objects.
 */
public class DatabaseInsert extends AsyncTask<Task, Void, TaskDao> {

    /**
     * Interface to interpret this AsyncTask's completion from an object that uses it
     */
    public interface DbInsertInterface {
        void completeInsert(TaskDao taskDao);
    }
    private DbInsertInterface delegate = null;

    private WeakReference<TaskListActivity > weakReference;

    public DatabaseInsert(TaskListActivity context) {
        weakReference = new WeakReference<>(context);
        delegate = weakReference.get();
    }

    /**
     * Inserts @tasks into the application database
     */
    @Override
    protected TaskDao doInBackground(Task... tasks) {
        weakReference.get().dao.insertAll(tasks);
        return weakReference.get().dao;
    }

    @Override
    protected void onPostExecute(TaskDao taskDao) {
        delegate.completeInsert(taskDao);
    }
}
