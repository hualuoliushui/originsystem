package com.scut.originsystem.service;

import com.scut.originsystem.entity.DetectionDevice;
import com.scut.originsystem.entity.Merchant;
import com.scut.originsystem.entity.User;
import com.scut.originsystem.entity.VideoDevice;
import com.scut.originsystem.enums.DeviceState;
import com.scut.originsystem.mapper.DetectionDeviceMapper;
import com.scut.originsystem.mapper.MerchantMapper;
import com.scut.originsystem.mapper.UserMapper;
import com.scut.originsystem.mapper.VideoDeviceMapper;
import com.scut.originsystem.util.DateUtil;
import org.apache.axis.client.Call;
import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TimerService {
    @Autowired
    UserMapper  userMapper;
    @Autowired
    MerchantMapper merchantMapper;
    @Autowired
    DetectionDeviceMapper detectionDeviceMapper;
    @Autowired
    VideoDeviceMapper videoDeviceMapper;

    private static Logger logger = LoggerFactory.getLogger(MerchantService.class);
    protected static Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final int INVAILD_DAY = 30;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 12)//半天一次
    public void cancelMerchant(){
        System.out.println(format.format(new Date()) + ":Cancel Timer wakeup");

        List<Merchant> merchantList = merchantMapper.findAcitvativedMerchant("");

        for (Merchant merchant : merchantList){
            if (canBeCanceled(merchant.getId())){
                userMapper.setActivationCode(0,merchant.getUser_id());
                logger.warn("Merchant has been canceled,id is " + merchant.getId());
            }
        }

        System.out.println(format.format(new Date()) + ":Cancel Timer sleep");
    }

    @Scheduled(fixedRate = 1000 * 60 * 10)//十分钟一次
    public void getOnlineState(){
        System.out.println(format.format(new Date()) + ":Online Timer wakeup");
        String today = format.format(new Date());
        List<VideoDevice> videoDeviceList = videoDeviceMapper.findAll1();
        List<DetectionDevice> detectionDeviceList = detectionDeviceMapper.findAll1();

        for (VideoDevice videoDevice : videoDeviceList){
            //如果在线，并且上次不在线
            if (videoOnline(videoDevice) && videoDevice.getOnline() == DeviceState.OFFLINE){
                videoDeviceMapper.setOnline(DeviceState.ONLINE,videoDevice.getId());
                videoDeviceMapper.setDropDate(today,videoDevice.getId());
                logger.warn("Video device " + videoDevice.getId() + " is return online now;");
            }
            //如果不在线，并且上次在线
            if (!videoOnline(videoDevice) && videoDevice.getOnline()==DeviceState.ONLINE){
                videoDeviceMapper.setOnline(DeviceState.OFFLINE,videoDevice.getId());
                videoDeviceMapper.setDropDate(format.format(new Date()),videoDevice.getId());
                Merchant merchant = merchantMapper.findMerchantById(videoDevice.getMerchant_id());
                User user = userMapper.findUserById(merchant.getUser_id());
                logger.warn("Video device " + videoDevice.getId() +" is offline,merchant name is " + merchant.getMerchant_name() + ",phone is " + user.getPhone());
            }
        }

        for (DetectionDevice detectionDevice : detectionDeviceList){
            //如果在线，并且上次不在线
            if (detectionOnline(detectionDevice) && detectionDevice.getOnline() == DeviceState.OFFLINE){
                detectionDeviceMapper.setOnline(DeviceState.ONLINE,detectionDevice.getId());
                detectionDeviceMapper.setDropDate(today,detectionDevice.getId());
                logger.warn("Detection device " + detectionDevice.getId() + " is return online now;");
            }
            //如果不在线，并且上次在线
            if (!detectionOnline(detectionDevice) && detectionDevice.getOnline()==DeviceState.ONLINE){
                detectionDeviceMapper.setOnline(DeviceState.OFFLINE,detectionDevice.getId());
                detectionDeviceMapper.setDropDate(format.format(new Date()),detectionDevice.getId());
                Merchant merchant = merchantMapper.findMerchantById(detectionDevice.getMerchant_id());
                User user = userMapper.findUserById(merchant.getUser_id());
                logger.warn("Detection device " + detectionDevice.getId() +" is offline,merchant name is " + merchant.getMerchant_name() + ",phone is " + user.getPhone());
            }
        }

        System.out.println(format.format(new Date()) + ":Online Timer sleep");
    }

    private boolean videoOnline(VideoDevice videoDevice){
        if(videoDevice==null || videoDevice.getI_id()==0)
            return false;
        try {
            validDateUpdate();
            Map<String,String> map = getVideoDeviceInfos().get(String.valueOf(videoDevice.getI_id()));
            return Integer.valueOf(map.get("i_is_online"))==1;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean detectionOnline(DetectionDevice detectionDevice){
        if(detectionDevice==null || detectionDevice.getI_id()==0)
            return false;
        try {
            validDateUpdate();
            Map<String,String> map = getDetectionDeviceInfos().get(String.valueOf(detectionDevice.getI_id()));
            return Integer.valueOf(map.get("i_is_online"))==1;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean canBeCanceled(int merchant_id){
        List<DetectionDevice> detectionDeviceList = detectionDeviceMapper.findAllDrop1(merchant_id);
        List<VideoDevice> videoDeviceList = videoDeviceMapper.findAllDrop1(merchant_id);
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        boolean detectionState = false;
        boolean videoState = false;

        for (DetectionDevice detectionDevice : detectionDeviceList){
            Calendar dropDate = DateUtil.stringToCalendar(detectionDevice.getDrop_date());
            dropDate.set(Calendar.DAY_OF_YEAR, dropDate.get(Calendar.DAY_OF_YEAR) + INVAILD_DAY);
            if (today.get(Calendar.YEAR)==dropDate.get(Calendar.YEAR)
                    && today.get(Calendar.MONTH)==dropDate.get(Calendar.MONTH)
                    && today.get(Calendar.DATE)==dropDate.get(Calendar.DATE) ){
                detectionState = true;
                break;
            }
        }

        for (VideoDevice videoDevice : videoDeviceList){
            Calendar dropDate = DateUtil.stringToCalendar(videoDevice.getDrop_date());
            dropDate.set(Calendar.DAY_OF_YEAR, dropDate.get(Calendar.DAY_OF_YEAR) + INVAILD_DAY);
            if (today.get(Calendar.YEAR)==dropDate.get(Calendar.YEAR)
                    && today.get(Calendar.MONTH)==dropDate.get(Calendar.MONTH)
                    && today.get(Calendar.DATE)==dropDate.get(Calendar.DATE) ){
                videoState = true;
                break;
            }
        }

//        return detectionState==false && videoState==false?false:true;
        return false;
    }

    @Value("${updateTimeInterval}")
    private long updateTimeInterval;

    private void validDateUpdate(){
        if(videoDeviceInfos==null || detectionDeviceInfos==null){
            initInfos();
            return;
        }
        Date now = new Date();
        if(now.getTime()-videoDeviceInfosUpdateDate.getTime()>updateTimeInterval ||
                now.getTime()-detectionDeviceInfosUpdateDate.getTime()>updateTimeInterval){
            initInfos();
            return;
        }
    }

    private static Map<String,Map<String,String>> videoDeviceInfos = new HashMap<>();
    private static Date videoDeviceInfosUpdateDate = new Date();
    private static Map<String,Map<String,String>> detectionDeviceInfos = new HashMap<>();
    private static Date detectionDeviceInfosUpdateDate = new Date();

    public static Map<String, Map<String, String>> getVideoDeviceInfos() {
        return videoDeviceInfos;
    }

    public static Map<String, Map<String, String>> getDetectionDeviceInfos() {
        return detectionDeviceInfos;
    }

    private static void initInfos(){
        Document document = null;

        try {
            document = getDocument();
            videoDeviceInfos = parse(document);
            videoDeviceInfosUpdateDate = new Date();

            document = getDocument();
            detectionDeviceInfos = parse(document);
            detectionDeviceInfosUpdateDate = new Date();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        initInfos();
    }

    /**
     * 调用webservice服务，获取信息
     * @return
     */
    public static Document getDocument(){
        String endPoint = "http://222.133.38.206:88/services/ICommonService.ICommonServiceHttpSoap11Endpoint";
        String namespace = "http://ws.cms.ivms6.hikvision.com";
        String operationName = "getAllResourceDetailResponse";
        String nodeIndexCode = "001040";
        String resType = "30000";
        org.apache.axis.client.Service service = new org.apache.axis.client.Service();
        Call call;
        try {
            call = (Call)service.createCall();
            call.setTargetEndpointAddress(new URL(endPoint));
            call.setOperationName(new QName(namespace,operationName));
            call.addParameter("nodeIndexCode",
                    org.apache.axis.encoding.XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);
            call.addParameter("resType",
                    org.apache.axis.encoding.XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
            String result = (String)call.invoke(new Object[]{nodeIndexCode,resType});

            return DocumentHelper.parseText(result);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析document文档
     * @param document
     * @return
     */
    public static Map parse(Document document){
        Map<String,Map<String,String>> map = new HashMap<>();
        try {
            Element root = document.getRootElement();
            Element rows = root.element("rows");
            for (Element element : rows.elements()) {
                Map<String,String> sub_map = new HashMap<>();
                for (Attribute attribute : element.attributes()) {
                    sub_map.put(attribute.getQualifiedName(),attribute.getValue());
                }
                map.put(sub_map.get("i_id"),sub_map);
            }
            document.getRootElement().elements().get(1).elements().get(0).attribute("i_is_online").getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
