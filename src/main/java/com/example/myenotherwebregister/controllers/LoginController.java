package com.example.myenotherwebregister.controllers;

import com.example.myenotherwebregister.Repository.UserRepository;
import com.example.myenotherwebregister.model.User;
import com.example.myenotherwebregister.usr.Polzovatel;
import com.example.myenotherwebregister.Repository.PolzovatelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model) {
        return "login"; // Это имя представления (шаблона Thymeleaf)
    }
    @GetMapping("/register")
    public String register() {
        return "registration";
    }
    @PostMapping("/register")
    public String processRegistration(@RequestParam String username, @RequestParam String password) {
        // Создание нового пользователя и добавление его в репозиторий
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        userRepository.save(user);
        return "redirect:/login"; // Перенаправление на страницу входа после регистрации
    }
    @Autowired
    public UserRepository userRepository;
    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password,Model model) {
       User user = userRepository.findByUsername(username);


        if (user != null && user.getPassword().equals(password)) {
            // Успешная аутентификация
            return "redirect:/trueLogin"; // Перенаправление на страницу после успешного входа
        } else {
            // Неудачная аутентификация
            model.addAttribute("error", "Неверное имя пользователя или пароль");
            return "login";

        }
    }
}