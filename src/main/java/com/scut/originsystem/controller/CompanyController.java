package com.scut.originsystem.controller;

import com.scut.originsystem.entity.Company;
import com.scut.originsystem.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    // upload picture
    @RequestMapping(value = "/uploadPicture",method = RequestMethod.POST)
    @ResponseBody
    public Object uploadPicture(@RequestParam(value = "file",required = true)MultipartFile file){return companyService.uploadPicture(file);}

    @RequestMapping(value= "/getPicture",method = RequestMethod.GET)
    @ResponseBody
    public void getPicture(@RequestParam(value="fileName",required = true)String fileName, HttpServletResponse response){
        companyService.getPicture(fileName, response);
    }

    // new company
    @RequestMapping(value = "/insertCompany",method = RequestMethod.POST)
    @ResponseBody
    public Object insertCompany(@Validated @RequestBody Company company){return companyService.insertCompany(company);}
}
