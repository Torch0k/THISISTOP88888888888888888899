package com.example.myenotherwebregister.controllers;

import com.example.myenotherwebregister.Services.HomeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String saveUser(@RequestParam String username,@RequestParam String password,@RequestParam String email,HttpServletRequest request,Model model) {
        return homeService.SaveUser(username, password,email, request, model);}
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivated = homeService.activateUser(code);
        if(isActivated){
            model.addAttribute("message","активировация прошла успешно можете войти со своим логином и паролем");
        }else {model.addAttribute("message","возникла ошибка при активации почты ");
        }
        return "login";
    }

}
