package com.scut.originsystem.util;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class PathUtil {
    /**
     * 获取运行java -jar 命令所在目录下的指定目录
     */
    public static File getSaveDir(String sub_dir){
        if (sub_dir==null || sub_dir=="") {
            sub_dir = "/static/images/upload/";
        }
        //获得上传文件路径
        File path;
        try{
            //
            File root = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!root.exists()) root = new File("");
            path = new File(root.getAbsolutePath(),sub_dir);
            if (!path.exists()) path.mkdirs();
            return  path;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
