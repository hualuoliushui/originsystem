package com.scut.originsystem.controller;

import com.scut.originsystem.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping(value = "/qrcode")
public class QRCodeController {
    @Autowired
    QRCodeService qrCodeService;

    /**
     * 前端请求一个二维码
     * 提供商品id
     * 后端提供URL
     * 并且在数据库插入二维码信息
     * 减少二维码数量
     */
    @RequestMapping(value = "/requestQRCode",method = RequestMethod.GET)
    @ResponseBody
    public Object requstQRCode(@RequestParam(value = "good_id", required = true) int good_id){
        return qrCodeService.requestQRCode(good_id);
    }

    /**
     * 前端扫描一个二维码，URL到后端
     * 后端获取二维码的code，查询二维码信息
     * 返回视频、检测、商户信息
     * 扫码次数，第一次扫码次数
     * 如果是第一次扫码就返回红包
     */
    @RequestMapping(value = "/sacnQRCode/{qrcode_url}",method = RequestMethod.GET)
    @ResponseBody
    public Object scanQRCode(@PathVariable("qrcode_url") String qrcode_url){
        return qrCodeService.scanQRCode(qrcode_url);
    }

    @RequestMapping(value = "/validQRCode",method = RequestMethod.GET)
    @ResponseBody
    public Object validQRCode(@RequestParam(value = "qrcode_url", required = true) String qrcode_url,
                              @RequestParam(value = "valid", required = true) String valid,
                              @RequestParam(value = "location", required = false,defaultValue = "无法识别地理位置") String location){
        return qrCodeService.validQRCode(qrcode_url,valid,location);
    }


    @RequestMapping(value = "/downloadQRCode",method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadQRCode(@RequestParam(value = "good_id", required = true) int good_id,
                                                 @RequestParam(value = "number", required = true) int number,
                                                 @RequestParam(value = "merchant_id", required = true) int merchant_id){
        return qrCodeService.downloadQRCode(good_id,number,merchant_id);
    }

    @RequestMapping(value = "/findValid",method = RequestMethod.GET)
    @ResponseBody
    public Object findValid(@RequestParam("qrcode_url") String qrcode_url,
                            @RequestParam("valid") String valid){
        return qrCodeService.findValid(qrcode_url,valid);

    }

    @RequestMapping(value = "/findHistory",method = RequestMethod.GET)
    @ResponseBody
    public Object findHistory(@RequestParam("merchant_id") int merchant_id,
                              @RequestParam(value = "page", required = false, defaultValue = "1") int page){
        return qrCodeService.findHistory(merchant_id,page);
    }

    @RequestMapping(value = "/downloadHistory",method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadHistory(@RequestParam("path") String path,
                                                  @RequestParam("good_id") int good_id,
                                                  @RequestParam("merchant_id") int merchant_id){
        return qrCodeService.downloadHistory(path,good_id,merchant_id);
    }

}
