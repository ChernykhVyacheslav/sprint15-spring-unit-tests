package com.softserve.sprint15.service;

import com.softserve.sprint15.entity.Sprint;
import com.softserve.sprint15.entity.Task;

import java.util.List;

public interface TaskService {

    public boolean addTaskToSprint(Task task, Sprint sprint);

    public Task getTaskById(Long id);

    public Task deleteTask(Task task);

    public Task createOrUpdateTask(Task task);

    public List<Task> getAllTasksOfSprint(Sprint sprint);

    public void deleteTaskByIdSafe(Long id);

    public void deleteTaskByIdUnsafe(Long id);
}
