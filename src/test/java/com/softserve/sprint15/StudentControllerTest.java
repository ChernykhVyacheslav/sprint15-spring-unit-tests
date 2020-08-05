package com.softserve.sprint15;

import com.softserve.sprint15.config.TestApplicationConfiguration;
import com.softserve.sprint15.dto.MarathonDTO;
import com.softserve.sprint15.dto.UserDTO;
import com.softserve.sprint15.entity.Marathon;
import com.softserve.sprint15.entity.User;
import com.softserve.sprint15.service.MarathonService;
import com.softserve.sprint15.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TestApplicationConfiguration.class)
@AutoConfigureMockMvc
@Transactional
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private MarathonService marathonService;

    @Test
    @Rollback
    public void getAllStudentsTest() throws Exception {
        List<User> students = userService.getAllByRole(User.Role.TRAINEE.toString());

        mockMvc.perform(MockMvcRequestBuilders.get("/students"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("students"))
                .andExpect(MockMvcResultMatchers.model().attribute("students", students));
    }

    @Test
    @Rollback
    public void getAllStudentsForMarathonTest() throws Exception {
        List<User> students = userService.getAllByRole(User.Role.TRAINEE.toString());
        Marathon marathon = new Marathon();
        marathon.setTitle("Marathon1");
        marathon = marathonService.createOrUpdateMarathon(marathon);

        mockMvc.perform(MockMvcRequestBuilders.get("/students/{marathonId}/", marathon.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("students"))
                .andExpect(MockMvcResultMatchers.model().attribute("students", students))
                .andExpect(MockMvcResultMatchers.model().attributeExists("marathon"))
                .andExpect(MockMvcResultMatchers.model().attribute("marathon", marathon));
    }

    @Test
    @Rollback
    public void deleteUserTest() throws Exception {
        User trainee = new User();
        trainee.setEmail("traineeUser@dh.com");
        trainee.setFirstName("TraineeName");
        trainee.setLastName("TraineeSurname");
        trainee.setPassword("qwerty^qwerty");
        trainee.setRole(User.Role.TRAINEE);
        trainee = userService.createOrUpdateUser(trainee);

        mockMvc.perform(MockMvcRequestBuilders.get("/students/delete/{student_id}/", trainee.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @Rollback
    public void removeUserFromMarathonTest() throws Exception {
        User trainee = new User();
        trainee.setEmail("traineeUser@dh.com");
        trainee.setFirstName("TraineeName");
        trainee.setLastName("TraineeSurname");
        trainee.setPassword("qwerty^qwerty");
        trainee.setRole(User.Role.TRAINEE);
        trainee = userService.createOrUpdateUser(trainee);

        Marathon marathon = new Marathon();
        marathon.setTitle("Marathon1");
        marathon = marathonService.createOrUpdateMarathon(marathon);

        userService.addUserToMarathon(trainee, marathon);

        mockMvc.perform(MockMvcRequestBuilders.get("/students/remove/{student_id}/{marathon_id}",
                trainee.getId(), marathon.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @Rollback
    public void editUserFormTest() throws Exception {
        User user = new User();
        user.setEmail("userUser@dh.com");
        user.setFirstName("TraineeName");
        user.setLastName("TraineeSurname");
        user.setPassword("qwerty^qwerty");
        user.setRole(User.Role.TRAINEE);
        user = userService.createOrUpdateUser(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/students/edit/{studentId}/", user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", user));
    }

    @Test
    @Rollback
    public void viewUserTest() throws Exception {
        User user = new User();
        user.setEmail("userUser@dh.com");
        user.setFirstName("TraineeName");
        user.setLastName("TraineeSurname");
        user.setPassword("qwerty^qwerty");
        user.setRole(User.Role.TRAINEE);
        user = userService.createOrUpdateUser(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/students/view/{studentId}/", user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", user));
    }


    @Test
    @Rollback
    public void testAddStudentToMarathonForm() throws Exception {
        Marathon marathon = new Marathon();
        marathon.setTitle("Marathon1");
        marathon = marathonService.createOrUpdateMarathon(marathon);

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            User trainee = new User();
            trainee.setEmail("traineeUser" + i + "@dh.com");
            trainee.setFirstName("TraineeName" + i);
            trainee.setLastName("TraineeSurname" + i);
            trainee.setPassword("qwerty^qwerty" + i);
            trainee.setRole(User.Role.TRAINEE);
            userService.createOrUpdateUser(trainee);
            users.add(trainee);
        }

        mockMvc.perform(MockMvcRequestBuilders.get("/students/{marathonId}/add", marathon.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attribute("users", users))
                .andExpect(MockMvcResultMatchers.model().attributeExists("marathon"))
                .andExpect(MockMvcResultMatchers.model().attribute("marathon", marathon));
    }

    @Test
    @Rollback
    public void testAddStudentToMarathonPost() throws Exception {
        Marathon marathon = new Marathon();
        marathon.setTitle("Marathon1");
        marathon = marathonService.createOrUpdateMarathon(marathon);

        String[] users = new String[2];
        for (int i = 0; i < 2; i++) {
            User trainee = new User();
            trainee.setEmail("traineeUser" + i + "@dh.com");
            trainee.setFirstName("TraineeName" + i);
            trainee.setLastName("TraineeSurname" + i);
            trainee.setPassword("qwerty^qwerty" + i);
            trainee.setRole(User.Role.TRAINEE);
            users[i] = userService.createOrUpdateUser(trainee).toString();
        }

        mockMvc.perform(MockMvcRequestBuilders.post("/students/{marathonId}/add", marathon.getId())
                .flashAttr("marathon",
                        new MarathonDTO(marathon.getId(), "Marathon1", false,
                                users, new ArrayList<>())))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @Rollback
    public void testShowFormForAddStudent() throws Exception {
        User user = new User();
        user.setRole(User.Role.TRAINEE);

        mockMvc.perform(MockMvcRequestBuilders.get("/students/add/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", user));
    }

    @Test
    @Rollback
    public void testAddStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/students/edit/save/")
                .param("email", "test@email.com")
                .param("firstName", "fName")
                .param("lastName", "lName")
                .param("password", "password1")
                .param("role", User.Role.TRAINEE.toString()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @Rollback
    public void addMarathonsToUserGetTest() throws Exception {
        List<Marathon> marathons = new ArrayList<>();
        Marathon marathon = new Marathon();
        marathon.setTitle("Marathon1");
        marathon = marathonService.createOrUpdateMarathon(marathon);
        marathons.add(marathon);
        Marathon marathon2 = new Marathon();
        marathon2.setTitle("Marathon2");
        marathon2 = marathonService.createOrUpdateMarathon(marathon2);
        marathons.add(marathon2);
        Marathon marathon3 = new Marathon();
        marathon3.setTitle("Marathon3");
        marathon3 = marathonService.createOrUpdateMarathon(marathon3);
        marathons.add(marathon3);

        User user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email@email.com");
        user.setRole(User.Role.TRAINEE);
        user.setPassword("123456");
        user = userService.createOrUpdateUser(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/students/{userId}/addMarathons/", user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("marathons"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", user))
                .andExpect(MockMvcResultMatchers.model().attribute("marathons", marathons));
    }

    @Test
    @Rollback
    public void add3MarathonsToUserPostTest() throws Exception {
        String[] marathons = new String[3];
        Marathon marathon = new Marathon();
        marathon.setTitle("Marathon1");
        marathon = marathonService.createOrUpdateMarathon(marathon);
        marathons[0] = marathon.toString();
        Marathon marathon2 = new Marathon();
        marathon2.setTitle("Marathon2");
        marathon2 = marathonService.createOrUpdateMarathon(marathon2);
        marathons[1] = marathon2.toString();
        Marathon marathon3 = new Marathon();
        marathon3.setTitle("Marathon3");
        marathon3 = marathonService.createOrUpdateMarathon(marathon3);
        marathons[2] = marathon3.toString();

        User user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email@email.com");
        user.setRole(User.Role.TRAINEE);
        user.setPassword("123456");
        user = userService.createOrUpdateUser(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/students/{userId}/add-marathons-to-student/", user.getId())
                .flashAttr("user",
                        new UserDTO(user.getId(), "firstName", "lastName", "email@email.com", "TRAINEE", "123456",
                                marathons, new ArrayList<>())))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @Rollback
    public void add1MarathonToUserPostTest() throws Exception {
        String[] marathons = new String[1];
        Marathon marathon = new Marathon();
        marathon.setTitle("Marathon1");
        marathon = marathonService.createOrUpdateMarathon(marathon);
        marathons[0] = marathon.toString();

        User user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email@email.com");
        user.setRole(User.Role.TRAINEE);
        user.setPassword("123456");
        user = userService.createOrUpdateUser(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/students/{userId}/add-marathons-to-student/", user.getId())
                .flashAttr("user",
                        new UserDTO(user.getId(), "firstName", "lastName", "email@email.com", "TRAINEE", "123456",
                                marathons, new ArrayList<>())))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

}
