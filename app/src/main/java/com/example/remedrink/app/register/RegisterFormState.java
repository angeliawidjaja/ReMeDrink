package com.example.remedrink.app.register;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class RegisterFormState {
    @Nullable
    private Integer fullnameError;
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer confirmPasswordError;

    public RegisterFormState(@Nullable Integer fullnameError, @Nullable Integer usernameError, @Nullable Integer emailError, @Nullable Integer passwordError, @Nullable Integer confirmPasswordError) {
        this.fullnameError = fullnameError;
        this.usernameError = usernameError;
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.confirmPasswordError = confirmPasswordError;
    }

    RegisterFormState() {
        this.fullnameError = null;
        this.usernameError = null;
        this.emailError = null;
        this.passwordError = null;
        this.confirmPasswordError = null;
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
    public Integer getEmailError() {
        return emailError;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    public Integer getConfirmPasswordError() {
        return confirmPasswordError;
    }
}