package com.softserve.sprint14.service;

import com.softserve.sprint14.entity.Marathon;

import java.util.List;

public interface MarathonService {
    public List<Marathon> getAll();
    public Marathon getMarathonById(Long id);
    public Marathon createOrUpdateMarathon(Marathon marathon);
    public void deleteMarathonById(Long id);
    public void closeMarathonById(Long theId);
}
