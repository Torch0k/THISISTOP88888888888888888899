package com.example.myenotherwebregister.controllers;

import com.example.myenotherwebregister.Repository.UserPostsRepository;
import com.example.myenotherwebregister.Services.UserPostService;
import com.example.myenotherwebregister.model.UserPost;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public  class UserPostsController {
    @Autowired
    private UserPostService userPostService;
    @Autowired
    UserPostsRepository userPostsRepository;
    @GetMapping("ChatRoom")
    String showChatRoom (Model model, HttpSession session){
     String username = (String) session.getAttribute("username");

        model.addAttribute("username",username);
        return "ChatRoom";
    }
    @PostMapping("/sendMessage")
    public String sendMessage(String message,String sender,Model model,HttpSession session){
        UserPost userPost = new UserPost();

        userPost.setUserMessage(message);
        userPost.setSender(sender);
        userPostService.saveUserPost(userPost);
        return showChatRoom(model,session);
    }
    @GetMapping("/getAllMessages")
    @ResponseBody
    public List<UserPost> getAllMessages() {
        return userPostService.getAlluserMessage();
    }
    @GetMapping("/getMessages")
    @ResponseBody
    public List<UserPost> getMessages() {
        return userPostService.getAllUserPosts();
    }

}
