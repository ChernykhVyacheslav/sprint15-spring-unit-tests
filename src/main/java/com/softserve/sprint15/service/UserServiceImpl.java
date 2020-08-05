package com.softserve.sprint15.service;

import com.softserve.sprint15.entity.Marathon;
import com.softserve.sprint15.entity.User;
import com.softserve.sprint15.exception.EntityNotFoundByIdException;
import com.softserve.sprint15.repository.MarathonRepository;
import com.softserve.sprint15.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final MarathonRepository marathonRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MarathonRepository marathonRepository) {
        this.userRepository = userRepository;
        this.marathonRepository = marathonRepository;
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users.isEmpty() ? new ArrayList<>() : users;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByIdException(("No existing user found with id "+id)));
    }

    @Override
    public User createOrUpdateUser(User user) {
        if (user.getId() != null) {
            Optional<User> userToUpdate = userRepository.findById(user.getId());
            if (userToUpdate.isPresent()) {
                User newUser = userToUpdate.get();
                newUser.setEmail(user.getEmail());
                newUser.setFirstName(user.getFirstName());
                newUser.setLastName(user.getLastName());
                newUser.setPassword(user.getPassword());
                newUser.setRole(user.getRole());
                newUser = userRepository.save(newUser);
                return newUser;
            }
        }
        return userRepository.save(user);
    }

    @Override
    public User deleteUser(User user) {
        Long id = user.getId();
        if (id != null) {
            userRepository.deleteById(id);
            return user;
        }
        return null;
    }

    @Override
    public boolean removeUserFromMarathon(User user, Marathon marathon) {
        User userEntity = userRepository.getOne(user.getId());
        Marathon marathonEntity = marathonRepository.getOne(marathon.getId());
        marathonEntity.getUsers().remove(userEntity);
        return marathonRepository.save(marathonEntity) != null;
    }

    @Override
    public List<User> getAllByRole(String role) {
        return userRepository.getAllByRole(User.Role.valueOf(role.toUpperCase()));
    }

    @Override
    public boolean addUserToMarathon(User user, Marathon marathon) {
        User userEntity = userRepository.getOne(user.getId());
        Marathon marathonEntity = marathonRepository.getOne(marathon.getId());
        marathonEntity.getUsers().add(userEntity);
        return marathonRepository.save(marathonEntity) != null;
    }
}
