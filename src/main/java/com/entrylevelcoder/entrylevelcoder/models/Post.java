package com.entrylevelcoder.entrylevelcoder.models;


import jakarta.persistence.*;

/** Post Model */

@Entity
@Table(name = "posts")

public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long companyId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 3000)
    private String description;

    @Column(nullable = false)
    private Integer minSalary;

    @Column(nullable = false)
    private Integer maxSalary;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Boolean modality;

    @Column(nullable = false)
    private String postUrl;


    public Post() {
    }

    public Post(long id, long companyId, String title, String description, Integer minSalary, Integer maxSalary, String location, Boolean modality, String postUrl) {
        this.id = id;
        this.companyId = companyId;
        this.title = title;
        this.description = description;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.location = location;
        this.modality = modality;
        this.postUrl = postUrl;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getModality() {
        return modality;
    }

    public void setModality(Boolean modality) {
        this.modality = modality;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }
}
