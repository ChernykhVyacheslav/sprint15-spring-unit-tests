package com.softserve.sprint15.repository;

import com.softserve.sprint15.entity.Marathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarathonRepository extends JpaRepository<Marathon,Long> {

    Marathon findMarathonByTitle(String title);
}
