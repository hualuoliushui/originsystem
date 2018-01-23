package com.scut.originsystem.entity;

public class GoodManager {
    private int id;
    private int qrcode_left;
    private int sell_number;
    private int good_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQrcode_left() {
        return qrcode_left;
    }

    @Override
    public String toString() {
        return "GoodManager{" +
                "id=" + id +
                ", qrcode_left=" + qrcode_left +
                ", sell_number=" + sell_number +
                ", good_id=" + good_id +
                '}';
    }

    public void setQrcode_left(int qrcode_left) {
        this.qrcode_left = qrcode_left;
    }

    public int getSell_number() {
        return sell_number;
    }

    public void setSell_number(int sell_number) {
        this.sell_number = sell_number;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }
}
