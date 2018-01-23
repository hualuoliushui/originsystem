package com.scut.originsystem.controller;

import com.scut.originsystem.annotation.SystemLogControllerAnno;
import com.scut.originsystem.entity.Good;
import com.scut.originsystem.entity.GoodType;
import com.scut.originsystem.entity.Merchant;
import com.scut.originsystem.entity.QRCodeForCheck;
import com.scut.originsystem.service.GoodService;
import org.apache.ibatis.annotations.Param;
import org.bouncycastle.ocsp.Req;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/good")
public class GoodController {
    @Autowired
    GoodService goodService;

    // upload picture
    @RequestMapping(value = "/uploadPicture",method = RequestMethod.POST)
    @ResponseBody
    public Object uploadPicture(@RequestParam(value = "file",required = true)MultipartFile file){
        return goodService.uploadPicture(file);
    }

    @RequestMapping(value= "/getPicture",method = RequestMethod.GET)
    @ResponseBody
    public void getPictureForPromotion(@RequestParam(value="fileName",required = true)String fileName, HttpServletResponse response){
        goodService.getPicture(fileName, response);
    }


    //添加商品，自动添加商品管理表
    @RequestMapping(value = "/addGood",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "添加商品，自动添加商品管理表")
    public Object addGood(@Validated @RequestBody Good good){
        return goodService.addGood(good);
    }

    //修改商品信息
    @RequestMapping(value = "/modifyGood",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "修改商品信息")
    public Object modifyGood(@Validated @RequestBody Good good){
        return goodService.modifyGood(good);
    }

    //查看某商户的商品列表
    @RequestMapping(value = "/getGoodList",method = RequestMethod.GET)
    @ResponseBody
    public Object getGoodList(@RequestParam(value = "merchant_id", required = true) int merchant_id,
                              @RequestParam(value = "page",required = false,defaultValue = "1")int page){
        return goodService.getGoodList(merchant_id,page);
    }

    //查看某商品的管理信息
    @RequestMapping(value = "/getGoodManager",method = RequestMethod.GET)
    @ResponseBody
    public Object getGoodManager(@RequestParam(value = "good_id", required = true) int good_id){
        return goodService.getGoodManager(good_id);
    }

    //为商品购买N个二维码
    @RequestMapping(value = "/buyQRCode",method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "为商品购买N个二维码")
    public Object buyQRCode(@RequestParam(value = "good_id", required = true) int good_id,
                            @RequestParam(value = "number", required = true) int number){
        return goodService.buyQRCode(good_id,number);
    }

    //增加销量
    @RequestMapping(value = "/sellGoods",method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "增加销量")
    public Object sellGoods(@RequestParam(value = "good_id", required = true) int good_id,
                            @RequestParam(value = "number", required = true) int number){
        return goodService.sellGoods(good_id,number);
    }

    //删除商品
    @RequestMapping(value = "/deleteGood",method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = "删除商品")
    public Object deleteGood(@RequestParam(value = "good_id", required = true) int good_id){
        return goodService.deleteGood(good_id);
    }

    //通过good_code、good_name、produce_place删除商品总类
    @RequestMapping(value = "deleteGoodByGoodInfo",method = RequestMethod.GET)
    @ResponseBody
    public Object deleteGoodByGoodInfo(@RequestParam(value = "good_code",required = true)String good_code,
                                       @RequestParam(value = "good_name",required = true)String good_name,
                                       @RequestParam(value = "produce_place",required = true)String produce_place,
                                       @RequestParam(value = "merchant_id",required = true)int merchant_id){
        return goodService.deleteGoodByGoodInfo(good_code,good_name,produce_place,merchant_id);
    }

    //通过商品种类
    @RequestMapping(value = "/findGoodsByType",method = RequestMethod.GET)
    @ResponseBody
    public Object findGoodsByType(@RequestParam(value = "type_code", required = true) String type_code,
                                  @RequestParam(value = "merchant_id", required = true) int merchant_id,
                                  @RequestParam(value = "page",defaultValue = "1")int page){
        return goodService.findGoodsByType(type_code, merchant_id,page);
    }

    //查找种类
    @RequestMapping(value = "/findGoodsType",method = RequestMethod.GET)
    @ResponseBody
    public Object findGoodsType(@RequestParam(value = "merchant_id", required = true) int merchant_id,
                                @RequestParam(value = "page",defaultValue = "1")int page){
        return goodService.findGoodsType(merchant_id,page);
    }

    //申请购买二维码
    @RequestMapping(value = "/buyQRCode",method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = "申请购买二维码")
    public Object buyQRCode(@Validated @RequestBody QRCodeForCheck qrCodeForCheck){
        return goodService.buyQRCode(qrCodeForCheck);
    }

    @RequestMapping(value = "/findGoodInventory",method = RequestMethod.GET)
    @ResponseBody
    public Object findGoodInventory(@RequestParam(value = "good_code",required = true)String good_code,
                                    @RequestParam(value = "good_name",required = true)String good_name,
                                    @RequestParam(value = "produce_place",required = true)String produce_place,
                                    @RequestParam(value = "merchant_id",required = true)int merchant_id,
                                    @RequestParam(value = "page",defaultValue = "1")int page){
        return goodService.findGoodInventory(good_code,good_name,produce_place,merchant_id,page);
    }

    @RequestMapping(value = "/findGoodInfoByMerchant",method = RequestMethod.GET)
    @ResponseBody
    public Object findGoodInfoByMerchant(@RequestParam(value = "merchant_id")int merchant_id,
                                         @RequestParam(value = "page",defaultValue = "1")int page){
        return goodService.findGoodInfoByMerchant(merchant_id,page);
    }

    @RequestMapping(value = "/findGoodInfoByGoodName",method = RequestMethod.GET)
    @ResponseBody
    public Object findGoodInfoByGoodName(@RequestParam(value = "merchant_id")int merchant_id,
                                         @RequestParam(value = "good_name")String good_name,
                                         @RequestParam(value = "page",defaultValue = "1")int page){
        return goodService.findGoodInfoByGoodName(merchant_id,good_name,page);
    }


    //查询某商户的可用种类表
    @RequestMapping(value = "/findType",method = RequestMethod.GET)
    @ResponseBody
    public Object findTpye(@RequestParam(value = "merchant_id")int merchant_id,
                           @RequestParam(value = "page",defaultValue = "1")int page){
        return goodService.findTpye(merchant_id,page);
    }

    //增加某商户的种类表
    @RequestMapping(value = "/addType",method = RequestMethod.POST)
    @ResponseBody
    public Object addTpye(@Validated @RequestBody GoodType goodType){
        return goodService.addTpye(goodType);
    }

    //删除某商户的种类表
    @RequestMapping(value = "/deleteType",method = RequestMethod.GET)
    @ResponseBody
    public Object deleteTpye(@RequestParam(value = "type_id")int type_id){
        return goodService.deleteTpye(type_id);
    }

    //通过种类id查找商品信息
    @RequestMapping(value = "/findGoodByTypeId",method = RequestMethod.GET)
    @ResponseBody
    public Object findGoodByTypeId(@RequestParam(value = "merchant_id")int merchant_id,
                                   @RequestParam(value = "type_id")int type_id,
                                   @RequestParam(value = "page",defaultValue = "1")int page){
        return goodService.findGoodByTypeId(merchant_id,type_id,page);
    }

    @RequestMapping(value = "/download_findGoodByTypeId",method = RequestMethod.GET)
    @ResponseBody
    public Object download_findGoodByTypeId(@RequestParam(value = "merchant_id")int merchant_id,
                                            @RequestParam(value = "type_id")int type_id){
        return goodService.download_findGoodByTypeId(merchant_id,type_id);
    }

    //查询某个种类表
    @RequestMapping(value = "/findGoodTypeById",method = RequestMethod.GET)
    @ResponseBody
    public Object findGoodTypeById(@RequestParam(value = "type_id")int type_id){
        return goodService.findGoodTypeById(type_id);
    }

    //按照条件查找商品
    @RequestMapping(value = "/selectiveGood",method = RequestMethod.GET)
    @ResponseBody
    public Object selectiveGood(@RequestParam(value = "merchant_id")int merchant_id,
                                @RequestParam(value = "produce_place",required = false)String produce_place,
                                @RequestParam(value = "pack_type",required = false)String pack_type,
                                @RequestParam(value = "good_code",required = false)String good_code,
                                @RequestParam(value = "good_batch",required = false)String good_batch,
                                @RequestParam(value = "good_name",required = false)String good_name,
                                @RequestParam(value = "type_id",required = true)int type_id,
                                @RequestParam(value = "page",required = false,defaultValue = "1")int page){
        return goodService.selectiveGood( produce_place, pack_type,  good_name,  good_code, merchant_id,  good_batch, type_id, page);
    }


    //按照条件查找种类
    @RequestMapping(value = "/selectiveGoodType",method = RequestMethod.GET)
    @ResponseBody
    public Object selectiveGoodType(@RequestParam(value = "merchant_id",required = false)int merchant_id,
                                    @RequestParam(value = "good_code",required = false)String good_code,
                                    @RequestParam(value = "produce_place",required = false)String produce_place,
                                    @RequestParam(value = "good_name",required = false)String good_name,
                                    @RequestParam(value = "page",required = false,defaultValue = "1")int page){
        return goodService.selectiveGoodType(merchant_id, good_code, produce_place, good_name, page);
    }

    //查询商品
    @RequestMapping(value = "/findGood_1s",method = RequestMethod.GET)
    @ResponseBody
    public Object findGood_1s(@RequestParam(value = "good_code")String good_code,
                              @RequestParam(value = "merchant_id")int merchant_id){
        return goodService.findGood_1s(good_code,merchant_id);
    }

    //查询商品种类
    @RequestMapping(value = "/findGoodType_1s",method = RequestMethod.GET)
    @ResponseBody
    public Object findGoodType_1s(@RequestParam(value = "good_code")String good_code,
                                  @RequestParam(value = "merchant_id")int merchant_id){
        return goodService.findGoodType_1s(good_code,merchant_id);
    }

    //条件查询商品大类
    @RequestMapping(value = "/findGoodTypesByGoodNameOrGoodCode",method = RequestMethod.GET)
    @ResponseBody
    public Object findGoodTypesByGoodNameOrGoodCode(@RequestParam(value = "good_name",required = false)String good_name,
                                                @RequestParam(value = "good_code",required = false)String good_code,
                                                @RequestParam(value = "merchant_name",required = false)String merchant_name,
                                                @RequestParam(value = "page",required = false,defaultValue = "1")int page,
                                                HttpServletRequest request){
        return goodService.findGoodTypesByGoodNameOrGoodCode(good_name,good_code,merchant_name,page,request);
    }

    @RequestMapping(value = "/download_findGoodTypesByGoodNameOrGoodCode",method = RequestMethod.GET)
    @ResponseBody
    public Object download_findGoodTypesByGoodNameOrGoodCode(@RequestParam(value = "good_name",required = false)String good_name,
                                                             @RequestParam(value = "good_code",required = false)String good_code,
                                                             @RequestParam(value = "merchant_name",required = false)String merchant_name,
                                                             HttpServletRequest request){
        return goodService.download_findGoodTypesByGoodNameOrGoodCode(good_name,good_code,merchant_name,request);
    }

    //获取全部商品大类
    @RequestMapping(value = "/findAllGoodTypes",method = RequestMethod.GET)
    @ResponseBody
    public Object findAllGoodTypes(@RequestParam(value = "page",required = false,defaultValue = "1")int page,
                                   HttpServletRequest request){
        return goodService.findAllGoodTypes(page,request);
    }
}
