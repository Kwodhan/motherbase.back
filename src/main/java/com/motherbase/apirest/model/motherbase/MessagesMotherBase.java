package com.motherbase.apirest.model.motherbase;

public enum MessagesMotherBase {
    CAN_NOT_MOVE_STAFF("%s can't move from department: %s to department: %s");

    private String msg;

    MessagesMotherBase(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
