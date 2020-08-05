package com.softserve.sprint15.service;

import com.softserve.sprint15.entity.Marathon;
import com.softserve.sprint15.entity.Sprint;

import java.util.List;

public interface SprintService {
    public List<Sprint> getSprintByMarathonId(Long id);
    public boolean addSprintToMarathon(Sprint sprint, Marathon marathon);
    public Sprint createOrUpdateSprint(Sprint sprint);
    public Sprint getSprintById(Long id);
    public Sprint deleteSprint(Sprint sprint);

    void deleteSprintByIdSafe(Long id);

    void deleteSprintByIdUnsafe(Long id);
}
