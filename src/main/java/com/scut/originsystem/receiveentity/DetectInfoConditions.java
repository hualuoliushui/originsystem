package com.scut.originsystem.receiveentity;

import com.scut.originsystem.annotation.SqlConditionAnno;
import com.scut.originsystem.annotation.SqlCountAnno;
import com.scut.originsystem.enums.SqlConditionType;
import com.scut.originsystem.util.SqlUtil;

public class DetectInfoConditions extends PageConditions{
    @SqlCountAnno
    @SqlConditionAnno(type = SqlConditionType.AND_LIKE)
    private String detect_code;
    @SqlCountAnno
    @SqlConditionAnno(type = SqlConditionType.AND_LIKE)
    private String detect_name;
    @SqlCountAnno
    @SqlConditionAnno(column="detect_date",type = SqlConditionType.AND_BETWEEN_DATE_START)
    private String detect_date_start;
    @SqlCountAnno
    @SqlConditionAnno(column="detect_date",type = SqlConditionType.AND_BETWEEN_DATE_END)
    private String detect_date_end;
    @SqlCountAnno
    @SqlConditionAnno(type = SqlConditionType.AND_LIKE)
    private String detect_unit;
    @SqlCountAnno
    @SqlConditionAnno(type = SqlConditionType.AND_LIKE)
    private String detect_explain;
    @SqlCountAnno
    @SqlConditionAnno(type = SqlConditionType.AND_LIKE)
    private String detect_operation;
    @SqlCountAnno
    @SqlConditionAnno(type = SqlConditionType.AND_LIKE)
    private String detect_result;
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
    @SqlConditionAnno(type = SqlConditionType.AND_EQUAL)
    private int detect_sample_id;

    @SqlConditionAnno(column = "detect_code",type = SqlConditionType.ORDER_BY)
    private String order_by_detect_code;
    @SqlConditionAnno(column = "detect_name",type = SqlConditionType.ORDER_BY)
    private String order_by_detect_name;
    @SqlConditionAnno(column = "detect_date",type = SqlConditionType.ORDER_BY,is_date = true)
    private String order_by_detect_date;
    @SqlConditionAnno(column = "detect_unit",type = SqlConditionType.ORDER_BY)
    private String order_by_detect_unit;
    @SqlConditionAnno(column = "detect_explain",type = SqlConditionType.ORDER_BY)
    private String order_by_detect_explain;
    @SqlConditionAnno(column = "detect_operation",type = SqlConditionType.ORDER_BY)
    private String order_by_detect_operation;
    @SqlConditionAnno(column = "detect_result",type = SqlConditionType.ORDER_BY)
    private String order_by_detect_result;
    @SqlConditionAnno(type = SqlConditionType.IS_ASC)
    private int is_asc;

    public int getDetect_sample_id() {
        return detect_sample_id;
    }

    public void setDetect_sample_id(int detect_sample_id) {
        this.detect_sample_id = detect_sample_id;
    }



    public String getDetect_code() {

        return detect_code;
    }

    public void setDetect_code(String detect_code) {
        this.detect_code = detect_code;
    }

    public String getDetect_name() {
        return detect_name;
    }

    public void setDetect_name(String detect_name) {
        this.detect_name = detect_name;
    }

    public String getDetect_date_start() {
        return detect_date_start;
    }

    public void setDetect_date_start(String detect_date_start) {
        this.detect_date_start = detect_date_start;
    }

    public String getDetect_date_end() {
        return detect_date_end;
    }

    public void setDetect_date_end(String detect_date_end) {
        this.detect_date_end = detect_date_end;
    }

    public String getDetect_unit() {
        return detect_unit;
    }

    public void setDetect_unit(String detect_unit) {
        this.detect_unit = detect_unit;
    }

    public String getDetect_explain() {
        return detect_explain;
    }

    public void setDetect_explain(String detect_explain) {
        this.detect_explain = detect_explain;
    }

    public String getDetect_operation() {
        return detect_operation;
    }

    public void setDetect_operation(String detect_operation) {
        this.detect_operation = detect_operation;
    }

    public String getDetect_result() {
        return detect_result;
    }

    public void setDetect_result(String detect_result) {
        this.detect_result = detect_result;
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

    public String getOrder_by_detect_code() {
        return order_by_detect_code;
    }

    public void setOrder_by_detect_code(String order_by_detect_code) {
        this.order_by_detect_code = order_by_detect_code;
    }

    public String getOrder_by_detect_name() {
        return order_by_detect_name;
    }

    public void setOrder_by_detect_name(String order_by_detect_name) {
        this.order_by_detect_name = order_by_detect_name;
    }

    public String getOrder_by_detect_date() {
        return order_by_detect_date;
    }

    public void setOrder_by_detect_date(String order_by_detect_date) {
        this.order_by_detect_date = order_by_detect_date;
    }

    public String getOrder_by_detect_unit() {
        return order_by_detect_unit;
    }

    public void setOrder_by_detect_unit(String order_by_detect_unit) {
        this.order_by_detect_unit = order_by_detect_unit;
    }

    public String getOrder_by_detect_explain() {
        return order_by_detect_explain;
    }

    public void setOrder_by_detect_explain(String order_by_detect_explain) {
        this.order_by_detect_explain = order_by_detect_explain;
    }

    public String getOrder_by_detect_operation() {
        return order_by_detect_operation;
    }

    public void setOrder_by_detect_operation(String order_by_detect_operation) {
        this.order_by_detect_operation = order_by_detect_operation;
    }

    public String getOrder_by_detect_result() {
        return order_by_detect_result;
    }

    public void setOrder_by_detect_result(String order_by_detect_result) {
        this.order_by_detect_result = order_by_detect_result;
    }

    public int getIs_asc() {
        return is_asc;
    }

    public void setIs_asc(int is_asc) {
        this.is_asc = is_asc;
    }
}
