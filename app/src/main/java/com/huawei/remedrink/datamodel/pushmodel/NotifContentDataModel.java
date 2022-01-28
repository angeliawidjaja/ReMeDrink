package com.huawei.remedrink.datamodel.pushmodel;

/**
 * Created by Angelia Widjaja on 27-Jan-22 11:57.
 */
public class NotifContentDataModel {
    private int pushtype;
    private PushBodyModel pushbody;

    public NotifContentDataModel() {
        // pushtype values:
        // 0: notification message
        // 1: data message
        this.pushtype = 0;
    }

    public PushBodyModel getPushbody() {
        return pushbody;
    }

    public void setPushbody(PushBodyModel pushbody) {
        this.pushbody = pushbody;
    }
}
