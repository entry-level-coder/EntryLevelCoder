package com.entrylevelcoder.entrylevelcoder.models;


import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/** MODEL CLASS FOR USER */

@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (nullable = false, length = 50)
    private String firstName;

    @Column (nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;


    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
