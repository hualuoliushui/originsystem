package com.scut.originsystem.service;

import com.scut.originsystem.entity.Area;
import com.scut.originsystem.entity.Result;
import com.scut.originsystem.entity.User;
import com.scut.originsystem.enums.AreaType;
import com.scut.originsystem.enums.RoleType;
import com.scut.originsystem.mapper.AreaMapper;
import com.scut.originsystem.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AreaService {
    @Autowired
    AreaMapper areaMapper;
    @Autowired
    TokenSessionService tokenSessionService;

    private String getArea_code(HttpServletRequest request) {
        String area_code = "";
        try {
            User user = tokenSessionService.getUser(request);
            if (user.getRole().equals(RoleType.OPERATOR.getRole())) {
                area_code = user.getArea_code();
            }
        } catch (NullPointerException e) {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return area_code;
        }
    }

    public Result getProvinces(){
        List<Area> areas = areaMapper.getAreaByType(AreaType.PROVINCE.getType());
        return ResultUtil.resultGoodReturner(areas);
    }

    public Result getNext(int type,String belong_to_code){
        List<Area> areas = areaMapper.getAreasByTypeBelongToCode(type,belong_to_code);
        return ResultUtil.resultGoodReturner(areas);
    }

    public Result getAreas(int type, String belong_to_code, HttpServletRequest request){
        String user_area_code = getArea_code(request);
        List<Area> areas = areaMapper.getAreasByTypeBelongToCode_merchant(user_area_code,type,belong_to_code);
        return ResultUtil.resultGoodReturner(areas);
    }
}
