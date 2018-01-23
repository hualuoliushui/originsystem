package com.scut.originsystem.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class Company {
//    @Size(max = 11,message = "最大长度为11")
    private int id;
    @Size(max = 100,message = "最大长度为100")
    private String company_name;
    @Size(max = 1000,message = "最大长度为1000")
    private String company_introduction;
    @Size(max = 100,message = "最大长度为100")
    private String gis_location;
    @Size(max = 100,message = "最大长度为100")
    private String business_scope;
    @Size(max = 100,message = "最大长度为100")
    private String company_location;
    @Size(max = 100,message = "最大长度为100")
    private String company_area;
    @Size(max = 100,message = "最大长度为100")
    @NotBlank
    private String area_code;
    @Size(max = 100,message = "最大长度为100")
    private String legal_person;
    @Size(max = 100,message = "最大长度为100")
    private String company_code;
    @Size(max = 100,message = "最大长度为100")
    private String company_picture;
//    @Size(max = 11,message = "最大长度为11")
    private int merchant_id;

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", company_name='" + company_name + '\'' +
                ", company_introduction='" + company_introduction + '\'' +
                ", gis_location='" + gis_location + '\'' +
                ", business_scope='" + business_scope + '\'' +
                ", company_location='" + company_location + '\'' +
                ", company_area='" + company_area + '\'' +
                ", area_code='" + area_code + '\'' +
                ", legal_person='" + legal_person + '\'' +
                ", company_code='" + company_code + '\'' +
                ", company_picture='" + company_picture + '\'' +
                ", merchant_id=" + merchant_id +
                '}';
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_introduction() {
        return company_introduction;
    }

    public void setCompany_introduction(String company_introduction) {
        this.company_introduction = company_introduction;
    }

    public String getGis_location() {
        return gis_location;
    }

    public void setGis_location(String gis_location) {
        this.gis_location = gis_location;
    }

    public String getBusiness_scope() {
        return business_scope;
    }

    public void setBusiness_scope(String business_scope) {
        this.business_scope = business_scope;
    }

    public String getCompany_location() {
        return company_location;
    }

    public void setCompany_location(String company_location) {
        this.company_location = company_location;
    }

    public String getCompany_area() {
        return company_area;
    }

    public void setCompany_area(String company_area) {
        this.company_area = company_area;
    }

    public String getLegal_person() {
        return legal_person;
    }

    public void setLegal_person(String legal_person) {
        this.legal_person = legal_person;
    }

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getCompany_picture() {
        return company_picture;
    }

    public void setCompany_picture(String company_picture) {
        this.company_picture = company_picture;
    }

}
