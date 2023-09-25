package com.example.myenotherwebregister.Repository;

import com.example.myenotherwebregister.model.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPostsRepository extends JpaRepository<UserPost,Long> {
    @Query("SELECT up.sender, up.userMessage FROM UserPost up")
    List<Object[]> findAllSenderAndContent();
    List<UserPost> findBySender(String username);
    List<UserPost> findAll();

}
