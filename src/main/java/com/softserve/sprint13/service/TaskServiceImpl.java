package com.softserve.sprint13.service;

import com.softserve.sprint13.entity.Sprint;
import com.softserve.sprint13.entity.Task;
import com.softserve.sprint13.exception.EntityNotFoundByIdException;
import com.softserve.sprint13.repository.SprintRepository;
import com.softserve.sprint13.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    SprintRepository sprintRepository;


    @Override
    public Task createOrUpdateTask(Task task) {
        if (task.getId() != null) {
            Optional<Task> taskToUpdate = taskRepository.findById(task.getId());
            if (taskToUpdate.isPresent()) {
                Task newTask = taskToUpdate.get();
                newTask.setTitle(task.getTitle());
                newTask.setProgressList(task.getProgressList());
                newTask.setSprint(task.getSprint());
                newTask = taskRepository.save(newTask);
                return newTask;
            }
        }
        task = taskRepository.save(task);
        return task;
    }

    @Override
    public boolean addTaskToSprint(Task task, Sprint sprint) {
        Task taskEntity = taskRepository.getOne(task.getId());
        Sprint sprintEntity = sprintRepository.getOne(sprint.getId());
        sprintEntity.getTasks().add(taskEntity);
        taskEntity.setSprint(sprintEntity);
        return sprintRepository.save(sprintEntity) != null && taskRepository.save(taskEntity) != null;
    }

    @Override
    public Task getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent())
            return task.get();
        else throw new EntityNotFoundByIdException("No task for given id");
    }

    @Override
    public List<Task> getAllTasksOfSprint(Sprint sprint) {
        return taskRepository.findAll()
                .stream()
                .filter(task -> task.getSprint().equals(sprint))
                .collect(Collectors.toList());
    }

    @Override
    public Task deleteTask(Task task) {
        Long id = task.getId();
        if (id != null) {
            taskRepository.deleteById(id);
            return task;
        }
        return null;
    }
}
