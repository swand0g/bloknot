package com.ajs.bloknot;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@SuppressWarnings("WeakerAccess")
@Entity
public class Task {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "first_name")
    public String name;

    @ColumnInfo(name = "details")
    public String details;

    @ColumnInfo(name = "date")
    public Date date;

    public Task(String name, int id, String details, Date date) {
        this.name = name;
        this.id = id;
        this.details = details;
        this.date = date;
    }


}
