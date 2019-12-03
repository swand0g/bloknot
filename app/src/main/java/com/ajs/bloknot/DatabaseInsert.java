package com.ajs.bloknot;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class DatabaseInsert extends AsyncTask<Task, Void, TaskDao> {

    private WeakReference<TaskListActivity> weakReference;

    public DatabaseInsert(TaskListActivity taskListActivity) {
        weakReference = new WeakReference<>(taskListActivity);
    }

    @Override
    protected TaskDao doInBackground(Task... tasks) {
        weakReference.get().taskDao.insertAll(tasks);
        return weakReference.get().taskDao;
    }

}
