package com.softserve.sprint14.service;

import com.softserve.sprint14.entity.Marathon;
import com.softserve.sprint14.entity.Progress;
import com.softserve.sprint14.entity.User;
import com.softserve.sprint14.exception.EntityNotFoundByIdException;
import com.softserve.sprint14.repository.MarathonRepository;
import com.softserve.sprint14.repository.ProgressRepository;
import com.softserve.sprint14.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProgressRepository progressRepository;

    @Autowired
    MarathonRepository marathonRepository;

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users.isEmpty() ? new ArrayList<>() : users;
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            return user.get();
        else throw new EntityNotFoundByIdException("No user for given id");
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
        user = userRepository.save(user);
        return user;
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
    public List<User> getAllByRole(String role) {
        List<User> users = userRepository.findAll()
                .stream()
                .filter(user -> user.getRole().toString().equals(role))
                .collect(Collectors.toList());
        return users.isEmpty() ? new ArrayList<>() : users;
    }

    @Override
    public boolean addUserToMarathon(User user, Marathon marathon) {
        User userEntity = userRepository.getOne(user.getId());
        Marathon marathonEntity = marathonRepository.getOne(marathon.getId());
        marathonEntity.getUsers().add(userEntity);
        return marathonRepository.save(marathonEntity) != null;
    }

    @Override
    public boolean addUserToProgress(User user, Progress progress) {
        User userEntity = userRepository.getOne(user.getId());
        Progress progressEntity = progressRepository.getOne(progress.getId());
        progressEntity.setTrainee(userEntity);
        return progressRepository.save(progressEntity) != null;
    }
}
