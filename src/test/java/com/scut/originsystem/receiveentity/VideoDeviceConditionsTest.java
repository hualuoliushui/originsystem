package com.scut.originsystem.receiveentity;

import com.alibaba.fastjson.JSON;
import com.scut.originsystem.tool.AutoInit;
import org.junit.Test;

import static org.junit.Assert.*;

public class VideoDeviceConditionsTest {
    @Test
    public void test(){
        String str = JSON.toJSONString(AutoInit.init(VideoDeviceConditions.class));
        System.out.println(str);
    }
}