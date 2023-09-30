package com.example.myenotherwebregister.controllers;

import com.example.myenotherwebregister.Repository.UserPostsRepository;
import com.example.myenotherwebregister.Repository.UserRepository;
import com.example.myenotherwebregister.Services.UserPostService;
import com.example.myenotherwebregister.model.UserPost;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public  class UserPostsController {
    @Autowired
    private UserPostService userPostService;
    @Autowired
    UserPostsRepository userPostsRepository;
    @Autowired
    UserRepository userRepository;
    @GetMapping("ChatRoom")
    String showChatRoom (Model model, HttpSession session){
     String username = (String) session.getAttribute("username");


        model.addAttribute("username",username);
        return "ChatRoom";
    }
    @PostMapping("/sendMessage")
    public String sendMessage(
            @RequestParam("message") String message,
            @RequestParam("sender") String sender,
            @RequestParam(value = "image", required = false) MultipartFile image,
            Model model,
            HttpSession session
    ) {

        UserPost userPost = new UserPost();
        userPost.setUserMessage(message);
        userPost.setSender(sender);

        // Обработка изображения только если оно было загружено
        if (image != null && !image.isEmpty()) {
            try {
                String fileName = StringUtils.cleanPath(image.getOriginalFilename());

                // Определите путь к папке, где будут храниться изображения (в данном случае, корень вашего приложения)
                String uploadDir = "ChatImages";

                // Создаем путь для сохранения изображения
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Путь к файлу, включая имя файла
                Path filePath = uploadPath.resolve(fileName);

                // Сохраняем изображение
                Files.write(filePath, image.getBytes());

                // Сохраняем относительный путь к файлу в UserPost
                userPost.setImagePath(filePath.toString());
            } catch (IOException e) {
                e.printStackTrace();
                // Обработка ошибки сохранения изображения
            }
        }

        userPostService.saveUserPost(userPost);
        return showChatRoom(model, session);
    }
    @GetMapping("/getAllMessages")
    @ResponseBody
    public List<UserPost> getAllMessages() {
        return userPostService.getAlluserMessage();
    }
    @GetMapping("/getMessages")
    @ResponseBody
    public List<UserPost> getMessages() {
        return userPostService.getAllUserPosts();
    }
    @DeleteMapping("/messages/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable("id") Long id) {
        userPostsRepository.deleteById(id);
        return ResponseEntity.ok().body("Message deleted successfully");
    }

}
