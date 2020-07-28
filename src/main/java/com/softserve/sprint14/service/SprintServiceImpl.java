package com.softserve.sprint14.service;

import com.softserve.sprint14.entity.Marathon;
import com.softserve.sprint14.entity.Sprint;
import com.softserve.sprint14.exception.EntityNotFoundByIdException;
import com.softserve.sprint14.repository.MarathonRepository;
import com.softserve.sprint14.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SprintServiceImpl implements SprintService {
    @Autowired
    SprintRepository sprintRepository;
    @Autowired
    MarathonRepository marathonRepository;

    @Override
    public List<Sprint> getSprintByMarathonId(Long id) {
        Optional<Marathon> marathon = marathonRepository.findById(id);
        if (marathon.isPresent())
            return marathon.get().getSprints();
        else throw new EntityNotFoundByIdException("No marathon for given id");
    }

    @Override
    public boolean addSprintToMarathon(Sprint sprint, Marathon marathon) {
        Sprint sprintEntity = sprintRepository.getOne(sprint.getId());
        Marathon marathonEntity = marathonRepository.getOne(marathon.getId());
        sprintEntity.setMarathon(marathonEntity);
        marathonEntity.getSprints().add(sprintEntity);
        return marathonRepository.save(marathonEntity) != null && sprintRepository.save(sprintEntity) != null;
    }


    @Override
    public Sprint createOrUpdateSprint(Sprint sprint) {
        if (sprint.getId() != null) {
            Optional<Sprint> sprintToUpdate = sprintRepository.findById(sprint.getId());
            if (sprintToUpdate.isPresent()) {
                Sprint newSprint = sprintToUpdate.get();
                newSprint.setFinishDate(sprint.getFinishDate());
                newSprint.setStartDate(sprint.getStartDate());
                newSprint.setTitle(sprint.getTitle());
                newSprint.setMarathon(sprint.getMarathon());
                newSprint = sprintRepository.save(newSprint);
                return newSprint;
            }
        }
        sprint = sprintRepository.save(sprint);
        return sprint;
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
}
