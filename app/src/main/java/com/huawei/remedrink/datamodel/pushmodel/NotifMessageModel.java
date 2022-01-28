package com.huawei.remedrink.datamodel.pushmodel;

/**
 * Created by Angelia Widjaja on 27-Jan-22 11:44.
 */
public class NotifMessageModel {
    private String data;
    private NotifAndroidModel android;
    private String[] token;

    public NotifMessageModel(String data, String[] token) {
        this.data = data;
        this.android = new NotifAndroidModel();
        this.token = token;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public NotifAndroidModel getAndroid() {
        return android;
    }

    public void setAndroid(NotifAndroidModel android) {
        this.android = android;
    }

    public String[] getToken() {
        return token;
    }

    public void setToken(String[] token) {
        this.token = token;
    }
}
