package com.ajs.bloknot.database;

import android.os.AsyncTask;

import com.ajs.bloknot.Task;
import com.ajs.bloknot.TaskDao;
import com.ajs.bloknot.TaskListActivity;

import java.lang.ref.WeakReference;

public class DatabaseDelete extends AsyncTask<Task, Void, TaskDao> {

    public interface DbDeleteInterface {
        void completeDelete(TaskDao taskDao);
    }
    public DbDeleteInterface delegate = null;

    private WeakReference<TaskListActivity> weakReference;

    DatabaseDelete(TaskListActivity context) {
        weakReference = new WeakReference<>(context);
    }

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