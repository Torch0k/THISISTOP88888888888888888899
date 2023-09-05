package com.example.myenotherwebregister.controllers;

import com.example.myenotherwebregister.Repository.UserRepository;
import com.example.myenotherwebregister.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @GetMapping("/") // главная страница
    public String home(Model model) {model.addAttribute("message", "Привет, это мой сайт на Spring с Jakarta EE!");return "index";}
    @GetMapping("/trueLogin")  // вход
    public String successfulLogin() {return "trueLogin";}
    @GetMapping("/register")   // регистрация
    public String register() {return "trueLogin";}
    @PostMapping("/register")
    public String processRegistration(@RequestParam String username, @RequestParam String password,@RequestParam Float latitude,@RequestParam Float longitude,
                                      @RequestParam String nearestAddress) {
        // Создание нового пользователя и добавление его в репозиторий
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setLatitude(latitude); // Сохраните широту
        user.setLongitude(longitude); // Сохраните долготу
        user.setNearestAddress(nearestAddress); // Сохраните ближайший адрес
        userRepository.save(user);


        return "redirect:/vnutriBunkera"; // Перенаправление на страницу выбоар бунекера после регистрации
    }
    @GetMapping("/vnutriBunkera")
    public String goToVnutriBunkera() {
        return "vnutriBunkera"; // Возвращаем имя HTML-страницы
    }
    @Autowired
    public UserRepository userRepository;

    @GetMapping("/map")
    public String showMapPage() {
        return "map"; //карта с адресами
    }
    @GetMapping("/login")  // логин
    public String login(Model model) {return "login";}
    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, Model model, HttpServletRequest request) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // Успешная аутентификация
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            if (user.getNearestAddress() != null && !user.getNearestAddress().isEmpty()) {
                model.addAttribute("nearestAddress", user.getNearestAddress());//передачa NearestAddress на страницу
                return "vnutriBunkera"; // Перенаправление на страницу VnutriBunkera
            } else {
                return "trueLogin"; // Перенаправление на страницу trueLogin при незаполненном nearestAddress
            }
        } else {
            model.addAttribute("error", "Неверное имя пользователя или пароль");
            return "login";
        }
    }

}

