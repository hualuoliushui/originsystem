package com.scut.originsystem.receiveentity;

import com.scut.originsystem.annotation.SqlConditionAnno;
import com.scut.originsystem.annotation.SqlCountAnno;
import com.scut.originsystem.enums.SqlConditionType;

public class VideoInfoConditions extends PageConditions{
    @SqlCountAnno
    @SqlConditionAnno(type = SqlConditionType.AND_LIKE)
    private String video_code;
    @SqlCountAnno
    @SqlConditionAnno(type = SqlConditionType.AND_LIKE)
    private String video_description;
    @SqlCountAnno
    @SqlConditionAnno(type = SqlConditionType.AND_LIKE)
    private String video_location;
    @SqlCountAnno
    @SqlConditionAnno(type = SqlConditionType.AND_LIKE)
    private String video_name;
    @SqlCountAnno
    @SqlConditionAnno(type = SqlConditionType.AND_LIKE)
    private String video_url;
    @SqlCountAnno
    @SqlConditionAnno(type = SqlConditionType.AND_EQUAL)
    private int device_id;
    @SqlCountAnno
    @SqlConditionAnno(type = SqlConditionType.AND_EQUAL)
    private int good_id;
    @SqlCountAnno
    @SqlConditionAnno(type = SqlConditionType.AND_EQUAL)
    private int merchant_id;
    @SqlCountAnno
    @SqlConditionAnno(column = "create_date",type = SqlConditionType.AND_BETWEEN_DATE_START)
    private String create_date_start;
    @SqlCountAnno
    @SqlConditionAnno(column = "create_date",type = SqlConditionType.AND_BETWEEN_DATE_END)
    private String create_date_end;

    @SqlConditionAnno(column = "video_code", type = SqlConditionType.ORDER_BY)
    private String order_by_video_code;
    @SqlConditionAnno(column = "video_description", type = SqlConditionType.ORDER_BY)
    private String order_by_video_description;
    @SqlConditionAnno(column = "video_location", type = SqlConditionType.ORDER_BY)
    private String order_by_video_location;
    @SqlConditionAnno(column = "video_name", type = SqlConditionType.ORDER_BY)
    private String order_by_video_name;
    @SqlConditionAnno(column = "video_url", type = SqlConditionType.ORDER_BY)
    private String order_by_video_url;
    @SqlConditionAnno(column = "create_date", type = SqlConditionType.ORDER_BY ,is_date = true)
    private String order_by_create_date;
    @SqlConditionAnno(type = SqlConditionType.IS_ASC)
    private int is_asc;

    public String getVideo_code() {
        return video_code;
    }

    public void setVideo_code(String video_code) {
        this.video_code = video_code;
    }

    public String getVideo_description() {
        return video_description;
    }

    public void setVideo_description(String video_description) {
        this.video_description = video_description;
    }

    public String getVideo_location() {
        return video_location;
    }

    public void setVideo_location(String video_location) {
        this.video_location = video_location;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getCreate_date_start() {
        return create_date_start;
    }

    public void setCreate_date_start(String create_date_start) {
        this.create_date_start = create_date_start;
    }

    public String getCreate_date_end() {
        return create_date_end;
    }

    public void setCreate_date_end(String create_date_end) {
        this.create_date_end = create_date_end;
    }

    public String getOrder_by_video_code() {
        return order_by_video_code;
    }

    public void setOrder_by_video_code(String order_by_video_code) {
        this.order_by_video_code = order_by_video_code;
    }

    public String getOrder_by_video_description() {
        return order_by_video_description;
    }

    public void setOrder_by_video_description(String order_by_video_description) {
        this.order_by_video_description = order_by_video_description;
    }

    public String getOrder_by_video_location() {
        return order_by_video_location;
    }

    public void setOrder_by_video_location(String order_by_video_location) {
        this.order_by_video_location = order_by_video_location;
    }

    public String getOrder_by_video_name() {
        return order_by_video_name;
    }

    public void setOrder_by_video_name(String order_by_video_name) {
        this.order_by_video_name = order_by_video_name;
    }

    public String getOrder_by_video_url() {
        return order_by_video_url;
    }

    public void setOrder_by_video_url(String order_by_video_url) {
        this.order_by_video_url = order_by_video_url;
    }

    public String getOrder_by_create_date() {
        return order_by_create_date;
    }

    public void setOrder_by_create_date(String order_by_create_date) {
        this.order_by_create_date = order_by_create_date;
    }

    public int getIs_asc() {
        return is_asc;
    }

    public void setIs_asc(int is_asc) {
        this.is_asc = is_asc;
    }
}
