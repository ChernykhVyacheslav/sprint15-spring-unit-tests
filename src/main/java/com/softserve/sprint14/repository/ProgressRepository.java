package com.softserve.sprint14.repository;

import com.softserve.sprint14.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepository extends JpaRepository<Progress,Long> {

    List<Progress> findAllByTraineeIdAndTaskSprintMarathonId(Long userId, Long marathonId);

    List<Progress> findAllByTraineeIdAndTaskSprintId(Long userId, Long sprintId);

//    @Query(value = "select p.* from progress p \n" +
//            "            left join users on p.trainee_id=users.id \n" +
//            "            left join task on p.task_id=task.id \n" +
//            "            left join sprint on task.sprint_id=sprint.id \n" +
//            "            left join marathon on sprint.marathon_id=marathon.id \n" +
//            "            where users.id=:userId and marathon.id=:marathonId",
//            nativeQuery=true)
//    List<Progress> allProgressByUserIdAndMarathonId(@Param("userId") Long userId, @Param("marathonId") Long marathonId);

//    @Query(value = "select p.* from progress p \n" +
//            "            left join users on p.trainee_id=users.id \n" +
//            "            left join task on p.task_id=task.id \n" +
//            "            left join sprint on task.sprint_id=sprint.id \n" +
//            "            where users.id=:userId and sprint.id=:sprintId",
//            nativeQuery=true)
//    List<Progress> allProgressByUserIdAndSprintId(@Param("userId") Long userId,@Param("sprintId") Long sprintId);
}
