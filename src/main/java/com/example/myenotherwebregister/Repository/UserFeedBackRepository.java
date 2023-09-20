package com.example.myenotherwebregister.Repository;

import com.example.myenotherwebregister.model.UserFeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFeedBackRepository extends JpaRepository<UserFeedBack,Long> {
}
