package com.scut.originsystem.entity;

import javax.validation.constraints.Size;
import java.util.List;

public class QRCode {
    private int id;
    @Size(max = 100,message = "最大长度为100")
    private String qrcode_url;
    @Size(max = 100,message = "最大长度为100")
    private String qrcode_valid;
    private int scan_time;
    @Size(max = 100,message = "最大长度为100")
    private String first_scan;
    private int good_id;
    private List<QRCodeScanInfo> scanInfoList;

    public List<QRCodeScanInfo> getScanInfoList() {
        return scanInfoList;
    }

    public void setScanInfoList(List<QRCodeScanInfo> scanInfoList) {
        this.scanInfoList = scanInfoList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQrcode_url() {
        return qrcode_url;
    }

    public void setQrcode_url(String qrcode_url) {
        this.qrcode_url = qrcode_url;
    }

    public String getQrcode_valid() {
        return qrcode_valid;
    }

    public void setQrcode_valid(String qrcode_valid) {
        this.qrcode_valid = qrcode_valid;
    }

    public int getScan_time() {
        return scan_time;
    }

    public void setScan_time(int scan_time) {
        this.scan_time = scan_time;
    }

    @Override
    public String toString() {
        return "QRCode{" +
                "id=" + id +
                ", qrcode_url='" + qrcode_url + '\'' +
                ", qrcode_valid='" + qrcode_valid + '\'' +
                ", scan_time=" + scan_time +
                ", first_scan='" + first_scan + '\'' +
                ", good_id=" + good_id +
                '}';
    }

    public String getFirst_scan() {
        return first_scan;
    }

    public void setFirst_scan(String first_scan) {
        this.first_scan = first_scan;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }
}
