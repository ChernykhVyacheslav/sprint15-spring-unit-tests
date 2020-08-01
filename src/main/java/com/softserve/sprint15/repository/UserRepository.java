package com.softserve.sprint15.repository;

import com.softserve.sprint15.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> getAllByRole(User.Role role);
    User findUserByEmail(String email);
}
