package com.example.myenotherwebregister.Services;

import com.example.myenotherwebregister.Repository.UserRepository;
import com.example.myenotherwebregister.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class LoginService {
    @Autowired
    public UserRepository userRepository;
    public String SaveUser(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletRequest request, Model model) {
        // Создание нового пользователя и добавление его в репозиторий
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setRole("user");
        userRepository.save(user);
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        session.setAttribute("isLoggedIn", true);
        session.setAttribute("userRole",user.getRole());// под вопросом пушто юзер то новый а я даунич основ програмирования не знаю и сномневаюсь //todo //туду как мыло дуру
        model.addAttribute("userAddress", user.getNearestAddress());
        // Перенаправляем пользователя на страницу /узнавания адреса
        return "nearestaddress";
    }
    public String showkabinetPage(Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();//получаем обьект сессии из hhtp запроса
        if (session.getAttribute("username") != null) {
            User user = userRepository.findByUsername(session.getAttribute("username").toString());//получаем пользователя из репозитория по юзер нейму из сессии
            List<User> matchingUsers = userRepository.findByNearestAddress(user.getNearestAddress());//поучаем список пользователей с таким же бункером каки обратившегося пользователя
            model.addAttribute("FriendList", matchingUsers);//добавляем в модель отфильтрованный список (модель используется шаблонизатором thymeleaf для отображения на
            model.addAttribute("userAddress", user.getNearestAddress());
            return "kabinet"; //личный кабинет
        } else {
            model.addAttribute("message","Авторизуйтесь пж");
            return "index";
        }
    }

    public String processLogin(@RequestParam String username, @RequestParam String password, Model model, HttpServletRequest request) { // Метод для обработки запроса на аутентификацию пользователя

        User user = userRepository.findByUsername(username); // Ищем пользователя по его имени в репозитории
        if (user != null && user.getPassword().equals(password)) {        // Проверяем существует ли пользователь и совпадает ли его пароль
            HttpSession session = request.getSession();// Получаем или создаем сессию для пользователя
            session.setAttribute("username", username); // Устанавливаем атрибут "username" в сессии для обозначения аутентифицированного пользователя
            model.addAttribute("userAddress", user.getNearestAddress());// Добавляем атрибут "userAddress" в модель содержащий информацию о ближайшем адресе пользователя
            session.setAttribute("isLoggedIn", true);
            session.setAttribute("role",user.getRole());
            model.addAttribute("role",user.getRole());
            if (user.getNearestAddress() != null) {        // Проверяем, есть ли у пользователя ближайший адрес
                return showkabinetPage(model,request); // Если есть перенаправляем пользователя на страницу личного кабинета
            } else { // если -
                return "nearestaddress"; // - адрес отсутствует, перенаправляем пользователя на страницу выбора ближайшего адреса
            }
        }

        model.addAttribute("error", "Неверное имя пользователя или пароль"); // Если пользователь не найден или пароль не совпадает, добавляем атрибут "error" в модель
        model.addAttribute("isLoggedIn", false);                             // с сообщением об ошибке и возвращаем страницу входа с сообщением об ошибке //
        return "login";                                                                             // TODO: 11.09.2023 помоему я не использую вывод сообщения и модель error надобы удалить .

    }
    public User getCurrentUser(HttpServletRequest request) {   // Метод для получения текущего пользователя на основе сессии.
        HttpSession session = request.getSession(false);// Получаем текущую сессию но не создаем новую если она отсутствует
        if (session != null) {// Проверяем, существует ли сессия
            String username = (String) session.getAttribute("username");// Извлекаем имя пользователя из атрибута "username" сессии
            if (username != null) {// Если имя пользователя не пусто
                return userRepository.findByUsername(username);// Ищем пользователя в репозитории по имени пользователя
            }
        }
        return null;// Если сессия не существует или имя пользователя не найдено, возвращаем null
    }

}
