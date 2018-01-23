package com.scut.originsystem.util;

import com.scut.originsystem.annotation.ImportField;
import com.scut.originsystem.entity.Result;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ImExportUtil {
    public static final String  sub_dir = "/static/excels";
    public static final String file_ext = ".xls";

    /**
     * 导出数据到指定的excel文件
     * @param list
     * @param clazz
     * @param fileName
     * @return
     */
    public static Result export_excel(List list, Class clazz, String fileName){
        Field[] fields1 = clazz.getDeclaredFields();
        LinkedList<Field> fields = new LinkedList<>();
        for (Field field : fields1) {
            if (field.getType().isPrimitive() ||
                    field.getType().getName().equals("java.lang.String")){
                fields.add(field);
            }
        }
        if(!(clazz.getSuperclass().isAssignableFrom(Object.class))){
            Field[] fields2 = clazz.getSuperclass().getDeclaredFields();
            for (Field field : fields2) {
                if (field.getType().isPrimitive() ||
                        field.getType().getName().equals("java.lang.String")){
                    fields.add(field);
                }
            }
        }

        if(fileName==null){
            fileName = clazz.getSimpleName();
        }
        fileName += "_" + UuidUtil.getUUID() + file_ext;

        File file;
        WritableWorkbook writableWorkbook = null;
        try {
            file = new File(PathUtil.getSaveDir(sub_dir)+File.separator + fileName);
            writableWorkbook = Workbook.createWorkbook(file);
            WritableSheet writableSheet = writableWorkbook.createSheet(clazz.getSimpleName(),0);
            for (int i = 0; i < fields.size(); i++) {
                writableSheet.addCell(new Label(i,0, fields.get(i).getName()));
            }

            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < fields.size(); j++) {
//                    java.beans.PropertyDescriptor pd = new java.beans.PropertyDescriptor(fields1.get(j).getName(),clazz);
//                    Method getMethod = pd.getReadMethod();
//                    Object o = getMethod.invoke(list.get(i));
                    Object o = ReflectUtil.getFieldValue(list.get(i),fields.get(j));
                    if (o == null) {
                        o = new String("");
                    }
                    writableSheet.addCell(new Label(j,i+1, String.valueOf(o)));
                }
            }

            writableWorkbook.write();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.resultBadReturner(e.getMessage());
        } finally {
            if (writableWorkbook != null) {
                try {
                    writableWorkbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ResultUtil.resultGoodReturner(fileName);
    }

    /**
     * 下载excel文件（包含导出的数据）
     * @param sub_dir 查找文件的相对路径
     * @param downloadFileName 下载时，设置的文件名，可设为空
     * @param fileName 导出的文件名，可设为空
     * @param list 数据列表
     * @param clazz 数据列表中的数据对象
     * @return
     */
    public static Object download(String sub_dir,String downloadFileName,String fileName,List list,Class clazz){
        Result result = ImExportUtil.export_excel(list,clazz,fileName);
        if(result.getErrCode()!=0) return result;
        try {
            return FileUtil.download(sub_dir, (String) result.getData(), downloadFileName);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.resultBadReturner(e.getMessage());
        }finally {
            boolean ret = FileUtil.deleteFile(sub_dir,(String)result.getData());
            if(!ret){
                System.out.println("delete error:" + result.getData());
            }
        }
    }

    public static Object download(String sub_dir,String downloadFileName,List list,Class clazz){
        return download(sub_dir,downloadFileName,null,list,clazz);
    }

    public static Object download(String sub_dir,List list,Class clazz){
        return download(sub_dir,null,list,clazz);
    }

    /**
     * 获取excel文件中的数据
     * @param fileName
     * @return
     */
    public static Result import_excel(String fileName, Class clazz){
        List list = new LinkedList();
        File file;
        Workbook workbook = null;

        Field[] fields = clazz.getDeclaredFields();
        Map<String,Field> fieldMap = new HashMap<>();
        if(clazz.getAnnotation(ImportField.class)==null){
            for (Field field : fields) {
                if (field.getAnnotation(ImportField.class)!=null){
                    fieldMap.put(field.getName(),field);
                }
            }
        }else{
            for (Field field : fields) {
                fieldMap.put(field.getName(),field);
            }
        }


        try {
            file = new File(PathUtil.getSaveDir(sub_dir)+File.separator + fileName);
            InputStream inputStream = new FileInputStream(file);
            workbook = Workbook.getWorkbook(inputStream);
            Sheet[] sheets = workbook.getSheets();
            for (Sheet sheet : sheets) {
                if (sheet.getRows()==0)
                    continue;
                Map<Integer,Field> integerFieldMap = new HashMap<>();

                int i=0;
                Cell cell;
                String fieldName;
                for (int j = 0; j < sheet.getColumns(); j++) {
                    cell = sheet.getCell(j,i);
                    fieldName = cell.getContents();
                    if(fieldMap.containsKey(fieldName)){
                        integerFieldMap.put(j,fieldMap.get(fieldName));
                    }
                }
                for (i = 1; i < sheet.getRows(); i++) {
                    Object object = clazz.getConstructor().newInstance();
                    for (Map.Entry<Integer, Field> integerFieldEntry : integerFieldMap.entrySet()) {
                        cell = sheet.getCell(integerFieldEntry.getKey(),i);
//                        java.beans.PropertyDescriptor pd = new java.beans.PropertyDescriptor(integerFieldEntry.getValue().getName(),clazz);
//                        Method setMethod = pd.getWriteMethod();
//                        Parameter[] parameters = setMethod.getParameters();
//                        if(parameters.length!=0){
//                            Class parameterClazz = parameters[0].getType();
//                            Object arg = null;
//                            if(parameterClazz.getName().equals(Integer.class.getName())){
//                                arg = Integer.valueOf(cell.getContents());
//                            }else{
//                                arg = cell.getContents();
//                            }
//                            setMethod.invoke(object,arg);
//                        }
                        Field field = integerFieldEntry.getValue();
                        if(field.getClass().getName().equals(Integer.class.getName())){
                            ReflectUtil.setFieldValue(object,field,Integer.valueOf(cell.getContents()));
                        }else{
                            ReflectUtil.setFieldValue(object,field,cell.getContents());
                        }
                    }
                    list.add(object);
                }
            }
            return ResultUtil.resultGoodReturner(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.resultBadReturner(e.getMessage());
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }
}
