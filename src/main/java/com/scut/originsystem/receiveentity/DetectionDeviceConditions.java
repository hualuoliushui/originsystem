package com.scut.originsystem.receiveentity;

import com.scut.originsystem.annotation.SqlConditionAnno;
import com.scut.originsystem.annotation.SqlCountAnno;
import com.scut.originsystem.enums.SqlConditionType;

/**
 * @create 2018-01-10 15:27
 * @desc 搜索检测信息，条件查询，实体类
 **/
public class DetectionDeviceConditions extends PageConditions{
    @SqlCountAnno
    @SqlConditionAnno(type= SqlConditionType.AND_LIKE)
    private String device_code;
    @SqlCountAnno
    @SqlConditionAnno(type=SqlConditionType.AND_LIKE)
    private String device_name;
    @SqlCountAnno
    @SqlConditionAnno(type=SqlConditionType.AND_LIKE)
    private String device_description;
    @SqlCountAnno
    @SqlConditionAnno(type=SqlConditionType.AND_LIKE)
    private String device_location;
    @SqlCountAnno
    @SqlConditionAnno(type=SqlConditionType.AND_LIKE)
    private String device_gis;
    @SqlCountAnno
    @SqlConditionAnno(type=SqlConditionType.AND_LIKE)
    private String device_brand;
    @SqlCountAnno
    @SqlConditionAnno(type=SqlConditionType.AND_LIKE)
    private String device_model;

    @SqlCountAnno
    @SqlConditionAnno(column = "purchase_date",type=SqlConditionType.AND_BETWEEN_DATE_START)
    private String purchase_date_start;
    @SqlCountAnno
    @SqlConditionAnno(column = "purchase_date",type=SqlConditionType.AND_BETWEEN_DATE_END)
    private String purchase_date_end;
    @SqlCountAnno
    @SqlConditionAnno(column = "login_date",type=SqlConditionType.AND_BETWEEN_DATE_START)
    private String login_date_start;
    @SqlCountAnno
    @SqlConditionAnno(column = "login_date",type=SqlConditionType.AND_BETWEEN_DATE_END)
    private String login_date_end;
    @SqlCountAnno
    @SqlConditionAnno(column = "drop_date",type=SqlConditionType.AND_BETWEEN_DATE_START)
    private String drop_date_start;
    @SqlCountAnno
    @SqlConditionAnno(column = "drop_date",type=SqlConditionType.AND_BETWEEN_DATE_END)
    private String drop_date_end;

    @SqlCountAnno
    @SqlConditionAnno(type=SqlConditionType.AND_EQUAL)
    private int merchant_id;
    @SqlCountAnno
    @SqlConditionAnno(type=SqlConditionType.AND_EQUAL)
    private int online;

    @SqlConditionAnno(type = SqlConditionType.IS_ASC)
    private int is_asc;

    @SqlConditionAnno(column="device_code",type= SqlConditionType.ORDER_BY)
    private String order_by_device_code;
    @SqlConditionAnno(column="device_name",type=SqlConditionType.ORDER_BY)
    private String order_by_device_name;
    @SqlConditionAnno(column="device_description",type=SqlConditionType.ORDER_BY)
    private String order_by_device_description;
    @SqlConditionAnno(column="device_location",type=SqlConditionType.ORDER_BY)
    private String order_by_device_location;
    @SqlConditionAnno(column="device_gis",type=SqlConditionType.ORDER_BY)
    private String order_by_device_gis;
    @SqlConditionAnno(column="device_brand",type=SqlConditionType.ORDER_BY)
    private String order_by_device_brand;
    @SqlConditionAnno(column="device_model",type=SqlConditionType.ORDER_BY)
    private String order_by_device_model;

    @SqlConditionAnno(column="purchase_date",type= SqlConditionType.ORDER_BY,is_date = true)
    private String order_by_purchase_date;
    @SqlConditionAnno(column="login_date",type=SqlConditionType.ORDER_BY,is_date = true)
    private String order_by_login_date;
    @SqlConditionAnno(column="drop_date",type=SqlConditionType.ORDER_BY,is_date = true)
    private String order_by_drop_date;


    @Override
    public String toString() {
        return "DetectionDeviceConditions{" +
                "device_code='" + device_code + '\'' +
                ", device_name='" + device_name + '\'' +
                ", device_description='" + device_description + '\'' +
                ", device_location='" + device_location + '\'' +
                ", device_gis='" + device_gis + '\'' +
                ", device_brand='" + device_brand + '\'' +
                ", device_model='" + device_model + '\'' +
                ", purchase_date_start='" + purchase_date_start + '\'' +
                ", purchase_date_end='" + purchase_date_end + '\'' +
                ", login_date_start='" + login_date_start + '\'' +
                ", login_date_end='" + login_date_end + '\'' +
                ", drop_date_start='" + drop_date_start + '\'' +
                ", drop_date_end='" + drop_date_end + '\'' +
                ", merchant_id=" + merchant_id +
                ", online=" + online +
                ", is_asc=" + is_asc +
                ", order_by_device_code='" + order_by_device_code + '\'' +
                ", order_by_device_name='" + order_by_device_name + '\'' +
                ", order_by_device_description='" + order_by_device_description + '\'' +
                ", order_by_device_location='" + order_by_device_location + '\'' +
                ", order_by_device_gis='" + order_by_device_gis + '\'' +
                ", order_by_device_brand='" + order_by_device_brand + '\'' +
                ", order_by_device_model='" + order_by_device_model + '\'' +
                ", order_by_purchase_date='" + order_by_purchase_date + '\'' +
                ", order_by_login_date='" + order_by_login_date + '\'' +
                ", order_by_drop_date='" + order_by_drop_date + '\'' +
                '}';
    }

    public String getDevice_code() {
        return device_code;
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_description() {
        return device_description;
    }

    public void setDevice_description(String device_description) {
        this.device_description = device_description;
    }

    public String getDevice_location() {
        return device_location;
    }

    public void setDevice_location(String device_location) {
        this.device_location = device_location;
    }

    public String getDevice_gis() {
        return device_gis;
    }

    public void setDevice_gis(String device_gis) {
        this.device_gis = device_gis;
    }

    public String getDevice_brand() {
        return device_brand;
    }

    public void setDevice_brand(String device_brand) {
        this.device_brand = device_brand;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public String getPurchase_date_start() {
        return purchase_date_start;
    }

    public void setPurchase_date_start(String purchase_date_start) {
        this.purchase_date_start = purchase_date_start;
    }

    public String getPurchase_date_end() {
        return purchase_date_end;
    }

    public void setPurchase_date_end(String purchase_date_end) {
        this.purchase_date_end = purchase_date_end;
    }

    public String getLogin_date_start() {
        return login_date_start;
    }

    public void setLogin_date_start(String login_date_start) {
        this.login_date_start = login_date_start;
    }

    public String getLogin_date_end() {
        return login_date_end;
    }

    public void setLogin_date_end(String login_date_end) {
        this.login_date_end = login_date_end;
    }

    public String getDrop_date_start() {
        return drop_date_start;
    }

    public void setDrop_date_start(String drop_date_start) {
        this.drop_date_start = drop_date_start;
    }

    public String getDrop_date_end() {
        return drop_date_end;
    }

    public void setDrop_date_end(String drop_date_end) {
        this.drop_date_end = drop_date_end;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getIs_asc() {
        return is_asc;
    }

    public void setIs_asc(int is_asc) {
        this.is_asc = is_asc;
    }

    public String getOrder_by_device_code() {
        return order_by_device_code;
    }

    public void setOrder_by_device_code(String order_by_device_code) {
        this.order_by_device_code = order_by_device_code;
    }

    public String getOrder_by_device_name() {
        return order_by_device_name;
    }

    public void setOrder_by_device_name(String order_by_device_name) {
        this.order_by_device_name = order_by_device_name;
    }

    public String getOrder_by_device_description() {
        return order_by_device_description;
    }

    public void setOrder_by_device_description(String order_by_device_description) {
        this.order_by_device_description = order_by_device_description;
    }

    public String getOrder_by_device_location() {
        return order_by_device_location;
    }

    public void setOrder_by_device_location(String order_by_device_location) {
        this.order_by_device_location = order_by_device_location;
    }

    public String getOrder_by_device_gis() {
        return order_by_device_gis;
    }

    public void setOrder_by_device_gis(String order_by_device_gis) {
        this.order_by_device_gis = order_by_device_gis;
    }

    public String getOrder_by_device_brand() {
        return order_by_device_brand;
    }

    public void setOrder_by_device_brand(String order_by_device_brand) {
        this.order_by_device_brand = order_by_device_brand;
    }

    public String getOrder_by_device_model() {
        return order_by_device_model;
    }

    public void setOrder_by_device_model(String order_by_device_model) {
        this.order_by_device_model = order_by_device_model;
    }

    public String getOrder_by_purchase_date() {
        return order_by_purchase_date;
    }

    public void setOrder_by_purchase_date(String order_by_purchase_date) {
        this.order_by_purchase_date = order_by_purchase_date;
    }

    public String getOrder_by_login_date() {
        return order_by_login_date;
    }

    public void setOrder_by_login_date(String order_by_login_date) {
        this.order_by_login_date = order_by_login_date;
    }

    public String getOrder_by_drop_date() {
        return order_by_drop_date;
    }

    public void setOrder_by_drop_date(String order_by_drop_date) {
        this.order_by_drop_date = order_by_drop_date;
    }
}
