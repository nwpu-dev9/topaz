package org.dev9.topaz.api.model.result.impl;

import org.dev9.topaz.api.model.result.UserSearchResult;
import org.dev9.topaz.common.entity.User;

import java.time.Instant;

public class UserSearchResultImpl implements UserSearchResult {
    private Integer userId;
    private String phoneNumber;
    private String name;
    private Instant signupTime;
    private String profile;
    private String avatarUrl;

    public UserSearchResultImpl(User user){
        this.userId = user.getUserId();
        this.phoneNumber = null;
        this.name = user.getName();
        this.signupTime = null;
        this.profile = null;
        this.avatarUrl = null;
    }

    public UserSearchResultImpl(Integer userId, String phoneNumber, String name, Instant signupTime, String profile, String avatarUrl) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.signupTime = signupTime;
        this.profile = profile;
        this.avatarUrl = avatarUrl;
    }

    @Override
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Instant getSignupTime() {
        return signupTime;
    }

    public void setSignupTime(Instant signupTime) {
        this.signupTime = signupTime;
    }

    @Override
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
