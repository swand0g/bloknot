package com.ajs.bloknot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskContent {

    public static final List<Task> TASKS = new ArrayList<>();

    public static final Map<String, Task> TASK_MAP = new HashMap<>();

    private static final int COUNT = 25;
    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addTask(createDummyItem("A Task", String.valueOf(i), "Some additional info", new Date()));
        }
    }

    private static void addTask(Task task) {
        TASKS.add(task);
        TASK_MAP.put(task.id, task);
    }

    private static Task createDummyItem(String name, String id, String additionalInfo, Date date) {
        return new Task(name, id, additionalInfo, date);
    }

}
