package com.motherbase.apirest.controller.responsecustom;

public abstract class CustomerResponse {
    private String msgError;
    private String msg;


    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
