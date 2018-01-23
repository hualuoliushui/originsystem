package com.scut.originsystem.returnentity;

import java.util.List;
import java.util.Map;

public class Report {
    private List<String> goodNames;
    private int qRCodeTotal;
    private int goodTotal;
    private Map<String ,Integer> goodAndQRCodeTotal;
    private Map<String ,Integer> goodAndGoodTotal;
    private double voucherTotal;
    private int voucherNumber;
    private int voucherUsedNumber;

    public List<String> getGoodNames() {
        return goodNames;
    }

    public void setGoodNames(List<String> goodNames) {
        this.goodNames = goodNames;
    }

    public int getqRCodeTotal() {
        return qRCodeTotal;
    }

    public void setqRCodeTotal(int qRCodeTotal) {
        this.qRCodeTotal = qRCodeTotal;
    }

    public int getGoodTotal() {
        return goodTotal;
    }

    public void setGoodTotal(int goodTotal) {
        this.goodTotal = goodTotal;
    }

    public Map<String, Integer> getGoodAndQRCodeTotal() {
        return goodAndQRCodeTotal;
    }

    public void setGoodAndQRCodeTotal(Map<String, Integer> goodAndQRCodeTotal) {
        this.goodAndQRCodeTotal = goodAndQRCodeTotal;
    }

    public Map<String, Integer> getGoodAndGoodTotal() {
        return goodAndGoodTotal;
    }

    public void setGoodAndGoodTotal(Map<String, Integer> goodAndGoodTotal) {
        this.goodAndGoodTotal = goodAndGoodTotal;
    }

    public double getVoucherTotal() {
        return voucherTotal;
    }

    public void setVoucherTotal(double voucherTotal) {
        this.voucherTotal = voucherTotal;
    }

    public int getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(int voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public int getVoucherUsedNumber() {
        return voucherUsedNumber;
    }

    public void setVoucherUsedNumber(int voucherUsedNumber) {
        this.voucherUsedNumber = voucherUsedNumber;
    }
}
