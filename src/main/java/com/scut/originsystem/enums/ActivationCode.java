package com.scut.originsystem.enums;

public enum ActivationCode {
    NOT_ACTIVATE(0, "未激活"),
    ACTIVATED(1, "已激活"),
    CANCEL(2, "注销"),
    NOT_USED(3, "未启用"),
    NOT_PASS(4, "审核不通过");



    private final int code;
    private final String msg;

    ActivationCode(int role, String msg) {
        this.code = role;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
