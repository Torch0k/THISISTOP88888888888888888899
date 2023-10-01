package com.example.myenotherwebregister.Services;

import com.example.myenotherwebregister.Repository.UserRepository;
import com.example.myenotherwebregister.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class HomeService {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public MailSender mailSender;
    public String SaveUser(
            @RequestParam String username,
            @RequestParam String password,String email,
            HttpServletRequest request, Model model) {
        // Создание нового пользователя и добавление его в репозиторий
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setEmail(email);
        user.setRole("user");

        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        session.setAttribute("isLoggedIn", true);
        session.setAttribute("userRole",user.getRole());// под вопросом  //todo //туду как мыло дуру
        model.addAttribute("userAddress", user.getNearestAddress());
        user.setActivationCode(UUID.randomUUID().toString());
        session.setAttribute("userActivation",user.getActivationCode());
        session.setAttribute("userAddress",user.getNearestAddress());
        userRepository.save(user);
        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format("привет %s! \n" + "перейди по ссылке и для завершения регистрации: http://95.84.138.219:8085/activate/%s",
            user.getUsername(),
                    user.getActivationCode() );

            mailSender.sendEmail(user.getEmail(),message,"activationcode");
        }
        // Перенаправляем пользователя на страницу /узнавания адреса
        return "nearestaddress"; // TODO: 01.10.2023 сделать страничку которая просит подтвердить мейл
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
            session.setAttribute("userAddress",user.getNearestAddress());
            session.setAttribute("userActivation",user.getActivationCode());

            if (user.getNearestAddress() != null) {        // Проверяем, есть ли у пользователя ближайший адрес
                return showkabinetPage(model,request); // Если есть перенаправляем пользователя на страницу личного кабинета
            } else { // если -
                return "nearestaddress"; // - адрес отсутствует, перенаправляем пользователя на страницу выбора ближайшего адреса
            }
        }

        model.addAttribute("error", "Неверное имя пользователя или пароль"); // Если пользователь не найден или пароль не совпадает, добавляем атрибут "error" в модель
        model.addAttribute("isLoggedIn", false);                             // с сообщением об ошибке и возвращаем страницу входа с сообщением об ошибке //
        return "login";                                                                             //

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

    public boolean activateUser(String code) {
       User user = userRepository.findByActivationCode(code);
       if(user == null){
           return false;
       }
       user.setActivationCode(null);
       userRepository.save(user);

        return true;
    }
}
