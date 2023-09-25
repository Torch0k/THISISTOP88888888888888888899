package com.example.myenotherwebregister.controllers;

import com.example.myenotherwebregister.Services.HomeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private HomeService homeService;
    @PostMapping("/login") // Обработка POST-запроса для обработки входа пользователя.
    public String processLogin(@RequestParam String username, @RequestParam String password, Model model, HttpServletRequest request) {
        return homeService.processLogin(username,password,model,request);}
    @PostMapping("/saveUser") // Обработка POST-запроса для сохранения пользователя.
    public String saveUser(@RequestParam String username,@RequestParam String password,HttpServletRequest request,Model model) {
        return homeService.SaveUser(username, password, request, model);}

}
