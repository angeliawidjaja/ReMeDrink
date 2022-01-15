package com.example.pathway_jogging.app.register;

import androidx.annotation.Nullable;

public class RegisterResponse {
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

    public RegisterResponse(@Nullable String fullname, @Nullable String username, @Nullable String email, @Nullable String password, @Nullable String id) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public RegisterResponse(@Nullable Integer error) {
        this.error = error;
    }

    public RegisterResponse(@Nullable String fullname, @Nullable String username, @Nullable String email, @Nullable String password) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
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