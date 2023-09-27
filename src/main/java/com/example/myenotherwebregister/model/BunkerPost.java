package com.example.myenotherwebregister.model;

import jakarta.persistence.*;

@Entity
@Table(name = "BunkerPost")
public class BunkerPost {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String userMessage;
    String sender;
    String nearestAddress;

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

    public String getNearestAddress() {
        return nearestAddress;
    }

    public void setNearestAddress(String nearestAddress) {
        this.nearestAddress = nearestAddress;
    }

    public Long getId() {
        return id;
    }
}
