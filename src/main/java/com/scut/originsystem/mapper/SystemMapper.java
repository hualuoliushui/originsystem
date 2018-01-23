package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.System;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "systemMapper")
public interface SystemMapper {
    //更新系统名字
    @Update("update system " +
            "set name = #{name}")
    int setName(@Param("name") String name);

    //获取系统名称
    @Select("select name from system limit 1")
    String getName();

    //更新系统logo
    @Update("update system " +
            "set logo = #{logo}")
    int setLogo(@Param("logo") String logo);

    //获取系统logo
    @Select("select logo from system limit 1")
    String getlogo();

    //获取系统信息
    @Select("select * from system limit 1")
    System findSystem( );
}
