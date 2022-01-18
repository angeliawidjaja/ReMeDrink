package com.example.remedrink.datamodel;

import androidx.annotation.Nullable;

public class UserResponse {
    @Nullable
    private String fullname;
    @Nullable
    private String username;
    @Nullable
    private String email;
    @Nullable
    private String password;
    @Nullable
    private String id;
    @Nullable
    private Integer error;

    public UserResponse(@Nullable String fullname, @Nullable String username, @Nullable String email, @Nullable String password, @Nullable String id) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public UserResponse(@Nullable Integer error) {
        this.error = error;
    }

    public UserResponse(@Nullable String fullname, @Nullable String username, @Nullable String email, @Nullable String password) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserResponse() {

    }

    @Nullable
    public String getFullname() {
        return fullname;
    }

    @Nullable
    public String getUsername() {
        return username;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    @Nullable
    public String getId() {
        return id;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}