package com.example.myenotherwebregister.yandexmap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {
    @GetMapping("/map")
    public String showMapPage() {
        return "map"; // Это вернет имя вашего HTML-шаблона без расширения
    }
}