package com.example.myenotherwebregister.Services;

import com.example.myenotherwebregister.Repository.UserPostsRepository;
import com.example.myenotherwebregister.model.UserPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPostService {
    @Autowired
    private UserPostsRepository userPostsRepository;
    public void saveUserPost(UserPost userPost) {
        userPostsRepository.save(userPost);
    }

    public List<UserPost> getAlluserMessage() {
        return userPostsRepository.findAll();
    }
    public List<UserPost> getAllUserPosts() {
        return userPostsRepository.findAll();
    }
    public List<Object[]> getAllSenderAndContent() {
        return userPostsRepository.findAllSenderAndContent();
    }

}
