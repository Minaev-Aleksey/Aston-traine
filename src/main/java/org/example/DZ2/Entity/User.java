package org.example.DZ2.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "dz_user")

public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "created_at", nullable = false)
    private LocalDate localDate = LocalDate.now();

    public User() {
    }

    public User(Long id, String name, String email, int age, LocalDate localDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.localDate = localDate;
    }

    public User(String name, String email, int age, LocalDate localDate) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.localDate = localDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", localDate=" + localDate +
                '}';
    }
}