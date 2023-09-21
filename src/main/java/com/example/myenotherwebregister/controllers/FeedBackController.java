package com.example.myenotherwebregister.controllers;

import com.example.myenotherwebregister.Repository.UserFeedBackRepository;
import com.example.myenotherwebregister.Services.UserFeedBackService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FeedBackController {

    @Autowired
    public UserFeedBackService userFeedBackService;
    @PostMapping("/feedBackSent")
    public String feedBackSent(@RequestParam("message") String message, @RequestParam("title") String title, HttpSession session) {
        String username = (String) session.getAttribute("username");
        userFeedBackService.saveUserFeedBack(username,message,title);

        return "kabinet";
    }
    @GetMapping("/UserFeedBackList")
    String showUserFeedBack(Model model, HttpServletRequest request) {return userFeedBackService.showUserFeedBackList(model,request);}
}
