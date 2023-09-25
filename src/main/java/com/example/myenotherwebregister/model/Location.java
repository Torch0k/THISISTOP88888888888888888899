package com.example.myenotherwebregister.model;

import jakarta.persistence.*;

@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    float latitude;
    float longitude;
    String address;
    String bunkerName;

    public String getBunkerName() {
        return bunkerName;
    }
    public void setBunkerName(String bunkerName) {
        this.bunkerName = bunkerName;
    }
    public void setId(long id) {
        this.id = id;
    }
    public float getLatitude() {
        return latitude;
    }
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
    public float getLongitude() {
        return longitude;
    }
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
