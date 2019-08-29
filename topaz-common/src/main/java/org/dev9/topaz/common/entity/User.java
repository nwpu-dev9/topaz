package org.dev9.topaz.common.entity;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

// TODO: shiro: Boolean isAdmin?

@Entity
@Table(name = "`USER`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(columnDefinition = "TEXT")
    private String phoneNumber;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String encryptedPassword;

    @Column(nullable = false)
    private Instant signupTime;

    @Column(columnDefinition = "TEXT")
    private String profile;

    @Column(columnDefinition = "TEXT")
    private String avatarUrl;

    @OneToMany
    @JoinTable(name = "favorite_topic")
    private List<Topic> favoriteTopics;

    public User() {
    }

    public User(String phoneNumber, String name, String encryptedPassword, Instant signupTime, String profile, String avatarUrl, List<Topic> favoriteTopics) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.encryptedPassword = encryptedPassword;
        this.signupTime = signupTime;
        this.profile = profile;
        this.avatarUrl = avatarUrl;
        this.favoriteTopics = favoriteTopics;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", name='" + name + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                ", signupTime=" + signupTime +
                ", profile='" + profile + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }

    public List<Topic> getFavoriteTopics() {
        return favoriteTopics;
    }

    public void setFavoriteTopics(List<Topic> favoriteTopics) {
        this.favoriteTopics = favoriteTopics;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
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
