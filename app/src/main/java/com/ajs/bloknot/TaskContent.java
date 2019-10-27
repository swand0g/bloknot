package com.ajs.bloknot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TaskContent {

    static final List<Task> TASKS = new ArrayList<>();

    static final Map<Integer, Task> TASK_MAP = new HashMap<>();

    static {
        // Add some sample items.
        addTask(createDummyItem("Shoping", 1, "Buy dairy products", new Date()));
        addTask(createDummyItem("Homework", 2, "Math homework", new Date()));
    }

    static void addTask(Task task) {
        TASKS.add(task);
        TASK_MAP.put(task.id, task);
    }

    private static Task createDummyItem(String name, int id, String additionalInfo, Date date) {
        return new Task(name, id, additionalInfo, date);
    }

}
