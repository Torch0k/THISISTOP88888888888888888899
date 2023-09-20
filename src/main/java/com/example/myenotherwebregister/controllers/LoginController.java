package com.example.myenotherwebregister.controllers;

import com.example.myenotherwebregister.Services.LoginService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
   private LoginService loginService;
    @GetMapping("/") // главная страница
    public String home(Model model) {return "index";}
    @GetMapping("/register")   // // Обработка запроса на страницу регистрации
    public String register() {return loginService.register();}
    @GetMapping("/kabinet")// Обработка запроса на страницу кабинета
    String showkabinetPage(Model model,HttpServletRequest request) {return loginService.showkabinetPage(model,request);}
    @GetMapping("/index")// Обработка запроса на главную страницу
    public String mainPage(){return loginService.mainPage();}//главная страница
    @GetMapping("/map")// Обработка запроса на страницу карты
    public String showMapPage() {return loginService.showMapPage();} //карта с адресами}
    @GetMapping("/personalPage") //
    public String personalPage() {return loginService.personalPage();} // персональная страница
    @GetMapping("/login")// Обработка запроса на страницу входа
    public String login(Model model) {return "login";}
    @GetMapping("feedBack")
    public String feedBack(){return loginService.feedBack();}
    @PostMapping("/login") // Обработка POST-запроса для обработки входа пользователя.
    public String processLogin(@RequestParam String username, @RequestParam String password, Model model, HttpServletRequest request) {
        return loginService.processLogin(username,password,model,request);}
    @PostMapping("/saveUser") // Обработка POST-запроса для сохранения пользователя.
    public String saveUser(@RequestParam String username,@RequestParam String password,HttpServletRequest request,Model model) {
        return loginService.SaveUser(username, password, request, model);}

}

