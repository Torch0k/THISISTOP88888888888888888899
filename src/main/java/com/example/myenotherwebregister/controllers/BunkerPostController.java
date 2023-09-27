package com.example.myenotherwebregister.controllers;

import com.example.myenotherwebregister.Repository.UserRepository;
import com.example.myenotherwebregister.Services.BunkerPostService;
import com.example.myenotherwebregister.Services.UserService;
import com.example.myenotherwebregister.model.BunkerPost;
import com.example.myenotherwebregister.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BunkerPostController {
    @Autowired
     BunkerPostService bunkerPostService;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @GetMapping("BunkerChat")
    String showBunkerChat (Model model, HttpSession session){
        User user = userRepository.findByUsername(session.getAttribute("username").toString());

        String username = (String) session.getAttribute("username");
        model.addAttribute("username",username);
        model.addAttribute("userAddress", user.getNearestAddress());
        return "BunkerChat";

    }
    @PostMapping("/sendBunkerMessage")
    public String sendMessage(String message,String sender,Model model,HttpSession session,String userAddress){
        BunkerPost bunkerPost = new BunkerPost();
        bunkerPost.setSender(sender);
        bunkerPost.setUserMessage(message);
        bunkerPost.setNearestAddress(userAddress);
        bunkerPostService.saveBunkerPost(bunkerPost);
        return showBunkerChat(model,session);
    }
    @GetMapping("/getAllBunkerMessages")
    @ResponseBody
    public List<BunkerPost> getAllMessages(){
        return bunkerPostService.getAllBunkerMessage();
    }
}
