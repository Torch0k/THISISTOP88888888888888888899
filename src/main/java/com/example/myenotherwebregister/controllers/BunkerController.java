package com.example.myenotherwebregister.controllers;

import com.example.myenotherwebregister.Repository.LocationRepository;
import com.example.myenotherwebregister.Repository.UserRepository;
import com.example.myenotherwebregister.model.Location;
import com.example.myenotherwebregister.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BunkerController {
    private static final double EARTH_RADIUS = 6371;


    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getusernearestadres")
    public String getUserAddress(Model model, HttpServletRequest request) {
        // Получаем информацию о текущем авторизованном пользователе с использованием вашего метода
        User user = getCurrentUser(request);

        if (user != null) {
            model.addAttribute("userAddress", user.getNearestAddress());
        }

        return "user"; // Имя представления (user.html)
    }


    @PostMapping("/compare_coordinates")
    public ResponseEntity<String> compareCoordinates(@RequestBody Location data, HttpServletRequest request) {
        float userLatitude = data.getLatitude();
        float userLongitude = data.getLongitude();
        User currentUser = getCurrentUser(request);
        List<Location> matchingLocations = locationRepository.findAllByLatitudeAndLongitude(userLatitude, userLongitude);

        if (!matchingLocations.isEmpty()) {
            Location selectedLocation = matchingLocations.stream()
                    .min(Comparator.comparingLong(Location::getId))
                    .orElse(null);
            if (selectedLocation != null) {
                String address = selectedLocation.getAddress();

                return ResponseEntity.ok("в вашем доме найден бункер! Адрес дома и бункера: " + address);
            } else {
                return ResponseEntity.ok("в вашем доме найден бункер");
            }
        } else {
            Location nearestLocation = findNearestLocation(userLatitude, userLongitude);
            if (nearestLocation != null) {
                String nearestAddress = nearestLocation.getAddress();

                // Получение текущего пользователя
                User user = getCurrentUser(request);

                if (user != null) {
                    // Сохраните информацию о ближайшей локации пользователя в сущность User
                    user.setLatitude(userLatitude);
                    user.setLongitude(userLongitude);
                    user.setNearestAddress(nearestAddress);
                    userRepository.save(user);
                }

                return ResponseEntity.ok("в вашем доме бункеров не найдено. Ближайший бункер находится по адресу: " + nearestAddress);
            } else {
                return ResponseEntity.ok("в вашем доме бункеров не найдено");
            }
        }
    }

    // Метод для поиска ближайших координат в locationRepository
    private Location findNearestLocation(float targetLatitude, float targetLongitude) {
        List<Location> allLocations = locationRepository.findAll();
        Location nearestLocation = null;
        double minDistance = Double.MAX_VALUE;
        for (Location location : allLocations) {
            double lat1 = Math.toRadians(targetLatitude);
            double lon1 = Math.toRadians(targetLongitude);
            double lat2 = Math.toRadians(location.getLatitude());
            double lon2 = Math.toRadians(location.getLongitude());
            double dLat = lat2 - lat1;
            double dLon = lon2 - lon1;
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(lat1) * Math.cos(lat2) *
                            Math.sin(dLon / 2) * Math.sin(dLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = EARTH_RADIUS * c;
            if (distance < minDistance) {
                minDistance = distance;
                nearestLocation = location;
            }
        }
        return nearestLocation;
    }

    // Метод для получения текущего пользователя
    private User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Получение текущей сессии без создания новой
        if (session != null) {
            String username = (String) session.getAttribute("username"); // Предполагается, что имя пользователя сохранено в сессии
            if (username != null) {
                return userRepository.findByUsername(username);
            }
        }
        return null; // Если пользователь не аутентифицирован или сессия отсутствует
    }
}