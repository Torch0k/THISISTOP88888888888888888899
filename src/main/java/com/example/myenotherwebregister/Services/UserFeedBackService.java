package com.example.myenotherwebregister.Services;

import com.example.myenotherwebregister.Repository.UserFeedBackRepository;
import com.example.myenotherwebregister.Repository.UserRepository;
import com.example.myenotherwebregister.constants.FeedBacksStatusConstants;
import com.example.myenotherwebregister.model.UserFeedBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFeedBackService {
    @Autowired
    public  UserFeedBackRepository userFeedBackRepository;

    public  void saveUserFeedBack(String username, String message, String title){
        UserFeedBack userFeedBack = new UserFeedBack();
        userFeedBack.setUsername(username);
        userFeedBack.setMessage(message);
        userFeedBack.setTitle(title);
        userFeedBack.setStatus(FeedBacksStatusConstants.STATUS_NEW);
        userFeedBackRepository.save(userFeedBack);
    }
}
