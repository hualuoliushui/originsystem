package com.scut.originsystem.util;

import com.scut.originsystem.receiveentity.DetectInfoConditions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SqlUtilTest {

    @Test
    public void conditionsResolver() {
        DetectInfoConditions conditions = new DetectInfoConditions();
        SqlUtil.ConditionsResolve(conditions);
    }


}