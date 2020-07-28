package com.softserve.sprint14.service;

import com.softserve.sprint14.entity.Progress;
import com.softserve.sprint14.entity.Task;
import com.softserve.sprint14.entity.User;
import com.softserve.sprint14.exception.EntityNotFoundByIdException;
import com.softserve.sprint14.repository.ProgressRepository;
import com.softserve.sprint14.repository.SprintRepository;
import com.softserve.sprint14.repository.TaskRepository;
import com.softserve.sprint14.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProgressServiceImpl implements ProgressService {

    @Autowired
    ProgressRepository progressRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    SprintRepository sprintRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Progress getProgressById(Long id) {
        Optional<Progress> progress = progressRepository.findById(id);
        if (progress.isPresent())
            return progress.get();
        else throw new EntityNotFoundByIdException("No progress for given id");
    }

    @Override
    public boolean addTaskForStudent(Task task, User user) {
        if (user.getRole() == User.Role.TRAINEE){
            Task taskEntity = taskRepository.getOne(task.getId());
            User userEntity = userRepository.getOne(user.getId());
            Progress newProgress = new Progress();
            newProgress.setTask(taskEntity);
            newProgress.setTrainee(userEntity);
            newProgress.setStatus(Progress.TaskStatus.NEW);
            createOrUpdateProgress(newProgress);
            userEntity.getProgressList().add(newProgress);
            return userRepository.save(userEntity) != null;
        }
        return false;
    }

    @Override
    public Progress createOrUpdateProgress(Progress progress) {
        if (progress.getId() != null) {
            Optional<Progress> progressToUpdate = progressRepository.findById(progress.getId());
            if (progressToUpdate.isPresent()) {
                Progress newProgress = progressToUpdate.get();
                newProgress.setStatus(progress.getStatus());
                newProgress.setTask(progress.getTask());
                newProgress.setTrainee(progress.getTrainee());
                newProgress = progressRepository.save(progress);
                return newProgress;
            }
        }
        progress = progressRepository.save(progress);
        return progress;
    }

    @Override
    public boolean setStatus(Progress.TaskStatus taskStatus, Progress progress) {
        Optional<Progress> progressEntity = progressRepository.findById(progress.getId());
        if(progressEntity.isPresent())
            progress.setStatus(taskStatus);
        return progressRepository.save(progress)!=null;
    }

    @Override
    public List<Progress> allProgressByUserIdAndMarathonId(Long userId, Long marathonId) {
        return progressRepository.findAllByTraineeIdAndTaskSprintMarathonId(userId,marathonId);
    }

    @Override
    public List<Progress> allProgressByUserIdAndSprintId(Long userId, Long sprintId) {
        return progressRepository.findAllByTraineeIdAndTaskSprintId(userId,sprintId);
    }

    @Override
    public boolean addProgressToUser(Progress progress, User user) {
        User userEntity = userRepository.getOne(user.getId());
        Progress progressEntity = progressRepository.getOne(progress.getId());
        userEntity.getProgressList().add(progressEntity);
        return userRepository.save(userEntity) != null;
    }

    @Override
    public List<Progress> getAll() {
        return progressRepository.findAll();
    }

    @Override
    public List<Progress> getAllProgressesOfTask(Task task) {
        return progressRepository.findAll()
                .stream()
                .filter(progress -> progress.getTask().equals(task))
                .collect(Collectors.toList());
    }
    
    @Override
    public Progress deleteProgress(Progress progress) {
        Long id = progress.getId();
        if(id != null) {
            progressRepository.deleteById(id);
            return progress;
        }
        return null;
    }
}
