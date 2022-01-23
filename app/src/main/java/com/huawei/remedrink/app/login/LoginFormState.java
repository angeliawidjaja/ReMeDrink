package com.huawei.remedrink.app.login;

import androidx.annotation.Nullable;

class LoginFormState {
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer passwordError;

    LoginFormState(@Nullable Integer emailError, @Nullable Integer passwordError) {
        this.emailError = emailError;
        this.passwordError = passwordError;
    }

    LoginFormState() {
        this.emailError = null;
        this.passwordError = null;
    }

    @Nullable
    Integer getEmailError() {
        return emailError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }
}