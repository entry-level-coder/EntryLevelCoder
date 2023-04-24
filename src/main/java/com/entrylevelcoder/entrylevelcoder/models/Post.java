package com.entrylevelcoder.entrylevelcoder.models;


import com.entrylevelcoder.entrylevelcoder.enums.Modality;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Post Model
 */

@Entity
@Table(name = "posts")
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id")
    private User company;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 3000)
    private String description;

    @Column(nullable = false)
    private Integer minSalary;

    @Column(nullable = false)
    private Integer maxSalary;

    @Column(nullable = false)
    private String city;

    @Column(columnDefinition = "CHAR(2) DEFAULT 'XX'")
    private String state;

    @Column(nullable = false, columnDefinition = "ENUM('REMOTE', 'HYBRID', 'IN_PERSON', 'NOT_PROVIDED_BY_EMPLOYER')")
    @Enumerated(EnumType.STRING)
    private Modality modality;

    @Column(nullable = false)
    private String postUrl;

    @ManyToMany(mappedBy = "posts", fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();


    public Post() {
    }

    public Post(Long id) {
        this.id = id;
    }

    public Post(User company, String title, String description, Integer minSalary, Integer maxSalary, String city, String state, Modality modality, String postUrl) {
        this.company = company;
        this.title = title;
        this.description = description;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.city = city;
        this.state = state;
        this.modality = modality;
        this.postUrl = postUrl;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCompany() {
        return company;
    }

    public void setCompany(User company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Integer minSalary) {
        this.minSalary = minSalary;
    }

    public Integer getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Integer maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Modality getModality() {
        return modality;
    }

    public void setModality(Modality modality) {
        this.modality = modality;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
