package com.ajs.bloknot;

import java.util.Date;

public class Task {

    public String name;
    public String id;
    public String additionalInfo;
    public Date date;

    public Task(String name, String id, String additionalInfo, Date date) {
        this.name = name;
        this.id = id;
        this.additionalInfo = additionalInfo;
        this.date = date;
    }


}
