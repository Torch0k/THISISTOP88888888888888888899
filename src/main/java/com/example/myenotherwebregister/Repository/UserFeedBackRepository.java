package com.example.myenotherwebregister.Repository;

import com.example.myenotherwebregister.model.User;
import com.example.myenotherwebregister.model.UserFeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFeedBackRepository extends JpaRepository<UserFeedBack,Long> {
    List<UserFeedBack> findByUsername(String username);

    List<UserFeedBack> findByMessage(String message);
}
