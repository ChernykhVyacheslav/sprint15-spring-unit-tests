package com.softserve.sprint15;


import com.softserve.sprint15.entity.Marathon;
import com.softserve.sprint15.entity.Sprint;
import com.softserve.sprint15.entity.User;
import com.softserve.sprint15.repository.MarathonRepository;
import com.softserve.sprint15.repository.SprintRepository;
import com.softserve.sprint15.repository.UserRepository;
import com.softserve.sprint15.service.MarathonService;
import com.softserve.sprint15.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class MarathonServiceTest {

    @MockBean
    private MarathonRepository marathonRepository;

    @Autowired
    private MarathonService marathonService;

    @Test
    void injectedComponentsAreNotNull() {
        Assertions.assertNotNull(marathonRepository);
        Assertions.assertNotNull(marathonService);
    }

    @Test
    public void getAllMarathonsTest() {
        List<Sprint> sprints1 = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        List<Marathon> marathons = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Marathon marathon = new Marathon();
            marathon.setId(10L + i);
            marathon.setSprints(sprints1);
            marathon.setUsers(userList);
            marathon.setTitle(" new tittle" + i);
            marathon.setClosed(true);
            marathonService.createOrUpdateMarathon(marathon);
            marathons.add(marathon);
        }
        Mockito.when(marathonRepository.findAll()).thenReturn(marathons);
        Assertions.assertEquals(marathons, marathonService.getAll());
    }

    @Test
    public void getMarathonByIdTest() {
        final Long id = 1L;
        List<Sprint> sprints = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        Marathon marathon = new Marathon();
        marathon.setClosed(true);
        marathon.setTitle(" new tittle");
        marathon.setId(id);
        marathon.setUsers(userList);
        marathon.setSprints(sprints);
        Mockito.when(marathonRepository.save(marathon)).thenReturn(marathon);
        Mockito.when(marathonRepository.findById(marathon.getId())).thenReturn(Optional.of(marathon));
        marathonService.createOrUpdateMarathon(marathon);
        marathonService.getMarathonById(marathon.getId());
    }

    @Test
    public void createOrUpdateMarathonTest() {
        List<Sprint> sprints = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        Marathon marathon = new Marathon();
        marathon.setId(1L);
        marathon.setClosed(true);
        marathon.setTitle(" new tittle");
        marathon.setUsers(userList);
        marathon.setSprints(sprints);

        Marathon expectedMarathon = new Marathon();
        expectedMarathon.setId(1L);
        expectedMarathon.setClosed(true);
        expectedMarathon.setTitle(" new tittle");
        expectedMarathon.setUsers(userList);
        expectedMarathon.setSprints(sprints);
        Mockito.when(marathonRepository.save(marathon)).thenReturn(expectedMarathon);
        Assertions.assertEquals(expectedMarathon, marathonService.createOrUpdateMarathon(marathon));
        Mockito.verify(marathonRepository, Mockito.times(1)).save(marathon);
    }

    @Test
    public void closeMarathonTest() {

        Marathon marathon = new Marathon();
        List<Sprint> sprints = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        marathon.setClosed(true);
        marathon.setTitle(" new tittle");
        marathon.setId(1L);
        marathon.setUsers(userList);
        marathon.setSprints(sprints);
        Mockito.when(marathonRepository.save(marathon)).thenReturn(marathon);
        Mockito.when(marathonRepository.findById(marathon.getId())).thenReturn(Optional.of(marathon));
        marathonService.createOrUpdateMarathon(marathon);
        marathonService.closeMarathonById(marathon.getId());

    }

    @Test
    public void openMarathonTest() {
        Marathon marathon = new Marathon();
        List<Sprint> sprints = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        marathon.setClosed(true);
        marathon.setTitle(" new tittle");
        marathon.setId(1L);
        marathon.setUsers(userList);
        marathon.setSprints(sprints);
        Mockito.when(marathonRepository.save(marathon)).thenReturn(marathon);
        Mockito.when(marathonRepository.findById(marathon.getId())).thenReturn(Optional.of(marathon));
        marathonService.createOrUpdateMarathon(marathon);
        marathonService.openMarathonById(marathon.getId());
    }

    @Test
    public void deleteMarathonTest() {
        Marathon marathon = new Marathon();
        List<Sprint> sprints = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        marathon.setClosed(true);
        marathon.setTitle(" new tittle");
        marathon.setId(1L);
        marathon.setUsers(userList);
        marathon.setSprints(sprints);
        Mockito.when(marathonRepository.save(marathon)).thenReturn(marathon);
        Mockito.when(marathonRepository.findById(marathon.getId())).thenReturn(Optional.of(marathon));
        marathonService.createOrUpdateMarathon(marathon);
        marathonService.deleteMarathonById(marathon.getId());
    }
}
