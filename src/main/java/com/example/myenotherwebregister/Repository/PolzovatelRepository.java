package com.example.myenotherwebregister.Repository;

import com.example.myenotherwebregister.usr.Polzovatel;

import java.util.ArrayList;
import java.util.List;

public class PolzovatelRepository {
    private static List<Polzovatel> polzovateli = new ArrayList<>();

    static {
        polzovateli.add(new Polzovatel("user1", "password1"));
        polzovateli.add(new Polzovatel("user2", "password2"));
        // Добавьте больше пользователей
    }

    public static Polzovatel findByUsername(String username) {
        return polzovateli.stream()
                .filter(polzovatel -> polzovatel.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
    public static void addPolzovatel(Polzovatel polzovatel) {
        polzovateli.add(polzovatel);
    }

}