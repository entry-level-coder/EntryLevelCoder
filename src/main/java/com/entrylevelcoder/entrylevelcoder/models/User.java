package com.entrylevelcoder.entrylevelcoder.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/** MODEL CLASS FOR USER */

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column (length = 50)
    private String firstName;
    @Column (length = 50)
    private String lastName;
    @Column(length = 100)
    private String username;
    @Column(nullable = false)
    private String password;

    //COMPANY PORTION OF USER
    @Column(name = "is_company")
    private boolean company;

    @Column(name = "company_name")
    private String companyName;
    @Column(name = "phone_number")
    private String number;
    private String industry;
    private String city;
    @Column(columnDefinition =  "CHAR(2) DEFAULT 'XX'")
    private String state;
    @Column(length = 2000)
    private String description;
    @Column(name = "job_board_url")
    private String url;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Users_posts", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> posts = new ArrayList<>();


    public User() {
    }

    public User(long id, String firstName, String lastName, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }
    //JOB SEEKER CONSTRUCTOR
    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, boolean company, String companyName, String number, String industry, String city, String state, String description, String url, List<Post> posts) {
        this.username = username;
        this.password = password;
        this.company = company;
        this.companyName = companyName;
        this.number = number;
        this.industry = industry;
        this.city = city;
        this.state = state;
        this.description = description;
        this.url = url;
        this.posts = posts;
    }

    //COMPANY CONSTRUCTOR W/O JOB POST
    public User(String username, String password, String companyName, String number, String industry, String city, String state, String description, String url) {
        this.username = username;
        this.password = password;
        this.companyName = companyName;
        this.number = number;
        this.industry = industry;
        this.city = city;
        this.state = state;
        this.description = description;
        this.url = url;
    }

    public User(String username, String password, String companyName, String number, String industry, String city, String state, String description, String url, List<Post> posts) {
        this.username = username;
        this.password = password;
        this.companyName = companyName;
        this.number = number;
        this.industry = industry;
        this.city = city;
        this.state = state;
        this.description = description;
        this.url = url;
        this.posts = posts;
    }

    public User(User copy) {
        id = copy.id;
        username = copy.username;
        company = copy.company;
        firstName = copy.firstName;
        lastName = copy.lastName;
        city = copy.city;
        state = copy.state;
        industry = copy.industry;
        number = copy.number;
        description = copy.description;
        url = copy.url;
        posts = copy.posts;
        password = copy.password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    //COMPANY GETTERS AND SETTERS

    public boolean isCompany() {
        return company;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public boolean getCompany() {
        return company;
    }

    public void setCompany(boolean company) {
        this.company = company;
    }

}
