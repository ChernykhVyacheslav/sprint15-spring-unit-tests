package com.softserve.sprint15.service;

import com.softserve.sprint15.entity.Marathon;
import com.softserve.sprint15.entity.Sprint;
import com.softserve.sprint15.entity.Task;
import com.softserve.sprint15.exception.EntityNotFoundByIdException;
import com.softserve.sprint15.repository.MarathonRepository;
import com.softserve.sprint15.repository.SprintRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class SprintServiceImpl implements SprintService {

    private final SprintRepository sprintRepository;

    private final MarathonRepository marathonRepository;

    private final TaskService taskService;

    @Override
    public List<Sprint> getSprintByMarathonId(Long id) {
        return sprintRepository.getAllSprintsByMarathonId(id);
    }

    @Override
    public boolean addSprintToMarathon(Sprint sprint, Marathon marathon) {
        Sprint sprintEntity = sprintRepository.getOne(sprint.getId());
        Marathon marathonEntity = marathonRepository.getOne(marathon.getId());
        marathonEntity.getSprints().add(sprintEntity);
        return marathonRepository.save(marathonEntity) != null;
    }


    @Override
    public Sprint createOrUpdateSprint(Sprint sprint) {
        if (sprint.getId() != null) {
            Optional<Sprint> sprintToUpdate = sprintRepository.findById(sprint.getId());
            if (sprintToUpdate.isPresent()) {
                Sprint newSprint = sprintToUpdate.get();
                newSprint.setTitle(sprint.getTitle());
                newSprint.setStartDate(sprint.getStartDate());
                newSprint.setFinishDate(sprint.getFinishDate());
                newSprint.setMarathon(sprint.getMarathon());
                return sprintRepository.save(newSprint);
            }
        }
        return sprintRepository.save(sprint);
    }

    @Override
    public Sprint getSprintById(Long id) {
        Optional<Sprint> sprint = sprintRepository.findById(id);
        if (sprint.isPresent())
            return sprint.get();
        else throw new EntityNotFoundByIdException("No sprint for given id");
    }

    @Override
    public Sprint deleteSprint(Sprint sprint) {
        Long id = sprint.getId();
        if (id != null) {
            sprintRepository.deleteById(id);
            return sprint;
        }
        return null;
    }

    @Override
    public void deleteSprintByIdSafe(Long id) {
        Optional<Sprint> sprint = sprintRepository.findById(id);
        if (sprint.isPresent())
            sprintRepository.deleteById(id);
        else throw new EntityNotFoundByIdException("No sprint exists for given id");
    }

    @Override
    public void deleteSprintByIdUnsafe(Long id) {
        Optional<Sprint> sprint = sprintRepository.findById(id);
        if (sprint.isPresent()) {
            deleteAllTasksUnsafe(sprint.get());
            sprintRepository.deleteById(id);
        } else throw new EntityNotFoundByIdException("No sprint exists for given id");
    }

    private void deleteAllTasksUnsafe(Sprint sprint) {
        for (Task task :
                sprint.getTasks()) {
            taskService.deleteTaskByIdUnsafe(task.getId());
        }
    }
}
