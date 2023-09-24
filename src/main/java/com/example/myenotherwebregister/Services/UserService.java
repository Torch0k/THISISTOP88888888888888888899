package com.example.myenotherwebregister.Services;

import com.example.myenotherwebregister.Repository.UserRepository;
import com.example.myenotherwebregister.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User findById ( Long id){
        User user = null;
       List<User> allUsers =  userRepository.findAll();
        for (User u:allUsers
             ) {
            if(u.getId() == id){
                user = u;
            }
        }
        return user;

    }

}
