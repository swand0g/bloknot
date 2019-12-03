package com.ajs.bloknot;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM tasks")
    List<Task> getAll();

    @Query("SELECT * FROM tasks WHERE id IN (:taskIds)")
    List<Task> loadAllByIds(int[] taskIds);

    @Query("SELECT * FROM tasks WHERE id=:id")
    List<Task> getTask(int id);

    @Insert
    void insertAll(Task... tasks);

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);

}

