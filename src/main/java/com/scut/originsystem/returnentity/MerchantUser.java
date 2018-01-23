package com.scut.originsystem.returnentity;

import com.scut.originsystem.entity.User;

/**
 * @create 2018-01-16 14:18
 * @desc
 **/
public class MerchantUser extends User {
    private String merchant_name;
    private int merchant_id;
    private int qrcode_total;
    private int qrcode_left;

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public int getQrcode_total() {
        return qrcode_total;
    }

    public void setQrcode_total(int qrcode_total) {
        this.qrcode_total = qrcode_total;
    }

    public int getQrcode_left() {
        return qrcode_left;
    }

    public void setQrcode_left(int qrcode_left) {
        this.qrcode_left = qrcode_left;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }
}
