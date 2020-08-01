package com.softserve.sprint15;

import com.softserve.sprint15.entity.User;
import com.softserve.sprint15.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void injectedComponentsAreNotNull(){
        Assertions.assertNotNull(userRepository);
        Assertions.assertNotNull(entityManager);
    }

    @Test
    public void newUserTest() {
        User user = new User();
        user.setRole(User.Role.TRAINEE);
        user.setEmail("newUser@email.com");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPassword("password");

        userRepository.save(user);
        User actual = userRepository.findUserByEmail("newUser@email.com");

        Assertions.assertEquals("firstName", actual.getFirstName());
    }

    @Test
    public void getAllByRoleTest() {
        List<User> expectedMentors = new ArrayList<>();
        List<User> expectedTrainees = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            User mentor = new User();
            mentor.setEmail("mentoruser" + i + "@dh.com");
            mentor.setFirstName("MentorName" + i);
            mentor.setLastName("MentorSurname" + i);
            mentor.setPassword("qwertyqwerty" + i);
            mentor.setRole(User.Role.MENTOR);
            userRepository.save(mentor);
            expectedMentors.add(mentor);

            User trainee = new User();
            trainee.setEmail("traineeUser" + i + "@dh.com");
            trainee.setFirstName("TraineeName" + i);
            trainee.setLastName("TraineeSurname" + i);
            trainee.setPassword("qwerty^qwerty" + i);
            trainee.setRole(User.Role.TRAINEE);
            userRepository.save(trainee);
            expectedTrainees.add(trainee);
        }

        List<User> actualMentors = userRepository.getAllByRole(User.Role.MENTOR);
        List<User> actualTrainees = userRepository.getAllByRole(User.Role.TRAINEE);

        Assertions.assertEquals(expectedMentors, actualMentors);
        Assertions.assertEquals(expectedTrainees, actualTrainees);
    }

    @Test
    public void getUserTest() {
        User user = new User();
        user.setEmail("userUser@dh.com");
        user.setFirstName("TraineeName");
        user.setLastName("TraineeSurname");
        user.setPassword("qwerty^qwerty");
        user.setRole(User.Role.TRAINEE);
        userRepository.save(user);
        User user2 = userRepository.findUserByEmail("userUser@dh.com");
        Assertions.assertNotNull(user);
        Assertions.assertEquals(user2.getFirstName(), user.getFirstName());
        Assertions.assertEquals(user2.getLastName(), user.getLastName());
        Assertions.assertEquals(user2.getEmail(), user.getEmail());
    }

    @Test
    public void deleteUserTest() {
        User user = new User();
        user.setEmail("userUser@dh.com");
        user.setFirstName("TraineeName");
        user.setLastName("TraineeSurname");
        user.setPassword("qwerty^qwerty");
        user.setRole(User.Role.TRAINEE);
        userRepository.save(user);
        userRepository.delete(user);
        Throwable e = new Throwable();
        try {
            userRepository.getOne(user.getId());
        } catch (JpaObjectRetrievalFailureException ex) {
            e = ex;
        }
        Assertions.assertEquals(JpaObjectRetrievalFailureException.class, e.getClass());
    }

    @Test
    public void findAllUsersTest() {
        User user = new User();
        user.setEmail("userUser@dh.com");
        user.setFirstName("TraineeName");
        user.setLastName("TraineeSurname");
        user.setPassword("qwerty^qwerty");
        user.setRole(User.Role.TRAINEE);
        userRepository.save(user);
        Assertions.assertNotNull(userRepository.findAll());
    }

    @Test
    public void deleteByUserIdTest() {
        User user = new User();
        user.setEmail("userUser@dh.com");
        user.setFirstName("TraineeName");
        user.setLastName("TraineeSurname");
        user.setPassword("qwerty^qwerty");
        user.setRole(User.Role.TRAINEE);
        User tempUser = userRepository.save(user);
        userRepository.deleteById(tempUser.getId());
        Throwable e = new Throwable();
        try {
            userRepository.getOne(user.getId());
        } catch (JpaObjectRetrievalFailureException ex) {
            e = ex;
        }
        Assertions.assertEquals(JpaObjectRetrievalFailureException.class, e.getClass());
    }

}
