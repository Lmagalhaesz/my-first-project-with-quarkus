package com.magalhaes.exceptions.enums;

public enum ErrorCodeEnum {
    DE0001("Documents error", "DE-0001"),

    DB0001("DataBase connection error", "DB-0001"),

    UE0001("User error", "UE-0001"),

    DIRE0001("Directory error", "DIRE-0001"),

    GE0001("Generic Error", "GE-0001");

    private String msg;
    private String code;

    ErrorCodeEnum(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
