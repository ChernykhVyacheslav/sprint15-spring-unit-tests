package com.softserve.sprint15;

import com.softserve.sprint15.config.TestApplicationConfiguration;
import com.softserve.sprint15.entity.Marathon;
import com.softserve.sprint15.entity.User;
import com.softserve.sprint15.repository.MarathonRepository;
import com.softserve.sprint15.repository.UserRepository;
import com.softserve.sprint15.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TestApplicationConfiguration.class)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MarathonRepository marathonRepository;

    @Autowired
    private UserService userService;

    @Test
    void injectedComponentsAreNotNull() {
        Assertions.assertNotNull(userRepository);
        Assertions.assertNotNull(userService);
    }

    @Test
    public void getAllUsersTest() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            User mentor = new User();
            mentor.setEmail("mentoruser" + i + "@dh.com");
            mentor.setFirstName("MentorName" + i);
            mentor.setLastName("MentorSurname" + i);
            mentor.setPassword("qwertyqwerty" + i);
            mentor.setRole(User.Role.MENTOR);
            userService.createOrUpdateUser(mentor);
            users.add(mentor);

            User trainee = new User();
            trainee.setEmail("traineeUser" + i + "@dh.com");
            trainee.setFirstName("TraineeName" + i);
            trainee.setLastName("TraineeSurname" + i);
            trainee.setPassword("qwerty^qwerty" + i);
            trainee.setRole(User.Role.TRAINEE);
            userService.createOrUpdateUser(trainee);
            users.add(trainee);
        }
        Mockito.when(userRepository.findAll()).thenReturn(users);
        Assertions.assertEquals(users, userService.getAll());
    }

    @Test
    public void getUserByIdTest() {
        User user = new User();
        user.setEmail("userUser@dh.com");
        user.setFirstName("TraineeName");
        user.setLastName("TraineeSurname");
        user.setPassword("qwerty^qwerty");
        user.setRole(User.Role.TRAINEE);
        userService.createOrUpdateUser(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Assertions.assertEquals(user, userService.getUserById(user.getId()));
    }

    @Test
    public void createOrUpdateUserTest() {
        User user = new User();
        user.setEmail("userUser@dh.com");
        user.setFirstName("TraineeName");
        user.setLastName("TraineeSurname");
        user.setPassword("qwerty^qwerty");
        user.setRole(User.Role.TRAINEE);
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setEmail("userUser@dh.com");
        expectedUser.setFirstName("TraineeName");
        expectedUser.setLastName("TraineeSurname");
        expectedUser.setPassword("qwerty^qwerty");
        expectedUser.setRole(User.Role.TRAINEE);
        Mockito.when(userRepository.save(user)).thenReturn(expectedUser);
        Assertions.assertEquals(expectedUser, userService.createOrUpdateUser(user));
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void removeUserFromMarathonTest() {
        User user = new User();
        user.setId(1L);
        user.setEmail("userUser@dh.com");
        user.setFirstName("TraineeName");
        user.setLastName("TraineeSurname");
        user.setPassword("qwerty^qwerty");
        user.setRole(User.Role.TRAINEE);
        Marathon marathon = new Marathon();
        marathon.setId(1L);
        Mockito.when(userRepository.getOne(1L)).thenReturn(user);
        Mockito.when(marathonRepository.getOne(1L)).thenReturn(marathon);
        Mockito.when(marathonRepository.save(marathon)).thenReturn(marathon);
        Assertions.assertTrue(userService.removeUserFromMarathon(user, marathon));
    }

    @Test
    public void getAllUserByRoleTest() {

        List<User> mentors = new ArrayList<>();
        List<User> trainees = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            User mentor = new User();
            mentor.setEmail("mentoruser" + i + "@dh.com");
            mentor.setFirstName("MentorName" + i);
            mentor.setLastName("MentorSurname" + i);
            mentor.setPassword("qwertyqwerty" + i);
            mentor.setRole(User.Role.MENTOR);
            userService.createOrUpdateUser(mentor);
            mentors.add(mentor);

            User trainee = new User();
            trainee.setEmail("traineeUser" + i + "@dh.com");
            trainee.setFirstName("TraineeName" + i);
            trainee.setLastName("TraineeSurname" + i);
            trainee.setPassword("qwerty^qwerty" + i);
            trainee.setRole(User.Role.TRAINEE);
            userService.createOrUpdateUser(trainee);
            trainees.add(trainee);
        }
        Mockito.when(userRepository.getAllByRole(User.Role.TRAINEE)).thenReturn(trainees);
        Mockito.when(userRepository.getAllByRole(User.Role.MENTOR)).thenReturn(mentors);

        Assertions.assertEquals(trainees, userService.getAllByRole("TRAINEE"));
        Assertions.assertEquals(mentors, userService.getAllByRole("MENTOR"));
    }

    @Test
    public void addUserToMarathonTest() {
        User user = new User();
        user.setId(1L);
        user.setEmail("userUser@dh.com");
        user.setFirstName("TraineeName");
        user.setLastName("TraineeSurname");
        user.setPassword("qwerty^qwerty");
        user.setRole(User.Role.TRAINEE);
        Marathon marathon = new Marathon();
        marathon.setId(1L);
        Mockito.when(userRepository.getOne(1L)).thenReturn(user);
        Mockito.when(marathonRepository.getOne(1L)).thenReturn(marathon);
        Mockito.when(marathonRepository.save(marathon)).thenReturn(marathon);
        Assertions.assertTrue(userService.addUserToMarathon(user, marathon));
    }

    @Test
    public void deleteUserTest() {
        User user = new User();
        user.setId(1L);
        userService.deleteUser(user);
        userService.deleteUser(user);

        Mockito.verify(userRepository, Mockito.times(2)).deleteById(1L);
    }


}
