package com.example.myenotherwebregister.model;

import jakarta.persistence.*;

@Entity
@Table(name = "UserPost")
public class UserPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String userMessage;
    String sender;

// гетеры и сеттеры
    public String getUserMessage() {
        return userMessage;
    }
    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public Long getId() {
        return id;
    }
}
