package com.scut.originsystem.receiveentity;

import com.alibaba.fastjson.JSON;
import com.scut.originsystem.tool.AutoInit;
import org.junit.Test;

import static org.junit.Assert.*;

public class VideoInfoConditionsTest {
    @Test
    public void test(){
        String str = JSON.toJSONString(AutoInit.init(VideoInfoConditions.class));
        System.out.println(str);
    }
}