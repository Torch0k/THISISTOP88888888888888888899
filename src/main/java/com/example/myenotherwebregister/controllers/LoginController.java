package com.example.myenotherwebregister.controllers;

import com.example.myenotherwebregister.Repository.LocationRepository;
import com.example.myenotherwebregister.Repository.UserRepository;
import com.example.myenotherwebregister.model.Location;
import com.example.myenotherwebregister.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class LoginController {
    private static final double EARTH_RADIUS = 6371;

    @Autowired
    private LocationRepository locationRepository;
    @GetMapping("/") // главная страница
    public String home(Model model) {model.addAttribute("message", "Привет, это мой сайт на Spring с Jakarta EE!");return "index";}
    @GetMapping("/trueLogin")  // вход
    public String successfulLogin() {return "trueLogin";}
    @GetMapping("/nearestaddress")  // вход
    public String nearestaddress() {return "nearestaddress";}
    @GetMapping("/register")   // регистрация
    public String register() {return "signUp";}
    @PostMapping("/register")
    public String registerAndGetCoordinates(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletRequest request,Model model) {
        // Создание нового пользователя и добавление его в репозиторий
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);

        userRepository.save(user);
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        model.addAttribute("userAddress", user.getNearestAddress());


        // Перенаправляем пользователя на страницу /узнавания адреса

        return "nearestaddress";
    }
    @GetMapping("/vnutriBunkera")
    public String goToVnutriBunkera() {
        return "vnutriBunkera"; // Возвращаем имя HTML-страницы
    }
    @Autowired
    public UserRepository userRepository;
    @GetMapping("/kabinet")
    public String showkabinetPage(Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();//получаем обьект сессии из hhtp запроса


        User user = userRepository.findByUsername(session.getAttribute("username").toString());//получаем пользователя из репозитория по юзернейму из сессии
        List<User> matchingUsers = userRepository.findByNearestAddress(user.getNearestAddress());//поулчаем список пользователей с таким же бункером каки убратившегося пользователя

        model.addAttribute("FriendList", matchingUsers);//добовляем в модель отфильтрованный список (модель используетсья шаблонищатором thymeleaf для отображения на
        return "kabinet"; //личный кабинет

    }
    @GetMapping("/index")
    public String mainPage(){return "index";}//главная страница
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
            model.addAttribute("userAddress", user.getNearestAddress());
            if (user.getNearestAddress() != null) {
               return showkabinetPage(model,request);
            } else {
                return "nearestaddress";
            }
        }
        model.addAttribute("error", "Неверное имя пользователя или пароль");
        return "login";
    }


}

