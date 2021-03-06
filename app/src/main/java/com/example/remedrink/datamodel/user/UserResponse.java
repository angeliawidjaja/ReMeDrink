package com.example.remedrink.datamodel.user;

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
    private String newPassword;
    @Nullable
    private String id;
    @Nullable
    private Integer height;
    @Nullable
    private Integer weight;
    @Nullable
    private Integer waterIntakeIdeal;
    @Nullable
    private Integer waterIntakeGoal;
    @Nullable
    private Integer error;

    public UserResponse(@Nullable Integer error) {
        this.error = error;
    }

    public UserResponse(@Nullable String fullname, @Nullable String username, @Nullable String email, @Nullable String password, @Nullable Integer height, @Nullable Integer weight, @Nullable Integer waterIntakeGoal) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.height = height;
        this.weight = weight;
        this.waterIntakeGoal = waterIntakeGoal;
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

    @Nullable
    public Integer getHeight() {
        return height;
    }

    @Nullable
    public Integer getWeight() {
        return weight;
    }

    public void setFullname(@Nullable String fullname) {
        this.fullname = fullname;
    }

    public void setUsername(@Nullable String username) {
        this.username = username;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    public void setPassword(@Nullable String password) {
        this.password = password;
    }

    public void setHeight(@Nullable Integer height) {
        this.height = height;
    }

    public void setWeight(@Nullable Integer weight) {
        this.weight = weight;
    }

    @Nullable
    public Integer getWaterIntakeIdeal() {
        return waterIntakeIdeal;
    }

    public void setWaterIntakeIdeal(@Nullable Integer waterIntakeIdeal) {
        this.waterIntakeIdeal = waterIntakeIdeal;
    }

    @Nullable
    public Integer getWaterIntakeGoal() {
        return waterIntakeGoal;
    }

    public void setWaterIntakeGoal(@Nullable Integer waterIntakeGoal) {
        this.waterIntakeGoal = waterIntakeGoal;
    }

    @Nullable
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(@Nullable String newPassword) {
        this.newPassword = newPassword;
    }
}