package com.scut.originsystem.util;

import com.scut.originsystem.entity.Result;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class FileUtil {
    public static boolean deleteDir(File dir){
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    public static boolean deleteFile(String sub_dir,String fileName){
        try{
            File path = PathUtil.getSaveDir(sub_dir);
            File file = new File(path + File.separator + fileName);
            if(file.isFile()){
                return file.delete();
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static byte[] getFileToByte(File file) {
        byte[] by = new byte[(int) file.length()];
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            byte[] bb = new byte[2048];
            int ch;
            ch = is.read(bb);
            while (ch != -1) {
                bytestream.write(bb, 0, ch);
                ch = is.read(bb);
            }
            by = bytestream.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return by;
    }

    public static ResponseEntity download(String sub_dir,String fileName,String downloadFileName){
        File path = PathUtil.getSaveDir(sub_dir);
        File file=new File(path+File.separator + fileName);
        HttpHeaders headers = new HttpHeaders();

        String attFileName = null;

        //为了解决中文名称乱码问题
        try {
            if (downloadFileName != null) {
                attFileName = new String(downloadFileName.getBytes("UTF-8"),"iso-8859-1");
            }else{
                attFileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        headers.setContentDispositionFormData("attachment", attFileName);
        return new ResponseEntity<byte[]>(FileUtil.getFileToByte(file), headers, HttpStatus.OK);
    }

    public static ResponseEntity download(String sub_dir,String fileName){
        return download(sub_dir,fileName,null);
    }

    /**
     * 上传单个文件，返回存储在服务器中的文件的名称
     * @param uploadFile
     * @param sub_dir
     * @param allow_file_exts
     * @return
     */
    public static Result uploadSingleFile(MultipartFile uploadFile, String sub_dir, Set<String> allow_file_exts){
        File path = PathUtil.getSaveDir(sub_dir);
        if (path==null)
            return ResultUtil.resultBadReturner("服务器错误");

        String fileName = uploadFile.getOriginalFilename();
        String postfix = fileName.substring(fileName.lastIndexOf(".")+1);

        String UUIDFilename = UuidUtil.getUUID() + "." + postfix;
        if(allow_file_exts != null && !allow_file_exts.contains(postfix)){
            StringBuilder allowFileExts = new StringBuilder();
            for (String allow_file_ext : allow_file_exts) {
                allowFileExts.append(allow_file_ext + ",");
            }
            return ResultUtil.resultBadReturner("文件格式不支持,支持的文件格式："+allowFileExts.toString());
        }

        File upload = new File(path + File.separator + UUIDFilename);
        try {
            uploadFile.transferTo(upload);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.resultBadReturner("保存失败");
        }
        return ResultUtil.resultGoodReturner(UUIDFilename);
    }

    public static Result uploadSingleFileForExcel(MultipartFile uploadFile, String sub_dir){
        Set<String> allow_file_exts = new HashSet<>();
        allow_file_exts.add("xls");
        allow_file_exts.add("xlsx");
        return uploadSingleFile(uploadFile,sub_dir,allow_file_exts);
    }

    public static Result uploadSingleFile(MultipartFile uploadFile, String sub_dir){
        return uploadSingleFile(uploadFile,sub_dir,null);
    }
}

