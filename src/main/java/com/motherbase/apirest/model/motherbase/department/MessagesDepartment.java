package com.motherbase.apirest.model.motherbase.department;

public enum MessagesDepartment {

    CAN_NOT_UPGRADE_RESOURCE_MISSING("%s can't upgrade to next rank");

    private String msg;

    MessagesDepartment(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
