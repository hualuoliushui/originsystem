package com.scut.originsystem.service;

import com.scut.originsystem.entity.Company;
import com.scut.originsystem.entity.Result;
import com.scut.originsystem.mapper.CompanyMapper;
import com.scut.originsystem.mapper.MerchantMapper;
import com.scut.originsystem.util.PathUtil;
import com.scut.originsystem.util.PictureUtil;
import com.scut.originsystem.util.ResultUtil;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class CompanyService {
    @Autowired
    MerchantMapper merchantMapper;
    @Autowired
    CompanyMapper companyMapper;

    private static Logger logger = LoggerFactory.getLogger(CompanyService.class);
    // 保存图片的相对路径
    private static String sub_dir = "/static/images/upload/";

    // upload file
    public Result uploadPicture(MultipartFile file){
        return PictureUtil.uploadSinglePicture(file,sub_dir);
    }

    public void getPicture(String filename, HttpServletResponse response){
        try{
            File path = PathUtil.getSaveDir(sub_dir);
            String s = path + File.separator + filename;
            byte[] data = PictureUtil.getPicture(s);
            PictureUtil.setResponseWithPicture(response,data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // new company
    public Result insertCompany(Company company){
        companyMapper.insertCompany(company);
        logger.info("insert company by merchant, id is " + company.getMerchant_id()+", company id is "+company.getId());
        return ResultUtil.resultGoodReturner(company);
    }
}
