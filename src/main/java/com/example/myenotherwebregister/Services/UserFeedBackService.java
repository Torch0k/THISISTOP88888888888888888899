package com.example.myenotherwebregister.Services;

import com.example.myenotherwebregister.Repository.UserFeedBackRepository;
import com.example.myenotherwebregister.Repository.UserRepository;
import com.example.myenotherwebregister.constants.FeedBacksStatusConstants;
import com.example.myenotherwebregister.model.User;
import com.example.myenotherwebregister.model.UserFeedBack;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class UserFeedBackService {
    @Autowired
    private LoginService loginService;
    @Autowired
    public  UserFeedBackRepository userFeedBackRepository;
    @Autowired
    public  UserRepository userRepository;
    public  void saveUserFeedBack(String username, String message, String title){
        UserFeedBack userFeedBack = new UserFeedBack();
        userFeedBack.setUsername(username);
        userFeedBack.setMessage(message);
        userFeedBack.setTitle(title);
        userFeedBack.setStatus(FeedBacksStatusConstants.STATUS_NEW);
        userFeedBackRepository.save(userFeedBack);
    }
    public String showUserFeedBackList (Model model, HttpServletRequest request){
        User user = loginService.getCurrentUser(request);
        String username = user.getUsername();

        List<UserFeedBack> feedbackList = userFeedBackRepository.findByUsername(username);



        model.addAttribute("feedbackList",feedbackList);
        return "UserFeedBackList";
    }
    public String showFeedBackAll(Model model) {
        List<UserFeedBack> userFeedBacksAll = userFeedBackRepository.findAll();
        model.addAttribute("feedBackListAll", userFeedBacksAll);
    return "adminFeedBack";
    }
    public UserFeedBack getFeedbackById (Long id) {
        return userFeedBackRepository.findById(id).orElse(null);
    }

    public void saveFeedback(UserFeedBack feedback) {
        // Логика сохранения обратной связи
        userFeedBackRepository.save(feedback);
    }

}
