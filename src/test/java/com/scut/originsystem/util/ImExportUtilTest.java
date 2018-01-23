package com.scut.originsystem.util;

import com.scut.originsystem.entity.Merchant;
import com.scut.originsystem.entity.Result;
import com.scut.originsystem.entity.User;
import com.scut.originsystem.entity.WaitForCheckingMerchant;
import com.scut.originsystem.mapper.*;
import com.scut.originsystem.service.AdminService;
import com.scut.originsystem.service.MerchantService;
import com.scut.originsystem.service.TokenSessionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImExportUtilTest {
    @Autowired
    UserMapper userMapper;
    @Autowired
    MerchantMapper merchantMapper;
    @Autowired
    CompanyMapper companyMapper;
    @Autowired
    SystemMapper systemMapper;
    @Autowired
    WaitForCheckingMerchantMapper waitForCheckingMerchantMapper;
    @Autowired
    SystemLogMapper systemLogMapper;
    @Autowired
    QRCodeForCheckMapper qrCodeForCheckMapper;
    @Autowired
    GoodMapper goodMapper;
    @Autowired
    GoodManagerMapper goodManagerMapper;
    @Autowired
    TokenSessionService tokenSessionService;
    @Autowired
    OperationLogMapper operationLogMapper;

    @Test
    public void export_import_excel_user() {
        Result result = ImExportUtil.export_excel(
                userMapper.findAllUser(),User.class,null);
        Assert.assertEquals(0,result.getErrCode());
        System.out.println(result.getMsg());
        System.out.println(result.getData());

        String fileName = (String)result.getData();
        result = ImExportUtil.import_excel(fileName,User.class);
        Assert.assertEquals(0,result.getErrCode());
        System.out.println(result.getMsg());
//        System.out.println(result.getData());
    }
}