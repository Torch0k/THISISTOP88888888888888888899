package com.example.myenotherwebregister.Repository;

import com.example.myenotherwebregister.model.BunkerPost;
import com.example.myenotherwebregister.model.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BunkerPostRepository extends JpaRepository<BunkerPost,Long> {
    @Query("SELECT up.sender, up.userMessage FROM UserPost up")
    List<Object[]> findAllSenderAndContent();
    List<BunkerPost> findBySender(String username);
    List<BunkerPost> findAll();
}
