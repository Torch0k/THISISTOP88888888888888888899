package com.example.myenotherwebregister.controllers;

import com.example.myenotherwebregister.Repository.LocationRepository;
import com.example.myenotherwebregister.model.Location;

import com.example.myenotherwebregister.model.LocationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
public class LocationController {
    private static final double EARTH_RADIUS = 6371 ;
    @Autowired
    private LocationRepository locationRepository;

    @PostMapping("/api/compare_coordinates")
    public ResponseEntity<String> compareCoordinates(@RequestBody LocationData data) {
        float userLatitude = data.getLatitude();
        float userLongitude = data.getLongitude();

        List<Location> matchingLocations = locationRepository.findAllByLatitudeAndLongitude(userLatitude, userLongitude);

        if (!matchingLocations.isEmpty()) {
            Location selectedLocation = matchingLocations.stream()
                    .min(Comparator.comparingLong(Location::getId))
                    .orElse(null);

            if (selectedLocation != null) {
                String address = selectedLocation.getAddress(); // Получаем значение поля address
                return ResponseEntity.ok("Совпадение найдено! Адрес: " + address);
            } else {
                return ResponseEntity.ok("Совпадений не найдено");
            }
        } else {
            // Логика для поиска ближайших координат и адреса
            Location nearestLocation = findNearestLocation(userLatitude, userLongitude);
            if (nearestLocation != null) {
                String nearestAddress = nearestLocation.getAddress();
                return ResponseEntity.ok("Совпадений не найдено. Ближайший адрес: " + nearestAddress);
            } else {
                return ResponseEntity.ok("Совпадений не найдено");
            }
        }
    }

    // Метод для поиска ближайших координат
    private Location findNearestLocation(float targetLatitude, float targetLongitude) {
        List<Location> allLocations = locationRepository.findAll(); // Получаем все записи

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
}