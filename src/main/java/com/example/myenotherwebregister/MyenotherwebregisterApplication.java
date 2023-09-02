package com.example.myenotherwebregister;

import com.example.myenotherwebregister.Repository.LocationRepository;
import com.example.myenotherwebregister.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@SpringBootApplication
public class MyenotherwebregisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyenotherwebregisterApplication.class, args);

    }


}
