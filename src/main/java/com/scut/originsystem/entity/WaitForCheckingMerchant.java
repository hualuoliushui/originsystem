package com.scut.originsystem.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class WaitForCheckingMerchant {
    private int id;
    @Size(max = 100,message = "最大长度为100")
    @NotEmpty
    private String username;
    @Size(max = 100,message = "最大长度为100")
    @NotEmpty
    private String contact_name;
    //电话验证
    @Pattern(regexp = "^1\\d{10}$|^\\d{3}-\\d{8}$|^\\d{4}-\\d{7,8}$", message = "请用十一位手机号或者固定电话形式")
    @NotEmpty
    private String phone;
    @Email
    @NotEmpty
    private String email;
    @Size(max = 100,message = "最大长度为100")
    @NotEmpty
    private String merchant_name;
    @Size(max = 100,message = "最大长度为100")
    @NotEmpty
    private String company_name;
    @Size(max = 100,message = "最大长度为100")
    @NotEmpty
    private String company_introduction;
    @Size(max = 100,message = "最大长度为100")
    @NotEmpty
    private String gis_location;
    @Size(max = 100,message = "最大长度为100")
    @NotEmpty
    private String business_scope;
    @Size(max = 100,message = "最大长度为100")
    @NotEmpty
    private String company_location;
    @Size(max = 100,message = "最大长度为100")
    @NotEmpty
    private String company_area;
    @Size(max = 100,message = "最大长度为100")
    @NotEmpty
    private String legal_person;
    @Size(max = 100,message = "最大长度为100")
    @NotEmpty
    private String company_code;
    @Size(max = 100,message = "最大长度为100")
    @NotEmpty
    private String company_picture;
    private int merchant_id;
    private int activation_code;
    @Size(max = 100,message = "最大长度为100")
    private String create_date;
    private int has_expired;
    @Size(max = 100,message = "最大长度为100")
    @NotEmpty
    private String password;
    @Size(max = 100,message = "最大长度为100")
    @NotEmpty
    private String area_code;

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    @Override
    public String toString() {
        return "WaitForCheckingMerchant{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", contact_name='" + contact_name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", merchant_name='" + merchant_name + '\'' +
                ", company_name='" + company_name + '\'' +
                ", company_introduction='" + company_introduction + '\'' +
                ", gis_location='" + gis_location + '\'' +
                ", business_scope='" + business_scope + '\'' +
                ", company_location='" + company_location + '\'' +
                ", company_area='" + company_area + '\'' +
                ", legal_person='" + legal_person + '\'' +
                ", company_code='" + company_code + '\'' +
                ", company_picture='" + company_picture + '\'' +
                ", merchant_id=" + merchant_id +
                ", activation_code=" + activation_code +
                ", create_date='" + create_date + '\'' +
                ", has_expired=" + has_expired +
                ", password='" + password + '\'' +
                '}';
    }

    public void setUser(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.contact_name = user.getContact_name();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.create_date = user.getCreate_date();
        this.activation_code =user.getActivation_code();
    }

    public void setMerchant(Merchant merchant){
        this.merchant_id = merchant.getId();
        this.merchant_name = merchant.getMerchant_name();
    }

    public void setCompany(Company company){
        this.company_name = company.getCompany_name();
        this.company_introduction = company.getCompany_introduction();
        this.gis_location = company.getGis_location();
        this.business_scope = company.getBusiness_scope();
        this.company_location = company.getCompany_location();
        this.company_area = company.getCompany_area();
        this.legal_person = company.getLegal_person();
        this.company_code = company.getCompany_code();
        this.company_picture = company.getCompany_picture();
        this.area_code = company.getArea_code();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public int getHas_expired() {
        return has_expired;
    }

    public void setHas_expired(int has_expired) {
        this.has_expired = has_expired;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
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

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
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

    public int getMerchant_id() {
        return merchant_id;
    }

    public int getActivation_code() {
        return activation_code;
    }

    public void setActivation_code(int activation_code) {
        this.activation_code = activation_code;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }
}
