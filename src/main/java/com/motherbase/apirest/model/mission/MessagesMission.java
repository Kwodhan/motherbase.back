package com.motherbase.apirest.model.mission;

public enum MessagesMission {

    CAN_NOT_TAKE_MISSION("%s can't take mission %s"),
    MISSION_NOT_FINISH("The mission is not finish");

    private String msg;

    MessagesMission(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
