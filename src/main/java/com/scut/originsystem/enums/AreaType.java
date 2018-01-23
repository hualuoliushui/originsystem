package com.scut.originsystem.enums;

public enum AreaType {
    PROVINCE(1, "省"),
    CITY(2, "市"),
    DISTRICT(3, "区");

    private final int type;
    private final String msg;

    AreaType(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public int getType() {

        return type;
    }

    public String getMsg() {
        return msg;
    }
}
