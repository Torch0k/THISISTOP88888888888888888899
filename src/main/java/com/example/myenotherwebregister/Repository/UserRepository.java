package com.example.myenotherwebregister.Repository;

import com.example.myenotherwebregister.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findByNearestAddress(String nearestAddress);

    User findByActivationCode(String code);
}
