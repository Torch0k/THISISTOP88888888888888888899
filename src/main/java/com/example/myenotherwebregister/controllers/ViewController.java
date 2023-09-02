package com.example.myenotherwebregister.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/daiManky")
    public String showDaiMankyPage() {
        return "daiManky"; // Возвращает имя файла без расширения (без .html)
    }
}