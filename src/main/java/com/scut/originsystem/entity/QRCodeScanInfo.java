package com.scut.originsystem.entity;

public class QRCodeScanInfo {
    private int id;
    private String scan_location;
    private String scan_date;
    private int qrcode_id;

    public QRCodeScanInfo() {
    }

    public QRCodeScanInfo(String scan_location, String scan_date, int qrcode_id) {
        this.scan_location = scan_location;
        this.scan_date = scan_date;
        this.qrcode_id = qrcode_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScan_location() {
        return scan_location;
    }

    public void setScan_location(String scan_location) {
        this.scan_location = scan_location;
    }

    public String getScan_date() {
        return scan_date;
    }

    public void setScan_date(String scan_date) {
        this.scan_date = scan_date;
    }

    public int getQrcode_id() {
        return qrcode_id;
    }

    public void setQrcode_id(int qrcode_id) {
        this.qrcode_id = qrcode_id;
    }
}
