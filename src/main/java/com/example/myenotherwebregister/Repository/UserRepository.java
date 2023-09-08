package com.example.myenotherwebregister.Repository;

import com.example.myenotherwebregister.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByNearestAddress(String nearestAddress);
}
