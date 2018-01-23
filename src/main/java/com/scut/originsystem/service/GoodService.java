package com.scut.originsystem.service;

import com.scut.originsystem.entity.*;
import com.scut.originsystem.enums.RoleType;
import com.scut.originsystem.mapper.*;
import com.scut.originsystem.returnentity.MerchantGoodType;
import com.scut.originsystem.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.System;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class GoodService {
    @Autowired
    GoodMapper goodMapper;
    @Autowired
    GoodManagerMapper goodManagerMapper;
    @Autowired
    DetectInfoMapper detectInfoMapper;
    @Autowired
    VideoInfoMapper videoInfoMapper;
    @Autowired
    QRCodeMapper qrCodeMapper;
    @Autowired
    PromotionMapper promotionMapper;
    @Autowired
    QRCodeForCheckMapper qrCodeForCheckMapper;
    @Autowired
    GoodTypeMapper goodTypeMapper;
    @Autowired
    OperationLogMapper operationLogMapper;
    @Autowired
    TokenSessionService tokenSessionService;
    protected static Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static Logger logger = LoggerFactory.getLogger(GoodService.class);
    private static final String sub_dir = "/static/images/upload/";

    // upload file
    public Result uploadPicture(MultipartFile file){
        return PictureUtil.uploadSinglePicture(file,sub_dir);
    }
    public void getPicture(String filename, HttpServletResponse response){
        PictureUtil.download(sub_dir,filename,response);
    }

    //添加商品，自动添加商品管理表
    public Result addGood(Good good) {
        try {
            int life_time = good.getLife_time();
            String expiry_date = DateUtil.getFutureDate(life_time);
            good.setExpiry_date(expiry_date);
            int type_id = good.getType_id();
            GoodType goodType = goodTypeMapper.findGoodTypeById(type_id);
            String good_code = getGoodCodeNumber(good.getMerchant_id(),goodType.getGood_code());
            good.setGood_code(good_code);
            good.setGood_name(goodType.getGood_name());
            good.setProduce_place(goodType.getProduce_place());
            goodMapper.insertGood(good);
        }catch (DuplicateKeyException e){
            e.printStackTrace();
            return ResultUtil.resultBadReturner("good_code 应具有唯一性");
        }
        goodManagerMapper.insetGoodManagerment(good.getId());
        logger.info("Add good:add good by merchant,id is " + good.getMerchant_id() + ",good`s id is " + good.getId());
        return ResultUtil.resultGoodReturner(good);
    }

    //修改商品信息
    public Result modifyGood(Good good) {
        int life_time = good.getLife_time();
        String expiry_date = DateUtil.getFutureDate(life_time);
        good.setExpiry_date(expiry_date);
        int type_id = good.getType_id();
        GoodType goodType = goodTypeMapper.findGoodTypeById(type_id);
        String good_code = getGoodCodeNumber(good.getMerchant_id(),goodType.getGood_code());
        good.setGood_code(good_code);
        good.setGood_name(goodType.getGood_name());
        good.setProduce_place(goodType.getProduce_place());
        goodMapper.updateGood(good);
        logger.info("Modify good:good is modified by merchant,merchant id is " + good.getMerchant_id() + ",good id is " + good.getId());
        good = goodMapper.findGoodsById(good.getId());
        return ResultUtil.resultGoodReturner(good);
    }

    //查看某商户的商品列表
    public Result getGoodList(int merchant_id,int page) {
        int total = goodMapper.getTotal_findGoodsByMerchantId(merchant_id);
        List<Good> goodList = goodMapper.findGoodsByMerchantIdWithPage(merchant_id,
                SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(goodList,total));
    }

    //查看某商品的管理信息
    public Result getGoodManager(int good_id) {
        GoodManager goodManager = goodManagerMapper.findGoodManagerByGoodId(good_id);
        return ResultUtil.resultGoodReturner(goodManager);
    }

    //为商品购买N个二维码
    public Result buyQRCode(int good_id, int number) {
        goodManagerMapper.buyQRCode(good_id, number);
        goodMapper.buyQRCode(good_id, number);
        logger.info("Buy QRcode:buy " + number + " QRcode for good,id is " + good_id);
        return ResultUtil.resultGoodReturner(goodMapper.findGoodsById(good_id));
    }

    //增加销量
    public Result sellGoods(int good_id, int number) {
        goodManagerMapper.sellGood(number, good_id);
        logger.info("Sell goods:sell " + number + " goods,good id is " + good_id);
        GoodManager goodManager = goodManagerMapper.findGoodManagerByGoodId(good_id);
        return ResultUtil.resultGoodReturner(goodManager);
    }

    //删除商品
    @Transactional
    public Result deleteGood(int good_id) {
        goodManagerMapper.deleteGoodManager(good_id);
        detectInfoMapper.deleteDetectInfoByGood(good_id);
        videoInfoMapper.deleteVideoInfo(good_id);
        operationLogMapper.deleteOperationLogByGood(good_id);
        qrCodeMapper.delteQRCode(good_id);

        goodMapper.deleteGood(good_id);
        logger.info("Delete good:good is deleted,id is " + good_id);
        return ResultUtil.resultGoodReturner();
    }

    //删除商品
    @Transactional
    public Result deleteGoodByGoodInfo(String good_code,String good_name,String produce_place,int merchant_id){
        int type_id = goodTypeMapper.findId(good_code,good_name,produce_place,merchant_id);
        List<Good> goods = goodMapper.findGoodInventory(type_id);
        if (goods!=null){
            for(Good good: goods){
                deleteGood(good.getId());
            }
        }
        return ResultUtil.resultGoodReturner();
    }

    //通过种类编码找商品
    public Result findGoodsByType(String type_code,int merchant_id,int page){
        int total = goodMapper.getTotal_findGoodsByType(type_code,merchant_id);
        List<Good> goodList = goodMapper.findGoodsByTypeWithPage(type_code,merchant_id,
                SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(goodList,total));
    }

    //查找种类
    public Result findGoodsType(int merchant_id,int page){
        int total = goodMapper.getTotal_findGoodsType(merchant_id);
        List<String> stringList = goodMapper.findGoodsTypeWithPage(merchant_id,
                SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(stringList,total));
    }

    //申请购买二维码
    public Result buyQRCode(QRCodeForCheck qrCodeForCheck){
        String today = format.format(new Date());
        qrCodeForCheck.setCreate_date(today);
        qrCodeForCheckMapper.insertQRCodeCheck(qrCodeForCheck);
        logger.info("Request for qrcode:number " + qrCodeForCheck.getQrcode_buy_number() + ",merchant is " + qrCodeForCheck.getMerchant_id());
        return ResultUtil.resultGoodReturner(qrCodeForCheck);
    }


    //通过good_code good_name produce_place查找商品
    public Result findGoodInventory(String good_code,String good_name,String produce_place,int merchant_id,int page){
        int type_id = goodTypeMapper.findId(good_code,good_name,produce_place,merchant_id);
        int total = goodMapper.getTotal_findGoodInventory(type_id);
        List<Good> goodList = goodMapper.findGoodInventoryWithPage(type_id,
                SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(goodList,total));
    }

    //通过商户查找商品信息 good_code good_name produce_place
    public Result findGoodInfoByMerchant(int merchant_id,int page){
        int total = goodMapper.getTotal_findGoodInfoByMerchant(merchant_id);
        List<Good> goodList = goodMapper.findGoodInfoByMerchantWithPage(merchant_id,
                SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(goodList,total));
    }

    //通过商品名查找商品信息
    public Result findGoodInfoByGoodName(int merchant_id,String good_name,int page){
        String search = "%" + good_name + "%";
        int total = goodMapper.getTotal_findGoodInfoByGoodName(search,merchant_id);
        List<Good> goodList = goodMapper.findGoodInfoByGoodNameWithPage(search,merchant_id,
                SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(goodList,total));
    }

    //查询某商户的可用种类表
    public Result findTpye(int merchant_id, int page){
        List<GoodType> goodTypeList = goodTypeMapper.findGoodType(merchant_id,SqlUtil.PAGE_LIMIT,SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(goodTypeList,goodTypeMapper.findGoodTypeAmount(merchant_id)));
    }

    //增加某商户的种类表
    public Result addTpye(GoodType goodType){
        try {
            goodType.setGood_code(getGoodCodeChar(goodType.getMerchant_id()));
            goodTypeMapper.insertGoodType(goodType);
        }catch (DuplicateKeyException e){
            e.printStackTrace();
            return ResultUtil.resultBadReturner("good_code应具有唯一性");
        }
        return ResultUtil.resultGoodReturner(goodType);
    }

    //删除某商户的种类表
    public Result deleteTpye(int id){

        goodTypeMapper.deleteGoodType(id);
        return ResultUtil.resultGoodReturner();
    }

    //通过种类id查找商品信息
    public Result findGoodByTypeId(int merchant_id , int type_id, int page){
        List<Good> goodList = goodMapper.findGoodByTypeId(merchant_id,type_id,SqlUtil.PAGE_LIMIT,SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(goodList,goodMapper.findGoodByTypeIdAmount(merchant_id,type_id)));
    }

    //下载-通过种类id查找商品信息
    public Object download_findGoodByTypeId(int merchant_id , int type_id){
        List list = goodMapper.findGoodByTypeId(merchant_id,type_id,
                Integer.MAX_VALUE,0);
        return ImExportUtil.download(ImExportUtil.sub_dir,list,Good.class);
    }

    //查询某个种类表
    public Result findGoodTypeById(int type_id){
        return ResultUtil.resultGoodReturner(goodTypeMapper.findGoodTypeById(type_id));
    }

    //按照条件查找商品
    public Result selectiveGood(String produce_place,String pack_type, String good_name, String good_code,int merchant_id, String good_batch,int type_id,int page){
        List<Good> goodList = goodMapper.selectiveGood(good_batch,produce_place,pack_type,good_name,good_code,merchant_id, type_id,SqlUtil.PAGE_LIMIT,SqlUtil.getOffset(page));
        int amount = goodMapper.selectiveAoumt(good_batch,produce_place,pack_type,good_name,good_code,merchant_id,type_id);
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(goodList,amount));
    }

    //按照条件查找种类
    public Result selectiveGoodType(int merchant_id, String good_code, String produce_place, String good_name,int page){
        List<GoodType> goodList = goodTypeMapper.selectiveGoodType(good_code,produce_place,good_name,merchant_id,SqlUtil.PAGE_LIMIT,SqlUtil.getOffset(page));
        int amount = goodTypeMapper.selectiveAmount(good_code,produce_place,good_name,merchant_id);
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(goodList,amount));
    }

    public Result findGood_1s(String good_code,int merchant_id){
        return ResultUtil.resultGoodReturner(goodMapper.findGood_1s(good_code,merchant_id));
    }

    public Result findGoodType_1s(String good_code,int merchant_id){
        return ResultUtil.resultGoodReturner(goodMapper.findGoodType_1s(good_code,merchant_id));
    }

    private String getGoodCodeChar(int merchant_id){
        String pre;
        do{
            pre = String.valueOf(CHAR.charAt((int) (Math.random() * 26))) + String.valueOf(CHAR.charAt((int) (Math.random() * 26)));
        }while (goodTypeMapper.findGoodTypeNumber(merchant_id,pre) != 0);
        return pre;
    }

    private String getGoodCodeNumber(int merchant_id,String pre){
        String suf;
        do{
            int i = (int)(Math.random()*10000);
            suf = String.format("%05d", i);
        }while (goodMapper.getGoodCertainType(merchant_id,pre + suf) != 0);
        return pre + suf;
    }

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

    public Result findGoodTypesByGoodNameOrGoodCode(String good_name,
                                                String good_code,
                                                String merchant_name,
                                                int page,
                                                HttpServletRequest request){
        String user_area_code = getArea_code(request);
        int total = goodTypeMapper.getTotal_findGoodTypesByGoodNameOrGoodCode(user_area_code,
                good_name,good_code,merchant_name);
        List list = goodTypeMapper.findGoodTypesByGoodNameOrGoodCodeWithPage(user_area_code,
                good_name,good_code,merchant_name,
                page > 0 ? SqlUtil.PAGE_LIMIT:0,SqlUtil.getOffset(page));
        List extra = goodTypeMapper.getGoodNumWithGoodName_findGoodTypesByGoodNameOrGoodCode(user_area_code,
                good_name,good_code,merchant_name);
        Map map = SqlUtil.resultHelper(list,total);
        map.put("extra_data",extra);
        return ResultUtil.resultGoodReturner(map);
    }

    public Object download_findGoodTypesByGoodNameOrGoodCode(String good_name, String good_code,
                                                             String merchant_name, HttpServletRequest request) {
        String user_area_code = getArea_code(request);
        List list = goodTypeMapper.findGoodTypesByGoodNameOrGoodCode(user_area_code,good_name,good_code,merchant_name);
        return ImExportUtil.download(ImExportUtil.sub_dir,list, MerchantGoodType.class);
    }

    public Result findAllGoodTypes(int page,HttpServletRequest request){
        String user_area_code = getArea_code(request);
        int total = goodTypeMapper.getTotal_findAllGoodTypes(user_area_code);
        List list = goodTypeMapper.findAllGoodTypesWithPage(user_area_code,
                page > 0 ? SqlUtil.PAGE_LIMIT:0,SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(list,total));
    }
}
