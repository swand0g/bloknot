package com.ajs.bloknot.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ajs.bloknot.Task;
import com.ajs.bloknot.TaskDao;

/**
 * This Application's database containing a single table composed of user created Tasks.
 */
@Database(entities = {Task.class}, version = 1)
@TypeConverters({DatabaseConverters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

}
