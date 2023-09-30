package com.example.myenotherwebregister.controllers;

import com.example.myenotherwebregister.Services.HomeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
   private HomeService homeService;


    @GetMapping("/") // главная страница
    public String home(Model model) {return "index";}
    @GetMapping("/register")   // // Обработка запроса на страницу регистрации
    public String register() {return "signUp";}
    @GetMapping("/kabinet")// Обработка запроса на страницу кабинета
    String showkabinetPage(Model model,HttpServletRequest request) {return homeService.showkabinetPage(model,request);}
    @GetMapping("/index")// Обработка запроса на главную страницу
    public String mainPage(){return "index";}//главная страница
    @GetMapping("/map")// Обработка запроса на страницу карты
    public String showMapPage() {return "map";} //карта с адресами
    @GetMapping("/personalPage") //
    public String personalPage() {return "personalPage";} // персональная страница
    @GetMapping("/login")// Обработка запроса на страницу входа
    public String login(Model model) {return "login";}
    @GetMapping("feedBack")
    public String feedBack(){return "feedBack";}
    @GetMapping("feeBackList")
    public String feeBackList(){return "UserFeedBackList";}
 @GetMapping("nearestaddress")
 public String shownearestaddress(){return "nearestaddress";}

}

