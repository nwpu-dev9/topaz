package org.dev9.topaz.common.entity;

import org.dev9.topaz.common.util.HashingUtil;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

// TODO: shiro: Boolean isAdmin?

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(columnDefinition = "TEXT", unique = true)
    private String phoneNumber;

    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String passwordHash;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String passwordSalt;

    @Column(nullable = false)
    private Instant signupTime;

    @Column(columnDefinition = "TEXT")
    private String profile;

    @Column(columnDefinition = "TEXT")
    private String avatarUrl;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    @JoinTable(name = "favorite_topic")
    private List<Topic> favoriteTopics;

    public User() {
    }

    public User(String name, String phoneNumber, String password, Instant signupTime) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.changePassword(password);
        this.signupTime = signupTime != null ? signupTime : Instant.now();
        this.profile = null;
        this.avatarUrl = null;
        this.favoriteTopics = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", signupTime=" + signupTime +
                '}';
    }

    public List<Topic> getFavoriteTopics() {
        return favoriteTopics;
    }

    public void addFavoriteTopic(Topic topic) {
        this.favoriteTopics.add(topic);
    }

    public void removeFavoriteTopic(Topic topic) {
        this.favoriteTopics.remove(topic);
    }

    public Integer getUserId() {
        return userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void changePassword(String newPassword) {
        this.passwordSalt = HashingUtil.generateSalt(32);
        this.passwordHash = HashingUtil.hashPassword(newPassword.toCharArray(), this.passwordSalt);
    }

    public Instant getSignupTime() {
        return signupTime;
    }

    public void setSignupTime(Instant signupTime) {
        this.signupTime = signupTime;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
