package com.scut.originsystem.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * Created by zsb on 2017/3/16.
 */
public class HttpUtil {
    public static void setResponse(HttpServletResponse response, int httpStatus, JSONObject body) {
        try {
            response.setStatus(httpStatus);
            response.setContentType("application/json;charset=UTF-8");
            if (body != null && !body.isEmpty()) {
                OutputStream os = response.getOutputStream();
                os.write(body.toJSONString().getBytes("utf-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
