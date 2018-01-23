package com.scut.originsystem.validationInterface;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public interface InsertUser {
    Integer getId();

    Integer getActivation_code();
    //只有数字和字母
    @Size(min=2,max=30,message="名字长度必须在2和30之间")
//    @Pattern(regexp = "[a-zA-Z0-9]+", message = "用户名只允许数字和字母")
    String getUsername();
    //8-16，允许数字字母特殊符号
//    @Size(min=8,max=16,message="密码长度必须在8和16之间")
    String getPassword();
    @NotEmpty
    String getRole();
    @NotEmpty
    String getContact_name();
    //13999999999，020-8888888
    @Pattern(regexp = "^1\\d{10}$|^\\d{3}-\\d{8}$|^\\d{4}-\\d{7,8}$", message = "请用十一位手机号或者固定电话形式")
    String getPhone();
    @Email
    String getEmail();

    String getCreate_date();
    @NotNull
    String getArea_code();
}
