package com.example.myenotherwebregister.controllers;

import com.example.myenotherwebregister.Services.UserService;
import com.example.myenotherwebregister.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    public UserService userService;

    @GetMapping("/{id}")
    public String getUserPage(@PathVariable Long id, Model model) {
        // В этой функции вы можете выполнить логику для получения данных пользователя по id
        // и передать эти данные в модель
        User user = userService.findById(id); //   получения данных пользователя из репозитория
        if (user != null) {
            model.addAttribute("username",user.getUsername() );
            model.addAttribute("address",user.getNearestAddress());
            return "personalPage"; // Вернуть имя шаблона HTML-страницы
        } else {
            return "userNotFound"; // Вернуть имя шаблона для страницы с сообщением "Пользователь не найден"
        }
    }
}