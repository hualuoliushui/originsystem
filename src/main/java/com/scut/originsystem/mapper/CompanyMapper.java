package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.Company;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "companyMapper")
public interface CompanyMapper {
    String tableName = "company_t";

    //插入公司信息
    @Insert("insert into " + tableName + "(company_name,company_introduction,gis_location,business_scope,company_location,company_area,legal_person,company_code,company_picture,merchant_id,area_code) " +
            "values(#{company_name},#{company_introduction},#{gis_location},#{business_scope},#{company_location},#{company_area},#{legal_person},#{company_code},#{company_picture},#{merchant_id},#{area_code})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn = "id")
    int insertCompany(Company company);

    //修改公司信息
    @Update("update " + tableName + " " +
            "set company_name = #{company_name},company_introduction = #{company_introduction}," +
            "gis_location = #{gis_location},business_scope = #{business_scope},company_location = #{company_location}," +
            "company_area = #{company_area},legal_person = #{legal_person}," +
            "company_code = #{company_code},company_picture = #{company_picture},merchant_id = #{merchant_id},area_code=#{area_code} " +
            "where id = #{id}")
    int updateCompany(Company company);

    //查找公司
    @Select("select * from " + tableName + " where merchant_id =#{merchant_id}")
    Company findCompanyByMerchantId(@Param("merchant_id") int merchant_id);

    @Select("select * from " + tableName + " where merchant_id =#{merchant_id}")
    Company find(@Param("merchant_id") int merchant_id);
}
