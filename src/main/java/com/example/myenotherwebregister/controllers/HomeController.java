package com.example.myenotherwebregister.controllers;

import com.example.myenotherwebregister.Repository.LocationRepository;
import com.example.myenotherwebregister.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Привет, это мой сайт на Spring с Jakarta EE!");
        return "index"; // Это имя представления (шаблона Thymeleaf)
    }
    /*
    @Autowired
     LocationRepository localrep;


@GetMapping("/name")
    public  String play(){

        String filePath ="C:\\Users\\darkm\\OneDrive\\Рабочий стол\\simplename.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {

                //line=55.465172, 37.297682 ; Адрес: Москва, Троицк, Физическая улица, 5
                Location location = new Location();
                location.setAddress(line.split(";")[1].trim());
                location.setLatitude(Float.parseFloat(line.split(";")[0].split(",")[0].trim()));
                location.setLongitude(Float.parseFloat(line.split(";")[0].split(",")[1].trim()));
                System.out.println("сохранадю  "+""+location.toString());
                localrep.save(location);
                System.out.println("сохранено");
ц
            }
            System.out.println("записал успешно");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/index";
    }

     */
}