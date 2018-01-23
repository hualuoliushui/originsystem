package com.scut.originsystem.controller;

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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InformationControllerTest {
    @Autowired
    VideoDeviceMapper videoDeviceMapper;
    @Autowired
    DetectionDeviceMapper detectionDeviceMapper;
}