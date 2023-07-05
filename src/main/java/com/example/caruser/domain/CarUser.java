package com.example.caruser.domain;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "caruser")
public class CarUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String FIO;
    private int age;
    private double experience;
    private String rides;
    private String violation;
    private String password;

    @Transient
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public CarUser() {
    }

    public CarUser(String FIO, int age, double experience, String rides, String violation, String password) {
        this.FIO = FIO;
        this.age = age;
        this.experience = experience;
        this.rides = rides;
        this.violation = violation;
        this.password = passwordEncoder.encode(password);
    }

    public String getFIO() {
        return FIO;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        this.password = hashedPassword;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public void setRides(String rides) {
        this.rides = rides;
    }

    public void setViolation(String violation) {
        this.violation = violation;
    }

    public boolean checkPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, this.password);
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public double getExperience() {
        return experience;
    }

    public String getRides() {
        return rides;
    }

    public String getViolation() {
        return violation;
    }

    public Long getId() {
        return id;
    }

    public String toString() {
        return "CarUser: " + this.id + ", " + this.FIO + ", " + this.age + " years, " + this.experience + " years of experience, " + "last ride: " + this.rides + ", violation: " + this.violation;
    }

}
