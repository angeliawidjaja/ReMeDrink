package com.huawei.remedrink.datamodel.user;

/**
 * Created by Angelia Widjaja on 27-Jan-22 23:09.
 */
public class AccessTokenModel {
    private String access_token;
    private Integer expires_in;
    private String token_type;
    private Integer sub_error;
    private String error_description;
    private Integer error;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public Integer getSub_error() {
        return sub_error;
    }

    public void setSub_error(Integer sub_error) {
        this.sub_error = sub_error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }
}
