package com.scut.originsystem.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.Size;

public class TestEntity {
//    @JsonSerialize
    private String name;
//    @JsonDeserialize
    @Size(max = 4)
    private String code;

    public TestEntity() {

    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public TestEntity(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

