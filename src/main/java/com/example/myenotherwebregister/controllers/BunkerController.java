package com.example.myenotherwebregister.controllers;

import com.example.myenotherwebregister.Repository.UserRepository;
import com.example.myenotherwebregister.Services.LoginService;
import com.example.myenotherwebregister.model.Location;
import com.example.myenotherwebregister.model.User;
import com.example.myenotherwebregister.Services.BunkerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bunker")
public class BunkerController {
    @Autowired
    private  BunkerService bunkerService;
    @Autowired
    private LoginService loginService;
    @PostMapping("/compare_coordinates")   // Метод для сравнения координат и возвращения результата.
    public ResponseEntity<String> compareCoordinates(@RequestBody Location data, HttpServletRequest request) {
        User user = loginService.getCurrentUser(request); // Получаем текущего пользователя на основе сессии с помощью loginService.getCurrentUser
        if (user != null) { // Если пользователь аутентифицирован не нулл
            String result = bunkerService.compareCoordinates(data, user);  // Вызываем bunkerService.compareCoordinates для сравнения координат и получения результата
            return ResponseEntity.ok(result);  // Возвращаем результат как ответ с кодом 200 OK
        } else {
            return ResponseEntity.ok("Пользователь не найден");  // Если пользователь не аутентифицирован, возвращаем сообщение "Пользователь не найден" с кодом 200 OK
        }
    }
}