package com.example.myenotherwebregister.controllers;

import com.example.myenotherwebregister.Repository.CoordinatesRepository;
import com.example.myenotherwebregister.model.polzovatelCoordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CoordinateController {

    @Autowired
    private CoordinatesRepository coordinatesRepository;

    @PostMapping("/coordinates")
    public String saveCoordinates(@RequestBody polzovatelCoordinates coordinates) {
        polzovatelCoordinates savedCoordinates = coordinatesRepository.save(coordinates);
        return "Coordinates saved with ID: " + savedCoordinates.getId();
    }
}