package com.scut.originsystem.enums;

public enum RoleType {
    ADMIN("ADMIN","平台管理员"),
    FINANCE("FINANCE","平台财务员"),
    OPERATOR("OPERATOR","平台操作员"),
    MERCHANT("MERCHANT","商户");

    private final String role;
    private final String name;

    RoleType(String role,String name) {
        this.role = role;
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "RoleType:" + role;
    }
}
