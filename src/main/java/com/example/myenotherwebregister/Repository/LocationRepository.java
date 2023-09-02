package com.example.myenotherwebregister.Repository;

import com.example.myenotherwebregister.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findAllByLatitudeAndLongitude(float latitude, float longitude);
}