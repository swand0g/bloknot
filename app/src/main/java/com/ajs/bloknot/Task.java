package com.ajs.bloknot;

import java.util.Date;

public class Task {

    public String name;
    public String id;
    public String details;
    public Date date;

    public Task(String name, String id, String details, Date date) {
        this.name = name;
        this.id = id;
        this.details = details;
        this.date = date;
    }


}
