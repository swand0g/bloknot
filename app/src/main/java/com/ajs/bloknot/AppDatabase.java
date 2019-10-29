package com.ajs.bloknot;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Task.class}, version = 1)
@TypeConverters({DatabaseConverters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

}
