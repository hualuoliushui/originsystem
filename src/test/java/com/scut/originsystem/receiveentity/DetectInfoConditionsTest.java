package com.scut.originsystem.receiveentity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.scut.originsystem.annotation.SqlConditionAnno;
import com.scut.originsystem.enums.SqlConditionType;
import com.scut.originsystem.tool.AutoInit;
import org.junit.Test;

import java.lang.reflect.Field;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class DetectInfoConditionsTest {
    @Test
    public void test(){
        DetectInfoConditions detectInfoConditions = (DetectInfoConditions) AutoInit.init(DetectInfoConditions.class);
        String str = JSON.toJSONString(detectInfoConditions);
        System.out.println(str);
    }
}