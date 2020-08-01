package com.softserve.sprint15.service;

import com.softserve.sprint15.entity.Marathon;
import com.softserve.sprint15.entity.User;

import java.util.List;

public interface UserService {

    public List<User> getAll();

    public User getUserById(Long id);

    public User createOrUpdateUser(User user);

    public boolean removeUserFromMarathon(User user, Marathon marathon);

    public List<User> getAllByRole(String role);

    public boolean addUserToMarathon(User user, Marathon marathon);

    public User deleteUser(User user);
}
