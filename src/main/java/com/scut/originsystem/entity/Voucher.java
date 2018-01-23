package com.scut.originsystem.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.Size;

public class Voucher {
    private int id;
    @Size(max = 100,message = "最大长度为100")
    private String voucher_code;
    private double voucher_amount;
    private int has_used;
    private int merchant_id;
    @Size(max = 100,message = "最大长度为100")
    private String use_date;
    @Size(max = 100,message = "最大长度为100")
    private String create_date;
    private int has_bid;
    @Size(max = 100,message = "最大长度为100")
    private String bid_date;
    private int bid_id;

    public String getUse_date() {
        return use_date;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "id=" + id +
                ", voucher_code='" + voucher_code + '\'' +
                ", voucher_amount=" + voucher_amount +
                ", has_used=" + has_used +
                ", merchant_id=" + merchant_id +
                ", use_date='" + use_date + '\'' +
                ", create_date='" + create_date + '\'' +
                ", has_bid=" + has_bid +
                ", bid_date='" + bid_date + '\'' +
                ", bid_id=" + bid_id +
                '}';
    }

    public void setUse_date(String use_date) {
        this.use_date = use_date;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public int getHas_bid() {
        return has_bid;
    }

    public void setHas_bid(int has_bid) {
        this.has_bid = has_bid;
    }

    public String getBid_date() {
        return bid_date;
    }

    public void setBid_date(String bid_date) {
        this.bid_date = bid_date;
    }

    public int getBid_id() {
        return bid_id;
    }

    public void setBid_id(int bid_id) {
        this.bid_id = bid_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoucher_code() {
        return voucher_code;
    }

    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }

    public double getVoucher_amount() {
        return voucher_amount;
    }

    public void setVoucher_amount(double voucher_amount) {
        this.voucher_amount = voucher_amount;
    }

    public int getHas_used() {
        return has_used;
    }

    public void setHas_used(int has_used) {
        this.has_used = has_used;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }
}
