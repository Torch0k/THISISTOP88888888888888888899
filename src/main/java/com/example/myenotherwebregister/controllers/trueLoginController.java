package com.example.myenotherwebregister.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class trueLoginController {
    @GetMapping("/trueLogin")
    public String successfulLogin() {
        return "trueLogin"; // Замените на имя вашего представления
    }
}
