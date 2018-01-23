package com.scut.originsystem.returnentity;

import com.scut.originsystem.entity.GoodType;

/**
 * @create 2018-01-17 10:16
 * @desc
 **/
public class MerchantGoodType extends GoodType {
    private String merchant_name;

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }
}
