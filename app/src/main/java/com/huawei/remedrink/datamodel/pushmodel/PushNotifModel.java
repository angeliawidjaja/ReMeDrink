package com.huawei.remedrink.datamodel.pushmodel;

/**
 * Created by Angelia Widjaja on 27-Jan-22 11:43.
 */
// Documentation Link: https://developer.huawei.com/consumer/en/doc/quickapp-access-push-kit
public class PushNotifModel {
    private NotifMessageModel message;

    public PushNotifModel(NotifMessageModel message) {
        this.message = message;
    }

    public NotifMessageModel getMessage() {
        return message;
    }

    public void setMessage(NotifMessageModel message) {
        this.message = message;
    }
}
