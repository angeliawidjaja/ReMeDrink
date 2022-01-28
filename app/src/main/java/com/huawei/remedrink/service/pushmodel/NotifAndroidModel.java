package com.huawei.remedrink.service.pushmodel;

/**
 * Created by Angelia Widjaja on 27-Jan-22 11:47.
 */
public class NotifAndroidModel {
    private int fast_app_target;
    private String delivery_priority;

    public NotifAndroidModel() {
        // The value 1 indicates that a push message is sent to a quick app running by Huawei Quick App Loader.
        // To send a push message to a quick app from AppGallery, set this parameter to 2.
        this.fast_app_target = 1;
        this.delivery_priority = "HIGH";
    }
}
