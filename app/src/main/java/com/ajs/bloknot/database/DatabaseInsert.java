package com.ajs.bloknot.database;

import android.os.AsyncTask;

import com.ajs.bloknot.Task;
import com.ajs.bloknot.TaskDao;
import com.ajs.bloknot.TaskListActivity;

import java.lang.ref.WeakReference;

public class DatabaseInsert extends AsyncTask<Task, Void, TaskDao> {

    public interface DbInsertInterface {
        void completeInsert(TaskDao taskDao);
    }
    public DbInsertInterface delegate = null;

    private WeakReference<TaskListActivity > weakReference;

    public DatabaseInsert(TaskListActivity context) {
        weakReference = new WeakReference<>(context);
    }

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
