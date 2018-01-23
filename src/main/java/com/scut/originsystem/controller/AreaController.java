package com.scut.originsystem.controller;

import com.scut.originsystem.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/area")
public class AreaController {
    @Autowired
    AreaService areaService;

    @RequestMapping(value = "/getProvinces",method = RequestMethod.GET)
    public Object getProvinces(){
        return areaService.getProvinces();
    }

    @RequestMapping(value = "/getNext",method = RequestMethod.GET)
    public Object getNext(@RequestParam(value = "type")int type,
                          @RequestParam(value = "belong_to_code")String belong_to_code){
        return areaService.getNext(type,belong_to_code);
    }

    @RequestMapping(value = "/getAreas",method = RequestMethod.GET)
    public Object getAreas(@RequestParam(value = "type")int type,
                           @RequestParam(value = "belong_to_code")String belong_to_code,
                           HttpServletRequest request){
        return areaService.getAreas(type,belong_to_code,request);
    }
}
