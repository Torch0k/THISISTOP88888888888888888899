package com.example.myenotherwebregister.controllers;

import com.example.myenotherwebregister.Repository.UserFeedBackRepository;
import com.example.myenotherwebregister.Services.UserFeedBackService;
import com.example.myenotherwebregister.model.UserFeedBack;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/adminFeedBack")
    String showAdminFeedBack(Model model){return userFeedBackService.showFeedBackAll(model);}
    @GetMapping("/edit-feedback/{id}")
    public String editFeedback(@PathVariable("id") Long id, Model model) {
        UserFeedBack feedback = userFeedBackService.getFeedbackById(id);
        model.addAttribute("feedback", feedback);
        model.addAttribute("title", feedback.getTitle());
        model.addAttribute("message", feedback.getMessage());
        model.addAttribute("username", feedback.getUsername());

        return "feedBackRedactor"; // Название страницы редактирования
    }
    @PostMapping("/save-feedback")
    public String saveFeedback(@ModelAttribute("feedback") UserFeedBack feedback) {
        userFeedBackService.saveFeedback(feedback);

        return "adminFeedBack";
    }

}
