package com.ajs.bloknot;

import java.util.Date;

@SuppressWarnings("WeakerAccess")
public class Task {

    public String name;
    public int id;
    public String details;
    public Date date;

    public Task(String name, int id, String details, Date date) {
        this.name = name;
        this.id = id;
        this.details = details;
        this.date = date;
    }


}
