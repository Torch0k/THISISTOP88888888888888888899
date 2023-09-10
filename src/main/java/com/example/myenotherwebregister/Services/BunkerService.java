package com.example.myenotherwebregister.Services;

import com.example.myenotherwebregister.Repository.LocationRepository;
import com.example.myenotherwebregister.Repository.UserRepository;
import com.example.myenotherwebregister.model.Location;
import com.example.myenotherwebregister.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class BunkerService {
    private static final double EARTH_RADIUS = 6371;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private UserRepository userRepository;
    public String compareCoordinates(Location data, User user) { // Метод для сравнения координат пользователя и поиска бункеров
        float userLatitude = data.getLatitude(); // Извлекаем широту из объекта Location
        float userLongitude = data.getLongitude();// Извлекаем долготу из объекта Location
        List<Location> matchingLocations = locationRepository.findAllByLatitudeAndLongitude(userLatitude, userLongitude);// Ищем все бункерs с заданными координатами
        if (!matchingLocations.isEmpty()) { // Если найдены бункеры с совпадающими координатами
            Location selectedLocation = matchingLocations.stream() // Выбираем бункер с наименьшим идентификатором
                    .min(Comparator.comparingLong(Location::getId))
                    .orElse(null);
            if (selectedLocation != null) { // Если найден выбранный бункер, возвращаем сообщение с адресом
                String address = selectedLocation.getAddress();
                return "в вашем доме найден бункер! Адрес дома и бункера: " + address;
            } else { // Если выбранный бункер не найден, возвращаем сообщение без адреса  todo помоему я невывожу эти сообщения или вывощу но после вывода перенаправляю пользователя и он их не видит удалить их надо мб
                return "в вашем доме найден бункер";
            }
        } else {// Если бункеры с совпадающими координатами не найдены
            // Ищем ближайшую локацию (бункер) по координатам пользователя
            Location nearestLocation = findNearestLocation(userLatitude, userLongitude);
            if (nearestLocation != null) {
                String nearestAddress = nearestLocation.getAddress();
                // Сохраните информацию о ближайшей локации пользователя в сущность User
                user.setLatitude(userLatitude);
                user.setLongitude(userLongitude);
                user.setNearestAddress(nearestAddress);
                userRepository.save(user);  // Сохраняем обновленного пользователя в репозитории
                // Возвращаем сообщение с адресом ближайшей локации
                return "в вашем доме бункеров не найдено. Ближайший бункер находится по адресу: " + nearestAddress;
            } else { // Если ближайшая локация (бункер) не найдена, возвращаем сообщение без адреса  todo помоему я невывожу эти сообщения или вывощу но после вывода перенаправляю пользователя и он их не видит удалить их надо мб
                return "в вашем доме бункеров не найдено";
            }
        }
    }
    // Метод для поиска ближайших координат в locationRepository
    private Location findNearestLocation(float targetLatitude, float targetLongitude) { // Метод для сравнения координат пользователя и поиска ближайшей локации (бункера)
        List<Location> allLocations = locationRepository.findAll();// Получаем список всех локаций  из репозитория
        Location nearestLocation = null;
        double minDistance = Double.MAX_VALUE; // Инициализируем переменные для хранения ближайшей локации и минимального расстояния
        for (Location location : allLocations) { // Итерируемся по всем локациям для сравнения расстояния до целевых координат пользователя
            // Преобразуем координаты из градусов в радианы
            double lat1 = Math.toRadians(targetLatitude);
            double lon1 = Math.toRadians(targetLongitude);
            double lat2 = Math.toRadians(location.getLatitude());
            double lon2 = Math.toRadians(location.getLongitude());
            // Вычисляем разницу между широтой и долготой в радианах
            double dLat = lat2 - lat1;
            double dLon = lon2 - lon1;
            // Вычисляем расстояние между двумя точками с помощью формулы haversine
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(lat1) * Math.cos(lat2) *
                            Math.sin(dLon / 2) * Math.sin(dLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = EARTH_RADIUS * c;
            // Если текущее расстояние меньше минимального обновляем ближайшую локацию и минимальное расстояние
            if (distance < minDistance) {
                minDistance = distance;
                nearestLocation = location;
            }
        }
        return nearestLocation; // Возвращаем ближайшую локацию (бункер)
    }
}