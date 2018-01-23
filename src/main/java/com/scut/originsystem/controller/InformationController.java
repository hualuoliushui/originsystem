package com.scut.originsystem.controller;

import com.scut.originsystem.annotation.SystemLogControllerAnno;
import com.scut.originsystem.entity.*;
import com.scut.originsystem.receiveentity.DetectInfoConditions;
import com.scut.originsystem.receiveentity.DetectionDeviceConditions;
import com.scut.originsystem.receiveentity.VideoDeviceConditions;
import com.scut.originsystem.receiveentity.VideoInfoConditions;
import com.scut.originsystem.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/manager")
public class InformationController {
    @Autowired
    InformationService informationService;

    ///{{{视频设备管理
    //添加视频设备
    @RequestMapping(value = "/addVideo",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "添加视频设备")
    public Object addVideo(@Validated @RequestBody VideoDevice videoDevice){
        return informationService.addVideo(videoDevice);
    }

    @RequestMapping(value = "/deleteVideoDevice",method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "删除视频设备")
    public Object deleteVideoDevice(@RequestParam(value = "id",required = true)int id){
        return informationService.deleteVideoDevice(id);
    }

    @RequestMapping(value = "/deleteVideoDeviceByMerchant",method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "删除视频设备")
    public Object deleteVideoDeviceByMerchant(@RequestParam(value = "merchant_id",required = true)int merchant_id){
        return informationService.deleteVideoDeviceByMerchant(merchant_id);
    }

    @RequestMapping(value = "/updateVideoDevice",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "更新视频设备")
    public Object updateVideoDevice(@Validated @RequestBody VideoDevice videoDevice){
        return informationService.updateVideoDevice(videoDevice);
    }

    //通过id查看视频设备
    @RequestMapping(value= "/getVideoDeviceById",method = RequestMethod.GET)
    @ResponseBody
    public Object getVideoDeviceById(@RequestParam(value="id",required = true)int id){
        return informationService.getVideoDeviceById(id);
    }

    //通过商户id查看视频设备
    @RequestMapping(value= "/getVideoDeviceByMerchant",method = RequestMethod.GET)
    @ResponseBody
    public Object getVideoDeviceByMerchant(@RequestParam(value="merchant_id",required = true)int merchant_id,
                                           @RequestParam(value = "page",required = false,defaultValue = "1")int page){
        return informationService.getVideoDeviceByMerchant(merchant_id,page);
    }

    //通过设备名查找视频设备
    @RequestMapping(value= "/getVideoDeviceByDeviceName",method = RequestMethod.GET)
    @ResponseBody
    public Object getVideoDeviceByDeviceName(@RequestParam(value="merchant_id",required = true)int merchant_id,
                                              @RequestParam(value = "device_name")String device_name,
                                             @RequestParam(value = "page",required = false,defaultValue = "1")int page){
        return informationService.getVideoDeviceByDeviceName(merchant_id,device_name,page);
    }

    @RequestMapping(value = "/findVideoDeviceConditions",method = RequestMethod.POST)
    @ResponseBody
    public Object findVideoDeviceConditions(@Validated @RequestBody VideoDeviceConditions videoDeviceConditions){
        return informationService.findVideoDeviceConditions(videoDeviceConditions);
    }
    ///}}}

    ///{{{检查设备管理
    //添加检测设备
    @RequestMapping(value = "/addDetection",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "添加检测设备")
    public Object addDetection(@Validated @RequestBody DetectionDevice detectionDevice){
        return informationService.addDetection(detectionDevice);
    }

    @RequestMapping(value = "/deleteDetectionDevice",method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "删除检测设备")
    public Object deleteDetectionDevice(@RequestParam(value = "id",required = true)int id){
        return informationService.deleteDetectionDevice(id);
    }

    @RequestMapping(value = "/deleteDetectionDeviceByMerchant",method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "删除检测设备")
    public Object deleteDetectionDeviceByMerchant(@RequestParam(value = "merchant_id",required = true)int merchant_id){
        return informationService.deleteVideoDeviceByMerchant(merchant_id);
    }

    @RequestMapping(value = "/updateDetectionDevice",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "更新检测设备")
    public Object updateDetectionDevice(@Validated @RequestBody DetectionDevice detectionDevice){
        return informationService.updateDetectionDevice(detectionDevice);
    }

    //通过id查看检测设备
    @RequestMapping(value= "/getDetectDeviceByid",method = RequestMethod.GET)
    @ResponseBody
    public Object getDetectDeviceByid(@RequestParam(value="id",required = true)int id){
        return informationService.getDetectDeviceById(id);
    }

    //通过商户id查看检测设备
    @RequestMapping(value= "/getDetectDeviceByMerchant",method = RequestMethod.GET)
    @ResponseBody
    public Object getDetectDeviceByMerchant(@RequestParam(value="merchant_id",required = true)int merchant_id,
                                            @RequestParam(value = "page",required = false,defaultValue = "1")int page){
        return informationService.getDetectDeviceByMerchant(merchant_id,page);
    }

    //通过设备名查找检测设备
    @RequestMapping(value= "/getDetectDeviceByDeviceName",method = RequestMethod.GET)
    @ResponseBody
    public Object getDetectDeviceByDeviceName(@RequestParam(value="merchant_id",required = true)int merchant_id,
                    @RequestParam(value = "device_name")String device_name,
                                              @RequestParam(value = "page",required = false,defaultValue = "1")int page){
        return informationService.getDetectDeviceByDeviceName(merchant_id,device_name,page);
    }

    @RequestMapping(value = "/findDetectionDeviceConditions",method = RequestMethod.POST)
    @ResponseBody
    public Object findDetectionDeviceConditions(@RequestBody DetectionDeviceConditions detectionDeviceConditions){
        return informationService.findDetectionDeviceConditions(detectionDeviceConditions);
    }

    @RequestMapping(value = "/findDetectionDevice_1s",method = RequestMethod.GET)
    @ResponseBody
    public Object findDetectionDevice_1s(@RequestParam(value = "device_code")String device_code,
                                         @RequestParam(value = "merchant_id")int merchant_id){
        return informationService.findDetectionDevice_1s(device_code,merchant_id);
    }
    ///}}}

    ///{{{视频信息管理
    //添加视频信息
    @RequestMapping(value = "/addVideoInfo",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "添加视频信息")
    public Object addVideoInfo(@Validated @RequestBody VideoInfo videoInfo){

        return informationService.addVideoInfo(videoInfo);
    }

    //删除
    @RequestMapping(value= "/deleteVideoInfo",method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "删除视频信息")
    public Object deleteVideoInfo(@RequestParam(value="id",required = true)int id){
        return informationService.deleteVideoInfo(id);
    }

    @RequestMapping(value = "/updateVideoInfo",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "更新视频信息")
    public Object updateVideoInfo(@Validated @RequestBody VideoInfo videoInfo){
        return informationService.updateVideoInfo(videoInfo);
    }

    //通过设备id查看视频信息
    @RequestMapping(value= "/getVideoInfoByDevice",method = RequestMethod.GET)
    @ResponseBody
    public Object getVideoInfoByDevice(@RequestParam(value="device_id",required = true)int device_id,
                                       @RequestParam(value = "page",required = false,defaultValue = "1")int page){
        return informationService.getVideoInfoByDevice(device_id,page);
    }

    //通过good_id查看视频信息
    @RequestMapping(value= "/getVideoInfoByGood",method = RequestMethod.GET)
    @ResponseBody
    public Object getVideoInfoByGood(@RequestParam(value="good_id",required = true)int good_id,
                                     @RequestParam(value = "page",required = false,defaultValue = "1")int page){
        return informationService.getVideoInfoByGood(good_id,page);
    }

    //通过merchant_id查看视频信息
    @RequestMapping(value= "/getVideoInfoByMerchant",method = RequestMethod.GET)
    @ResponseBody
    public Object getVideoInfoByMerchant(@RequestParam(value="merchant_id",required = true)int merchant_id,
                                         @RequestParam(value = "page",required = false,defaultValue = "1")int page){
        return informationService.getVideoInfoByMerchant(merchant_id,page);
    }

    //条件查询视频信息
    @RequestMapping(value = "/findVideoInfoConditions",method = RequestMethod.POST)
    @ResponseBody
    public Object findVideoInfoConditions(@Validated @RequestBody VideoInfoConditions v){
        return informationService.findVideoInfoConditions(v);
    }
    ///}}}

    ///{{{检测信息管理
    //添加检测信息
    @RequestMapping(value = "/addDetectInfo",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "添加检测信息")
    public Object addDetectInfo(@Validated @RequestBody DetectInfo detectInfo){
        return informationService.addDetectInfo(detectInfo);
    }

    //删除
    @RequestMapping(value= "/deleteDetectInfo",method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "删除检测信息")
    public Object deleteDetectInfo(@RequestParam(value="id",required = true)int id){
        return informationService.deleteDetectInfo(id);
    }

    @RequestMapping(value = "/updateDetectInfo",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "更新检测信息")
    public Object updateDetectInfo(@Validated @RequestBody DetectInfo videoInfo){
        return informationService.updateDetectInfo(videoInfo);
    }

    //通过设备id查看检测信息
    @RequestMapping(value= "/getDetectInfoByDevice",method = RequestMethod.GET)
    @ResponseBody
    public Object getDetectInfoByDevice(@RequestParam(value="device_id",required = true)int device_id,
                                        @RequestParam(value = "page",required = false,defaultValue = "1")int page){
        return informationService.getDetectInfoByDevice(device_id,page);
    }

    //通过id查看检测信息
    @RequestMapping(value= "/getDetectInfoById",method = RequestMethod.GET)
    @ResponseBody
    public Object getDetectInfoById(@RequestParam(value="id",required = true)int id){
        return informationService.getDetectInfoById(id);
    }

    //通过merchant_id查看检测信息
    @RequestMapping(value= "/getDetectInfoByMerchant",method = RequestMethod.GET)
    @ResponseBody
    public Object getDetectInfoByMerchant(@RequestParam(value="merchant_id",required = true)int merchant_id,
                                          @RequestParam(value="min_date",required = false)String min_date,
                                          @RequestParam(value="max_date",required = false)String max_date,
                                          @RequestParam(value="page",required = true,defaultValue = "1")String page){
        return informationService.getDetectInfoByMerchant(merchant_id,min_date,max_date,Integer.valueOf(page));
    }

    //通过good_id查看检测信息
    @RequestMapping(value= "/getDetectInfoByGood",method = RequestMethod.GET)
    @ResponseBody
    public Object getDetectInfoByGood(@RequestParam(value="good_id",required = true)int good_id,
                                      @RequestParam(value = "page",required = false,defaultValue = "1")int page){
        return informationService.getDetectInfoByGood(good_id,page);
    }

    @RequestMapping(value = "/findDetectInfoConditions",method = RequestMethod.POST)
    @ResponseBody
    public Object findDetectInfoConditions(@Validated @RequestBody DetectInfoConditions detectInfoConditions){
        return informationService.findDetectInfoConditions(detectInfoConditions);
    }
    ///}}}

    ///{{{促销信息管理
    // upload picture
    @RequestMapping(value = "/uploadPictureForPromotion",method = RequestMethod.POST)
    @ResponseBody
    public Object uploadPictureForPromotion(@RequestParam(value = "file",required = true)MultipartFile file){return informationService.uploadPictureForPromotion(file);}

    @RequestMapping(value= "/getPictureForPromotion",method = RequestMethod.GET)
    @ResponseBody
    public void getPictureForPromotion(@RequestParam(value="fileName",required = true)String fileName, HttpServletResponse response){
        informationService.getPictureForPromotion(fileName, response);
    }

    //添加促销
    @RequestMapping(value = "/addPromotion",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "添加促销信息")
    public Object addPromotion(@Validated @RequestBody Promotion promotion){
        return informationService.addPromotion(promotion);
    }

    //通过good_id删除促销
    @RequestMapping(value = "/deletePromotionByGoodType",method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "删除促销信息")
    public Object deletePromotionByGoodType(@RequestParam(value = "type_id")int type_id){
        return informationService.deletePromotionByGoodType(type_id);
    }

    //通过id删除促销
    @RequestMapping(value = "/deletePromotion",method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "删除促销信息")
    public Object deletePromotion(@RequestParam(value = "id")int id){
        return informationService.deletePromotionInfo(id);
    }

    //修改促销
    @RequestMapping(value = "/updatePromotion",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "更新促销信息")
    public Object updatePromotion(@Validated @RequestBody Promotion promotion){
        return informationService.updatePromotionInfo(promotion);
    }

    //通过good_id查看促销
    @RequestMapping(value= "/getPromotionByGoodType",method = RequestMethod.GET)
    @ResponseBody
    public Object getPromotionByGoodType(@RequestParam(value="type_id",required = true)int type_id,
                                     @RequestParam(value="page",required = false,defaultValue = "1")int page){
        return informationService.getPromotionByGoodType(type_id,page);
    }

    //通过merchant_id查看促销
    @RequestMapping(value= "/getPromotionByMerchant",method = RequestMethod.GET)
    @ResponseBody
    public Object getPromotionByMerchant(@RequestParam(value="merchant_id",required = true)int merchant_id,
                                         @RequestParam(value="page",required = false,defaultValue = "1")int page){
        return informationService.getPromotionByMerchant(merchant_id,page);
    }

    @RequestMapping(value = "/findAllPromotion",method = RequestMethod.GET)
    @ResponseBody
    public Object findAllPromotion(@RequestParam(value="page",required = false,defaultValue = "1")int page){
        return informationService.findAllPromotion(page);
    }
    ///}}}

    ///{{{商户商品操作日志管理
    // upload picture
    @RequestMapping(value = "/uploadPictureForOperation",method = RequestMethod.POST)
    @ResponseBody
    public Object uploadPictureForOperation(@RequestParam(value = "file",required = true)MultipartFile file){
        return informationService.uploadOperationPicture(file);
    }

    @RequestMapping(value= "/getPictureForOperation",method = RequestMethod.GET)
    @ResponseBody
    public void getPictureForOperation(@RequestParam(value="fileName",required = true)String fileName,
                                       HttpServletResponse response){
        informationService.downloadOperationPicture(fileName, response);
    }

    @RequestMapping(value = "/uploadOperationFile",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "上传操作日志附件")
    public Object uploadOperationFile(@RequestParam(value = "file",required = true)MultipartFile file){
        return informationService.uploadOperationFile(file);
    }
    
    @RequestMapping(value = "/downloadOperationFile",method = RequestMethod.GET)
    @ResponseBody
    public Object downloadOperationFile(@RequestParam(value = "fileName",required = true)String fileName){
        return informationService.downloadOperationFile(fileName);
    }

    @RequestMapping(value = "/addOperationLog",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "添加操作日志")
    public Object addOperationLog(@Validated @RequestBody OperationLog operationLog){
        return informationService.addOperationLog(operationLog);
    }


    @RequestMapping(value = "/deleteOperationLog",method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "删除操作日志")
    public Object deleteOperationLog(@RequestParam(value = "id")int id){
        return informationService.deleteOperationLog(id);
    }

    @RequestMapping(value = "/updateOperationLog",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "更新操作日志")
    public Object updateOperationLog(@Validated @RequestBody OperationLog operationLog){
        return informationService.updateOperationLog(operationLog);
    }

    @RequestMapping(value= "/findOperationLogByGood",method = RequestMethod.GET)
    @ResponseBody
    public Object findOperationLogByGood(@RequestParam(value="good_id",required = true)int good_id,
                                         @RequestParam(value = "page",required = false,defaultValue = "1")int page){
        return informationService.findOperationLogByGood(good_id,page);
    }

    @RequestMapping(value= "/findOperationLogByMerchant",method = RequestMethod.GET)
    @ResponseBody
    public Object findOperationLogByMerchant(@RequestParam(value="merchant_id",required = true)int merchant_id,
                                             @RequestParam(value = "page",required = false,defaultValue = "1")int page){
        return informationService.findOperationLogByMerchant(merchant_id,page);
    }

    @RequestMapping(value = "/findAllOperationLog",method = RequestMethod.GET)
    @ResponseBody
    public Object findAllOperationLog(@RequestParam(value = "page",required = false,defaultValue = "1")int page){
        return informationService.findAllOperationLog(page);
    }

    ///}}}

    ///{{{检测信息样本管理
    @RequestMapping(value = "/addDetectSample",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "添加检测信息模板")
    public Object addDetectSample(@Validated @RequestBody DetectSample detectSample){
        return informationService.addDetectSample(detectSample);
    }


    @RequestMapping(value = "/deleteDetectSample",method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "删除检测信息模板")
    public Object deleteDetectSample(@RequestParam(value = "id")int id){
        return informationService.deleteDetectSample(id);
    }

    @RequestMapping(value = "/updateDetectSample",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "更新检测信息模板")
    public Object updateDetectSample(@Validated @RequestBody DetectSample detectSample){
        return informationService.updateDetectSample(detectSample);
    }

    @RequestMapping(value= "/findAllDetectSample",method = RequestMethod.GET)
    @ResponseBody
    public Object findAllDetectSample(@RequestParam(value = "page",required = false,defaultValue = "1")int page){
        return informationService.findAllDetectSample(page);
    }

    @RequestMapping(value= "/findDetectSamplesByMerchant",method = RequestMethod.GET)
    @ResponseBody
    public Object findDetectSamplesByMerchant(@RequestParam(value = "merchant_id",required = true)int merchant_id,
                                              @RequestParam(value = "page",required = false,defaultValue = "1")int page){
        return informationService.findDetectSamplesByMerchant(merchant_id,page);
    }
    ///}}}

    //查看所有视频设备
    @RequestMapping(value= "/findAllVideo",method = RequestMethod.GET)
    @ResponseBody
    public Object findAllVideo( ){
        return informationService.findAllVideo();
    }

    //查看所有检测设备
    @RequestMapping(value= "/findAllDetection",method = RequestMethod.GET)
    @ResponseBody
    public Object findAllDetection( ){
        return informationService.findAllDetection();
    }
}
