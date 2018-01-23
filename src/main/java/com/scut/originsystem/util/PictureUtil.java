package com.scut.originsystem.util;

import com.scut.originsystem.entity.Result;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PictureUtil {
    /**
     * 可用图片扩展名
     */
    private static String pic_extensions[]={
            "jpg",
            "png",
//            "gif",
            "bmp",
            "jpeg"
    };

    /**
     * 测试文件扩展名是否符合图片文件
     * @param ext 扩展名
     * @return true（符合)
     */
    public static boolean test_exten(String ext){
        ext=ext.toLowerCase();
        for(String pic_extension : pic_extensions){
            if(ext.equals(pic_extension)){
                return true;
            }
        }
        return false;
    }



    public static byte[] getPicture(String filePath){
        byte data[] = null;
        File file = new File(filePath);
        if(!file.exists() && file.canRead()) {
            return null;
        }

        try {
            FileInputStream inputStream = new FileInputStream(file);
            data = new byte[(int)file.length()];
            int length = inputStream.read(data);
            inputStream.close();
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setResponseWithPicture(HttpServletResponse response,byte[] data){
        try{
            if (data!=null){
                response.setContentType("image/png");
                try {
                    OutputStream stream = response.getOutputStream();
                    stream.write(data);
                    stream.flush();
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 当前只保存在运行时类目录中，没有固定文件夹0
     */
    public static List<String> uploadPicture(MultipartFile[] files,String sub_dir){
        List<String> picturePathList = new ArrayList<String>();
        File path = PathUtil.getSaveDir(sub_dir);
        if (path==null)
            return null;
        if( files != null && files.length > 0){
            for(int i =0;i<files.length;i++){
                MultipartFile multipartFile = files[i];
                if (!multipartFile.isEmpty()) {
                    String fileName = multipartFile.getOriginalFilename();
                    String postfix = fileName.substring(fileName.lastIndexOf(".")+1);

                    if(!test_exten(postfix))
                        break;

                    //利用UUID获得唯一文件名
                    String UUIDFilename = UuidUtil.getUUID() + "." + postfix;
                    File filepath = new File(path, UUIDFilename);

                    //上传图片
                    File file = new File(path + File.separator + UUIDFilename);

                    try {
                        multipartFile.transferTo(file);
                        picturePathList.add(UUIDFilename);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return picturePathList;
    }

    public static Result uploadSinglePicture(MultipartFile uploadFile, String sub_dir){
        File path = PathUtil.getSaveDir(sub_dir);
        if (path==null)
            throw new RuntimeException("服务器错误");

        String fileName = uploadFile.getOriginalFilename();
        String postfix = fileName.substring(fileName.lastIndexOf(".")+1);

        if(!test_exten(postfix))
            throw new RuntimeException("文件格式不正确");

        String UUIDFilename = UuidUtil.getUUID() + "." + postfix;

        File upload = new File(path + File.separator + UUIDFilename);
        try {
            uploadFile.transferTo(upload);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("保存失败");
        } catch (Throwable throwable){
            throwable.printStackTrace();
            throw new RuntimeException(throwable.getMessage());
        }
        return ResultUtil.resultGoodReturner(UUIDFilename);
    }

    public static void testAPI(){

    }

    public static void download(String sub_dir,String fileName,HttpServletResponse response){
        File path = PathUtil.getSaveDir(sub_dir);
        String s = path + File.separator + fileName;
        byte[] data = PictureUtil.getPicture(s);
        PictureUtil.setResponseWithPicture(response,data);
    }
}
