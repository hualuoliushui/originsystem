package com.scut.originsystem.service;

import com.scut.originsystem.entity.*;
import com.scut.originsystem.mapper.*;
import com.scut.originsystem.receiveentity.DetectInfoConditions;
import com.scut.originsystem.receiveentity.DetectionDeviceConditions;
import com.scut.originsystem.receiveentity.VideoDeviceConditions;
import com.scut.originsystem.receiveentity.VideoInfoConditions;
import com.scut.originsystem.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InformationService {
    @Autowired
    VideoDeviceMapper videoDeviceMapper;
    @Autowired
    DetectionDeviceMapper detectionDeviceMapper;
    @Autowired
    VideoInfoMapper videoInfoMapper;
    @Autowired
    DetectInfoMapper detectInfoMapper;
    @Autowired
    PromotionMapper promotionMapper;
    @Autowired
    OperationLogMapper operationLogMapper;
    @Autowired
    DetectSampleMapper detectSampleMapper;
    @Autowired
    TokenSessionService tokenSessionService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    protected static Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 保存图片的相对路径
    private static String promotion_sub_dir = "/static/images/upload/";
    private static String operation_file_sub_dir = "/static/files/operation_files";
    private static String operation_picture_sub_dir = "/static/images/upload/operation";

    ///{{{视频设备管理
    //添加视频设备
    public Result addVideo(VideoDevice videoDevice){
        String today = format.format(new Date());
        videoDevice.setLogin_date(today);
        videoDeviceMapper.insertVideoDevice(videoDevice);
        logger.info("Insert video device:merchant id is +" + videoDevice.getMerchant_id() + ",detect id is " + videoDevice.getId());
        return ResultUtil.resultGoodReturner(videoDevice);
    }

    public Result deleteVideoDevice(int id){
        videoInfoMapper.deleteVideoInfoByDevice(id);
        videoDeviceMapper.deleteVideoDevice(id);
        return ResultUtil.resultGoodReturner();
    }

    public Result deleteVideoDeviceByMerchant(int merchant_id){
        videoInfoMapper.deleteVideoInfoByMerchant(merchant_id);
        videoDeviceMapper.deleteVideoDeviceByMerchant(merchant_id);
        return ResultUtil.resultGoodReturner();
    }

    public Result updateVideoDevice(VideoDevice videoDevice){
        videoDeviceMapper.updateVideoDevice(videoDevice);
        return ResultUtil.resultGoodReturner(videoDevice);
    }

    //通过id查看视频设备
    public Result getVideoDeviceById(int id){
        return ResultUtil.resultGoodReturner(videoDeviceMapper.findVideoById(id));
    }

    //通过商户id查看视频设备
    public Result getVideoDeviceByMerchant(int merchant_id,int page){
        List<VideoDevice> videoDeviceList = videoDeviceMapper.find(merchant_id, SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        Map<String ,Object> map = new HashMap<>();
        map.put("videoDeviceList",videoDeviceList);
        map.put("total",videoDeviceMapper.count(merchant_id));
        return ResultUtil.resultGoodReturner(map);
    }

    //通过设备名查找视频设备
    public Result getVideoDeviceByDeviceName(int merchant_id,String device_name,int page){
        String search = "%" + device_name + "%";
        List<VideoDevice> videoDeviceList = videoDeviceMapper.findVideoDeviceByDeviceName(search,merchant_id, SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        Map<String ,Object> map = new HashMap<>();
        map.put("videoDeviceList",videoDeviceList);
        map.put("total",videoDeviceMapper.countVideoDeviceByDeviceName(search,merchant_id));
        return ResultUtil.resultGoodReturner(map);
    }

    public Result findVideoDeviceConditions(VideoDeviceConditions videoDeviceConditions) {
        int total = videoDeviceMapper.getTotal_findVideoDeviceConditions(videoDeviceConditions);
        List<VideoDevice> videoDeviceList = videoDeviceMapper.findVideoDeviceConditions(videoDeviceConditions);
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(videoDeviceList,total));
    }
    ///}}}

    ///{{{检测设备管理
    //添加检测设备
    public Result addDetection(DetectionDevice detectionDevice){
        String today = format.format(new Date());
        detectionDevice.setLogin_date(today);
        detectionDeviceMapper.insertDetectionDevice(detectionDevice);
        logger.info("Insert detect device:merchant id is +" + detectionDevice.getMerchant_id() + ",detect id is " + detectionDevice.getId());
        return ResultUtil.resultGoodReturner(detectionDevice);
    }

    public Result deleteDetectionDevice(int id){
        detectInfoMapper.deleteDetectInfoByDevice(id);
        detectionDeviceMapper.deleteDetectionDevice(id);
        return ResultUtil.resultGoodReturner();
    }

    public Result deleteDetectionDeviceByMerchant(int merchant_id){
        detectInfoMapper.deleteDetectInfoByMerchant(merchant_id);
        detectionDeviceMapper.deleteDetectionDeviceByMerchant(merchant_id);
        return ResultUtil.resultGoodReturner();
    }

    public Result updateDetectionDevice(DetectionDevice detectionDevice){
        detectionDeviceMapper.updateDetectionDevice(detectionDevice);
        return ResultUtil.resultGoodReturner(detectionDevice);
    }

    //通过id查看检测设备
    public Result getDetectDeviceById(int id){
        return ResultUtil.resultGoodReturner(detectionDeviceMapper.findDetectionById(id));
    }

    //通过商户id查看检测设备
    public Result getDetectDeviceByMerchant(int merchant_id,int page){
        List<DetectionDevice> detectionDeviceList = detectionDeviceMapper.find(merchant_id, SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        Map<String ,Object> map = new HashMap<>();
        map.put("detectionDeviceList",detectionDeviceList);
        map.put("total",detectionDeviceMapper.count(merchant_id));
        return ResultUtil.resultGoodReturner(map);
    }

    //通过设备名查找检测设备
    public Result getDetectDeviceByDeviceName(int merchant_id,String device_name,int page){
        String search = "%" + device_name + "%";
        List<DetectionDevice> detectionDeviceList = detectionDeviceMapper.findDetectionDeviceByDeviceName(search,merchant_id, SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        Map<String ,Object> map = new HashMap<>();
        map.put("detectionDeviceList",detectionDeviceList);
        map.put("total",detectionDeviceMapper.countDetectionDeviceByDeviceName(search,merchant_id));
        return ResultUtil.resultGoodReturner(map);
    }

    //条件查询
    public Result findDetectionDeviceConditions(DetectionDeviceConditions detectionDeviceConditions){
        int total = detectionDeviceMapper.getTotal_findDetectionDeviceConditions(detectionDeviceConditions);
        List<DetectionDevice> detectionDeviceList = detectionDeviceMapper.findDetectionDeviceConditions(detectionDeviceConditions);
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(detectionDeviceList,total));
    }

    ///}}}

    ///{{{视频信息管理
    //添加视频信息
    public Result addVideoInfo(VideoInfo videoInfo){
        String today = format.format(new Date());
        videoInfo.setCreate_date(today);
        videoInfoMapper.insertVideoInfo(videoInfo);
        logger.info("Insert video info:good id is +" + videoInfo.getGood_id() + ",info id is " + videoInfo.getId());
        return ResultUtil.resultGoodReturner(videoInfo);
    }
    public Result deleteVideoInfo(int id){
        videoInfoMapper.deleteVideoInfo(id);
        return ResultUtil.resultGoodReturner();
    }

    public Result updateVideoInfo(VideoInfo videoInfo){
        videoInfoMapper.updateVideoInfo(videoInfo);
        return ResultUtil.resultGoodReturner(videoInfo);
    }

    //通过设备id查看视频信息
    public Result getVideoInfoByDevice(int device_id,int page){
        List<VideoInfo> videoInfoList = videoInfoMapper.find(device_id, SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        Map<String ,Object> map = new HashMap<>();
        map.put("videoInfoList",videoInfoList);
        map.put("total",videoInfoMapper.count(device_id));
        return ResultUtil.resultGoodReturner(map);
    }

    //通过good_id查看视频信息
    public Result getVideoInfoByGood(int good_id,int page){
        List<VideoInfo> videoInfoList = videoInfoMapper.findVideoInfo(good_id, SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        Map<String ,Object> map = new HashMap<>();
        map.put("videoInfoList",videoInfoList);
        map.put("total",videoInfoMapper.countVideoInfo(good_id));
        return ResultUtil.resultGoodReturner(map);
    }

    //通过merchant_id查看视频信息
    public Result getVideoInfoByMerchant(int merchant_id,int page){
        List<VideoInfo> videoInfoList = videoInfoMapper.findByMernchant(merchant_id, SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        Map<String ,Object> map = new HashMap<>();
        map.put("videoInfoList",videoInfoList);
        map.put("total",videoInfoMapper.countByMernchant(merchant_id));
        return ResultUtil.resultGoodReturner(map);
    }

    public Result findVideoInfoConditions(VideoInfoConditions videoInfoConditions) {
        int total = videoInfoMapper.getTotal_findVideoInfoConditions(videoInfoConditions);
        List<VideoInfo> videoInfoList = videoInfoMapper.findVideoInfoConditions(videoInfoConditions);
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(videoInfoList,total));
    }
    ///}}}

    ///{{{检测信息管理
    //添加检测信息
    public Result addDetectInfo(DetectInfo detectInfo){
        String today = format.format(new Date());
        detectInfo.setCreate_date(today);
        detectInfoMapper.insertDetectInfo(detectInfo);
        logger.info("Insert detect info:good id is " + detectInfo.getGood_id() + ",info id is " + detectInfo.getId());
        return ResultUtil.resultGoodReturner(detectInfo);
    }

    public Result deleteDetectInfo(int id){
        detectInfoMapper.deleteDetectInfo(id);
        return ResultUtil.resultGoodReturner();
    }

    public Result updateDetectInfo(DetectInfo detectInfo){
        detectInfoMapper.updateDetectInfo(detectInfo);
        return ResultUtil.resultGoodReturner(detectInfo);
    }
    //通过设备id查看检测信息
    public Result getDetectInfoByDevice(int device_id,int page){
        List list = detectInfoMapper.find(device_id, SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        Map<String ,Object> map = new HashMap<>();
        map.put("detectInfoList",list);
        map.put("total",detectInfoMapper.count(device_id));
        return ResultUtil.resultGoodReturner(map);
    }

    //通过id查看检测信息
    public Result getDetectInfoById(int id){
        return ResultUtil.resultGoodReturner(detectInfoMapper.findById(id));
    }

    //通过merchant_id查看检测信息
    public Result getDetectInfoByMerchant(int merchant_id,String min_date,String max_date,int page){
        List list = detectInfoMapper.findByMerchant(String.valueOf(merchant_id),min_date,max_date,String .valueOf(SqlUtil.PAGE_LIMIT),String.valueOf(SqlUtil.getOffset(page)));
        Map<String ,Object> map = new HashMap<>();
        map.put("detectInfoList",list);
        map.put("total",detectInfoMapper.countByMerchant(String.valueOf(merchant_id),min_date,max_date));
        return ResultUtil.resultGoodReturner(map);
    }

    //通过good_id查看检测信息
    public Result getDetectInfoByGood(int good_id,int page){
        List list = detectInfoMapper.findDetectInfo(good_id, SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        Map<String ,Object> map = new HashMap<>();
        map.put("detectInfoList",list);
        map.put("total",detectInfoMapper.countDetectInfo(good_id));
        return ResultUtil.resultGoodReturner(map);
    }

    //条件查询
    public Result findDetectInfoConditions(DetectInfoConditions detectInfoConditions) {
        int total = detectInfoMapper.getTotal_findDetectInfoConditions(detectInfoConditions);
        List list = detectInfoMapper.findDetectInfoConditions(detectInfoConditions);
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(list,total));
    }
    ///}}}

    ///{{{促销信息管理
    //上传促销图片
    // upload file
    public Result uploadPictureForPromotion(MultipartFile file){
        return PictureUtil.uploadSinglePicture(file, promotion_sub_dir);
    }

    //获取指定促销的照片
    public void getPictureForPromotion(String filename, HttpServletResponse response){
        PictureUtil.download(promotion_sub_dir,filename, response);
    }

    //添加促销
    public Result addPromotion(Promotion promotion){
        String today = format.format(new Date());
        promotion.setCreate_date(today);
        promotionMapper.insertPromotion(promotion);
        logger.info("Insert promotion info:good type id is +" + promotion.getType_id() + ",info id is " + promotion.getId());
        return ResultUtil.resultGoodReturner(promotion);
    }

    //删除促销信息
    public Result deletePromotionByGoodType(int type_id){
        promotionMapper.deletePromotionByGoodType(type_id);
        return ResultUtil.resultGoodReturner();
    }

    //删除促销信息
    public Result deletePromotionInfo(int id){
        promotionMapper.deletePromotion(id);
        return ResultUtil.resultGoodReturner();
    }

    //修改促销信息
    public Result updatePromotionInfo(Promotion promotion){
        promotionMapper.updatePromotion(promotion);
        return ResultUtil.resultGoodReturner(promotion);
    }

    //通过good_id查看促销
    public Result getPromotionByGoodType(int type_id,int page){
        List promotionList = promotionMapper.findPage(type_id, SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(promotionList,promotionMapper.count(type_id)));
    }

    //通过merchant_id查看促销
    public Result getPromotionByMerchant(int merchant_id,int page){
        List promotionList = promotionMapper.findByMerchant(merchant_id, SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(promotionList,promotionMapper.countByMerchant(merchant_id)));
    }

    public Result findAllPromotion(int page){
        List promotionList = promotionMapper.findAllPromotion(SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(promotionList,promotionMapper.countAllPromotion()));
    }

    ///{{{商户商品操作日志管理
    public Result uploadOperationPicture(MultipartFile file){
        return PictureUtil.uploadSinglePicture(file,operation_picture_sub_dir);
    }

    public void downloadOperationPicture(String fileName,HttpServletResponse response){
        PictureUtil.download(operation_picture_sub_dir,fileName,response);
    }

    public Result uploadOperationFile(MultipartFile file) {
        return FileUtil.uploadSingleFile(file,operation_file_sub_dir);
    }

    public Object downloadOperationFile(String fileName) {
        return FileUtil.download(operation_file_sub_dir,fileName);
    }

    public Result addOperationLog(OperationLog operationLog){
        String today = format.format(new Date());
        operationLog.setOperation_date(today);
        operationLogMapper.insertOperationLog(operationLog);
        return ResultUtil.resultGoodReturner(operationLog);
    }

    public Result deleteOperationLog(int id){
        operationLogMapper.deleteOperationLog(id);
        return ResultUtil.resultGoodReturner();
    }

    public Result updateOperationLog(OperationLog operationLog){
        String today = format.format(new Date());
        operationLog.setOperation_date(today);
        operationLogMapper.updateOperationLog(operationLog);
        return ResultUtil.resultGoodReturner(operationLog);
    }

    public Result findOperationLogByGood(int good_id,int page){
        int total = operationLogMapper.getTotal_findByGood(good_id);
        List<OperationLog> operationLogs = operationLogMapper.findByGoodWithPage(good_id,
                SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(operationLogs,total));
    }

    public Result findOperationLogByMerchant(int merchant_id,int page){
        int total = operationLogMapper.getTotal_findByMerchant(merchant_id);
        List<OperationLog> operationLogs = operationLogMapper.findByMerchantWithPage(merchant_id,
                SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(operationLogs,total));
    }

    public Result findAllOperationLog(int page){
        int total = operationLogMapper.getTotal_findAllOperationLog();
        List<OperationLog> operationLogs =operationLogMapper.findAllOperationLogWithPage(
                SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page)
        );
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(operationLogs,total));
    }
    ///}}}

    ///{{{检测信息样本管理
    public Result addDetectSample(DetectSample detectSample){
        detectSampleMapper.insertDetectSample(detectSample);
        return ResultUtil.resultGoodReturner(detectSample);
    }

    public Result deleteDetectSample(int id){
        detectInfoMapper.deleteDetectInfoByDetectSample(id);
        detectSampleMapper.deleteDetectSample(id);
        return ResultUtil.resultGoodReturner();
    }

    public Result updateDetectSample(DetectSample detectSample){
        detectSampleMapper.updateDetectSample(detectSample);
        return ResultUtil.resultGoodReturner(detectSample);
    }

    public Result findAllDetectSample(int page){
        int total = detectSampleMapper.getTotal_findAllDetectSample();
        List<DetectSample> detectSamples = detectSampleMapper.findAllDetectSampleWithPage(
                SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page)
        );
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(detectSamples,total));
    }

    public Result findDetectSamplesByMerchant(int merchant_id,int page){
        int total = detectSampleMapper.getTotal_findDetectSamplesByMerchant(merchant_id);
        List<DetectSample> detectSamples = detectSampleMapper.findDetectSamplesByMerchantWithPage(merchant_id,
                SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(detectSamples,total));
    }
    ///}}}

    //查看所有视频设备
    public Result findAllVideo( ){
        return ResultUtil.resultGoodReturner((videoDeviceMapper.findAll1()));
    }

    //查看所有检测设备
    public Result findAllDetection( ){
        return ResultUtil.resultGoodReturner((detectionDeviceMapper.findAll1()));
    }

    public Result findDetectionDevice_1s(String device_code,int merchant_id) {
        return ResultUtil.resultGoodReturner(detectionDeviceMapper.findDetectionDevice_1s(device_code,merchant_id));
    }
}
