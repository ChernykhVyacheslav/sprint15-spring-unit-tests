package com.softserve.sprint15.service;

import com.softserve.sprint15.entity.Marathon;

import java.util.List;

public interface MarathonService {
    public List<Marathon> getAll();
    public Marathon getMarathonById(Long id);
    public Marathon createOrUpdateMarathon(Marathon marathon);

    void openMarathonById(Long id);

    public void deleteMarathonByIdSafe(Long id);
    public void closeMarathonById(Long id);

    void deleteMarathonByIdUnsafe(Long id);
}
