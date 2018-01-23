package com.scut.originsystem.entity;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.LinkedList;
import java.util.List;

public class Area {
    private int id;
    //1:省；2：市；3：区
    private int type;
    @NotEmpty
    private String description;
    private String code;
    //表示从属关系，最高级应为长度为0的字符串
    private String belong_to_code;

    private List children = new LinkedList();

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Area{" +
                "id=" + id +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                ", belong_to_code='" + belong_to_code + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBelong_to_code() {
        return belong_to_code;
    }

    public void setBelong_to_code(String belong_to_code) {
        this.belong_to_code = belong_to_code;
    }
}
