package com.motherbase.apirest.model.mission;

public enum MessagesMission {

    CAN_NOT_TAKE_MISSION("%s can't take mission %s");

    private String msg;

    MessagesMission(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
