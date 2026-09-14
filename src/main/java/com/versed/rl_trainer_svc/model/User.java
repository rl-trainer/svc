package com.versed.rl_trainer_svc.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String email;

    private boolean isVerified;

    private String googleSub;

    private Instant createdAt;
    private Instant updatedAt;

    private String avatarUrl;

    @OneToOne(mappedBy = "user")
    private Points points;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public boolean isVerified(){
        return this.isVerified;
    }

    public void setIsVerified(boolean isVerified){
        this.isVerified = isVerified;
    }

    public String getGoogleSub(){
        return this.googleSub;
    }

    public void setGoogleSub(String googleSub){
        this.googleSub = googleSub;
    }

    public Instant getCreatedAt(){
        return this.createdAt;
    }

    public void setCreatedAt(Instant createdAt){
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt(){
        return this.updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt){
        this.updatedAt = updatedAt;
    }

    public String getAvatarUrl(){
        return this.avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl){
        this.avatarUrl = avatarUrl;
    }

    public Points getPoints(){
        return this.points;
    }

    public void setPoints(Points points){
        this.points = points;
    }



}
