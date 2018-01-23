package com.scut.originsystem.receiveentity;

import com.alibaba.fastjson.JSON;
import com.scut.originsystem.tool.AutoInit;
import org.junit.Test;

public class DetectionDeviceConditionsTest {
    @Test
    public void test(){
        String str = JSON.toJSONString(AutoInit.init(DetectionDeviceConditions.class));
        System.out.println(str);
    }
}