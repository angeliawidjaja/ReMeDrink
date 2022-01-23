package com.huawei.remedrink.app.landing.profile.editprofile;

import androidx.annotation.Nullable;

class EditProfileFormState {
    @Nullable
    private Integer fullnameError;
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer oldPasswordError;
    @Nullable
    private Integer newPasswordError;

    public EditProfileFormState(@Nullable Integer fullnameError, @Nullable Integer usernameError, @Nullable Integer oldPasswordError, @Nullable Integer newPasswordError) {
        this.fullnameError = fullnameError;
        this.usernameError = usernameError;
        this.oldPasswordError = oldPasswordError;
        this.newPasswordError = newPasswordError;
    }

    public EditProfileFormState() {
    }

    @Nullable
    public Integer getFullnameError() {
        return fullnameError;
    }

    @Nullable
    public Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    public Integer getOldPasswordError() {
        return oldPasswordError;
    }

    @Nullable
    public Integer getNewPasswordError() {
        return newPasswordError;
    }
}