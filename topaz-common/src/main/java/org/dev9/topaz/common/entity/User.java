package org.dev9.topaz.common.entity;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

// TODO: shiro: Boolean isAdmin?

@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column
    private String phoneNumber;

    @Column
    private String name;

    @Column
    private String encryptedPassword;

    @Column
    private Date signupTime;

    @Column
    private String profile;

    @Column
    private String avatarUrl;

    public User(){}

    public User(String phoneNumber, String name, String encryptedPassword, Date signupTime, String profile, String avatarUrl) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.encryptedPassword = encryptedPassword;
        this.signupTime = signupTime;
        this.profile = profile;
        this.avatarUrl = avatarUrl;
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

    public Date getSignupTime() {
        return signupTime;
    }

    public void setSignupTime(Date signupTime) {
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
