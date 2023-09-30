package com.example.myenotherwebregister.controllers;

import com.example.myenotherwebregister.Repository.UserRepository;
import com.example.myenotherwebregister.Services.UserService;
import com.example.myenotherwebregister.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    public UserService userService;
    @Autowired
    UserRepository userRepository;
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
    @GetMapping("/getUserRole")
    public ResponseEntity<String> getUserRole(HttpSession session) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        String role = user.getRole();
        if(!role.equals("admin"))
        {
            return ResponseEntity.status(403).body("не админ");
        }
        System.out.println("получили роль админа");
        return ResponseEntity.ok("admin");
    }
}