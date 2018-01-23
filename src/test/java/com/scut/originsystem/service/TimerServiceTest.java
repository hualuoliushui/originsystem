package com.scut.originsystem.service;

import com.scut.originsystem.entity.DetectionDevice;
import com.scut.originsystem.entity.VideoDevice;
import com.scut.originsystem.mapper.DetectionDeviceMapper;
import com.scut.originsystem.mapper.VideoDeviceMapper;
import com.scut.originsystem.tool.AutoInit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimerServiceTest {
    @Autowired
    VideoDeviceMapper videoDeviceMapper;
    @Autowired
    DetectionDeviceMapper detectionDeviceMapper;

    @Test
    public void test(){
//        addVideo(20,TimerService.getVideoDeviceInfos());
//        addDetection(20,TimerService.getDetectionDeviceInfos());
    }

    public void addVideo(int start,Map<String,Map<String,String>> map) {
        int num = map.size();
        List<String> list = new LinkedList<>();
        for(String s : map.keySet()){
            list.add(s);
        }
        for (int i = start; i < num+start; i++) {
            VideoDevice videoDevice = (VideoDevice) AutoInit.init(VideoDevice.class,i);
            String key = list.get(i-start);
            Map<String,String> sub_map = map.get(key);
            videoDevice.setMerchant_id(43);
            videoDevice.setI_id(Integer.valueOf(key));
            videoDevice.setDevice_code(sub_map.get("c_index_code"));
            videoDevice.setDevice_name(sub_map.get("c_org_name"));
            videoDevice.setLogin_date(sub_map.get("c_create_time"));
            System.out.println(videoDevice);
            videoDeviceMapper.insertVideoDevice(videoDevice);
        }
    }

    public void addDetection(int start,Map<String,Map<String,String>> map) {
        int num = map.size();
        List<String> list = new LinkedList<>();
        for(String s : map.keySet()){
            list.add(s);
        }
        for (int i = start; i < num+start; i++) {
            DetectionDevice detectionDevice = (DetectionDevice) AutoInit.init(DetectionDevice.class,i);
            String key = list.get(i-start);
            Map<String,String> sub_map = map.get(key);
            detectionDevice.setMerchant_id(43);
            detectionDevice.setI_id(Integer.valueOf(key));
            detectionDevice.setDevice_code(sub_map.get("c_index_code"));
            detectionDevice.setDevice_name(sub_map.get("c_name"));
            detectionDevice.setLogin_date(sub_map.get("c_create_time"));
            System.out.println(detectionDevice);
            detectionDeviceMapper.insertDetectionDevice(detectionDevice);
        }
    }
}