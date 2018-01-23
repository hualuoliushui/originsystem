package com.scut.originsystem.entity;

import com.scut.originsystem.annotation.ImportField;
import com.scut.originsystem.validationInterface.InsertUser;

public class User implements InsertUser{
    private int id;
//    //只有数字和字母
//    @Size(min=2,max=30,message="名字长度必须在2和30之间")
//    @Pattern(regexp = "[a-zA-Z0-9]+", message = "用户名只允许数字和字母")
    @ImportField
    private String username;
//    //8-16，允许数字字母特殊符号
//    @Size(min=8,max=16,message="密码长度必须在8和16之间")
    @ImportField
    private String password;
    @ImportField
    private String role;
    private int activation_code;
    @ImportField
    private String contact_name;
//    //13999999999，020-8888888
//    @Pattern(regexp = "^1\\d{10}$|^\\d{3}-\\d{8}$|^\\d{4}-\\d{7,8}$", message = "请用十一位手机号或者固定电话形式")
    @ImportField
    private String phone;
//    @Email
    @ImportField
    private String email;
    private String create_date;
    @ImportField
    private String area_code;
    private int check_state;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", activation_code=" + activation_code +
                ", contact_name='" + contact_name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", create_date='" + create_date + '\'' +
                ", area_code='" + area_code + '\'' +
                ", check_state=" + check_state +
                '}';
    }

    public int getCheck_state() {
        return check_state;
    }

    public void setCheck_state(int check_state) {
        this.check_state = check_state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getActivation_code() {
        return activation_code;
    }

    public void setActivation_code(int activation_code) {
        this.activation_code = activation_code;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }
}
