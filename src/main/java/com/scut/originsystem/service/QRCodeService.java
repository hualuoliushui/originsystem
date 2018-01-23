package com.scut.originsystem.service;

import com.scut.originsystem.entity.*;
import com.scut.originsystem.mapper.*;
import com.scut.originsystem.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class QRCodeService {
    @Autowired
    QRCodeMapper qrCodeMapper;
    @Autowired
    GoodManagerMapper goodManagerMapper;
    @Autowired
    GoodMapper goodMapper;
    @Autowired
    VideoInfoMapper videoInfoMapper;
    @Autowired
    DetectInfoMapper detectInfoMapper;
    @Autowired
    MerchantMapper merchantMapper;
    @Autowired
    CompanyMapper companyMapper;
    @Autowired
    VoucherMapper voucherMapper;
    @Autowired
    DownloadHistoryMapper downloadHistoryMapper;
    @Autowired
    QRCodeScanInfoMapper qrCodeScanInfoMapper;
    @Autowired
    GoodTypeMapper goodTypeMapper;
    @Value("${vueHost}")
    String address;
    @Value("${vuePort}")
    String port;

    private static Logger logger = LoggerFactory.getLogger(QRCodeService.class);
    protected static Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String QRCODE_FAILED = "生成二维码失败";
    private static final String QRCODE_NOT_FOUND = "找不到该二维码";
    private static final String QRCODE_NOT_ENOUGH = "二维码剩余不足";
    private static final String VALID_NOT_MATCH = "验证码错误";



    /**
     * 前端请求一个二维码
     * 提供商品id
     * 如果还有剩余数量
     * 后端提供URL
     * 并且在数据库插入二维码信息
     * 减少二维码剩余数量
     */
    @Transactional
    public Result requestQRCode(int good_id){
        GoodManager goodManager = goodManagerMapper.findGoodManagerByGoodId(good_id);
        if(goodManager.getQrcode_left() <= 0){
            return ResultUtil.resultBadReturner(QRCODE_NOT_ENOUGH);
        }

        Map<String,String> map = new HashMap<>();

        String uuid = UuidUtil.getUUID();
        String valid = RandomUtil.fourValid();
//        try {
//            address = Inet4Address.getLocalHost().getHostAddress();
//        } catch (UnknownHostException e) {
//            logger.error("Get IP address errer!");
//            address = "127.0.0.1";
//        }
//        String url = "http://" + address + ":" + port + "/qrcode/sacnQRCode/" + uuid;
        String url = "http://" + address + ":" + port + "/#/form?" + uuid;

        qrCodeMapper.insertQRCode(uuid,valid,good_id);
        goodManagerMapper.useOneQRCode(good_id);
        logger.info("Request QRcode:good_id is " + good_id);

        map.put("url",url);
        map.put("valid",valid);
        return ResultUtil.resultGoodReturner(map);
    }

    /**
     * 前端扫描一个二维码，URL到后端
     * 后端获取二维码的code，查询二维码信息
     * 返回视频、检测、商户信息
     * 增加扫码次数
     * 如果是第一次扫码，记录第一次时间，返回红包（找出一个红包，然后绑定）
     *
     * 废除
     *
     */
    public Result scanQRCode(String qrcode_url){
        QRCode qrCode = qrCodeMapper.findQRCodeByUrl(qrcode_url);
        if(qrCode == null){
            return ResultUtil.resultBadReturner(QRCODE_NOT_FOUND);
        }

        Map<String,Object> map = new HashMap<>();

        int qrcode_id = qrCode.getId();
        int good_id = qrCode.getGood_id();
        Good good = goodMapper.findGoodsById(good_id);
        int merchant_id = good.getMerchant_id();
        Date date = new Date();
        String today = format.format(date);
        Voucher voucher = null;

        if(qrCode.getScan_time() == 0){
            qrCodeMapper.scanQRCode(qrcode_url);
            qrCodeMapper.firstScanAddTime(qrcode_url,today);
            voucher = voucherMapper.findOneVoucher(merchant_id);
            if(voucher != null){
                voucherMapper.getVoucher(voucher.getVoucher_code(),today,qrcode_id);
            }
        }else {
            qrCodeMapper.scanQRCode(qrcode_url);
        }
        qrCode = qrCodeMapper.findQRCodeByUrl(qrcode_url);
        List<VideoInfo> videoInfo = videoInfoMapper.findVideoInfo1(good_id);
        List detectInfo = detectInfoMapper.findDetectInfo1(good_id);
        Merchant merchant = merchantMapper.findMerchantById(merchant_id);
        Company company = companyMapper.findCompanyByMerchantId(merchant_id);
        qrCode.setQrcode_valid(null);

        map.put("qrcode",qrCode);
        map.put("voucher",voucher);
        map.put("videoInfos",videoInfo);
        map.put("detectInfo",detectInfo);
        map.put("merchant",merchant);
        map.put("company",company);
        map.put("good",good);

        logger.info("Scan QRcode:URL of QRcode if" + qrcode_url);

        return ResultUtil.resultGoodReturner(map);
    }

    //扫描和验证二维码
    @Transactional
    public Result validQRCode(String qrcode_url,String validQRCode, String scan_location){
        QRCode qrCode = qrCodeMapper.findQRCodeByUrl(qrcode_url);
        if(qrCode == null){
            return ResultUtil.resultBadReturner(QRCODE_NOT_FOUND);
        }
        if(!qrCode.getQrcode_valid().equals(validQRCode)){
            return ResultUtil.resultBadReturner(VALID_NOT_MATCH);
        }

        Map<String,Object> map = new HashMap<>();

        int qrcode_id = qrCode.getId();
        int good_id = qrCode.getGood_id();
        Good good = goodMapper.findGoodsById(good_id);
        int merchant_id = good.getMerchant_id();
        Date date = new Date();
        String today = format.format(date);
        Voucher voucher = null;

        if(qrCode.getScan_time() == 0){
            qrCodeMapper.scanQRCode(qrcode_url);
            qrCodeMapper.firstScanAddTime(qrcode_url,today);
            voucher = voucherMapper.findOneVoucher(merchant_id);
            if(voucher != null){
                voucherMapper.getVoucher(voucher.getVoucher_code(),today,qrcode_id);
            }
        }else {
            qrCodeMapper.scanQRCode(qrcode_url);
        }

        //记录扫描信息
        QRCodeScanInfo scanInfo = new QRCodeScanInfo(scan_location,today,qrCode.getId());
        qrCodeScanInfoMapper.insertQRCodeScanInfo(scanInfo);

        qrCode = qrCodeMapper.findQRCodeByUrl(qrcode_url);
        List<VideoInfo> videoInfo = videoInfoMapper.findVideoInfo1(good_id);
        List detectInfo = detectInfoMapper.findDetectInfo1(good_id);
        Merchant merchant = merchantMapper.findMerchantById(merchant_id);
        Company company = companyMapper.findCompanyByMerchantId(merchant_id);
        GoodType goodType = goodTypeMapper.findGoodTypeById(good.getType_id());
        qrCode.setQrcode_valid(null);

        map.put("qrcode",qrCode);
        map.put("voucher",voucher);
        map.put("videoInfos",videoInfo);
        map.put("detectInfo",detectInfo);
        map.put("merchant",merchant);
        map.put("company",company);
        map.put("good",good);
        map.put("goodType",goodType);

        logger.info("Scan QRcode:URL of QRcode if" + qrcode_url);

        return ResultUtil.resultGoodReturner(map);
    }

    /**
     * 尝试二维码word文件
     */
    @Transactional
    public Result qRCodeWord(int good_id, int number,int merchant_id){
        List<String> urls = new ArrayList<>();
        List<String> valids = new ArrayList<>();
        Result result ;
        Map<String,String> map ;
        for (int i = 0; i < number; i++) {
            result = requestQRCode(good_id);
            map = (Map<String, String>) result.getData();
            urls.add(map.get("url"));
            valids.add(map.get("valid"));
        }
        try {
            QRCodeUtil.createQRCode(urls,good_id,merchant_id);
            String fileName = QRCodeUtil.createWord(valids,urls,good_id,merchant_id);
            // TODO 如果成功，要返回下载地址
            if (StringUtils.isEmpty(fileName)){
                return ResultUtil.resultBadReturner(QRCODE_FAILED);
            }else {
                Good good = goodMapper.findGoodsById(good_id);
                String today = format.format(new Date());
                String historyPath = fileName.replace(QRCodeUtil.getQRCodeFilePath(good_id,merchant_id),"");
                DownloadHistory downloadHistory = new DownloadHistory(today,number,merchant_id,historyPath,good.getGood_code(),good.getGood_name(),good.getGood_batch(),good.getId());
                downloadHistoryMapper.insertDownloadHistory(downloadHistory);
                logger.info(String.format("Create QRcode doc:merchant id is %d,good id is %d,number is %d",merchant_id,good_id,number));
                return ResultUtil.resultGoodReturner(fileName);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.resultBadReturner(QRCODE_FAILED);
        }
    }

    public ResponseEntity<byte[]> downloadQRCode(int good_id, int number,int merchant_id){
        Result result = qRCodeWord(good_id, number, merchant_id);
        if (result.getMsg().equals(QRCODE_FAILED)){
            return null;
        }
        String path = (String) result.getData();
        File file=new File(path);
        HttpHeaders headers = new HttpHeaders();
        //为了解决中文名称乱码问题
        String fileName= null;
        try {
            fileName = new String("report.doc".getBytes("UTF-8"),"iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        headers.setContentDispositionFormData("attachment", fileName);
        return new ResponseEntity<byte[]>(FileUtil.getFileToByte(file), headers, HttpStatus.CREATED);
    }

    public Result findValid(String qrcode_url,String valid_code){
        QRCode qrCode = qrCodeMapper.findQRCodeByUrl(qrcode_url);
        if(qrCode == null){
            return ResultUtil.resultBadReturner(QRCODE_NOT_FOUND);
        }
        if (qrCode.getQrcode_valid().equals(valid_code)){
            return ResultUtil.resultGoodReturner();
        }else {
            return ResultUtil.resultBadReturner(VALID_NOT_MATCH);
        }
    }

    //查看下载历史
    public Result findHistory(int merhcant_id, int page){
        List list = downloadHistoryMapper.findHistory(merhcant_id,SqlUtil.getOffset(page));
        Map map = SqlUtil.resultHelper(list,downloadHistoryMapper.findHistoryCount(merhcant_id));
        return ResultUtil.resultGoodReturner(map);
    }

    //下载文件
    public ResponseEntity<byte[]> downloadHistory(String path,int good_id,int merchant_id){
        File file=new File(QRCodeUtil.getQRCodeFilePath(good_id,merchant_id) + path);
        HttpHeaders headers = new HttpHeaders();
        //为了解决中文名称乱码问题
        String fileName= null;
        try {
            fileName = new String("report.doc".getBytes("UTF-8"),"iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        headers.setContentDispositionFormData("attachment", fileName);
        return new ResponseEntity<byte[]>(FileUtil.getFileToByte(file), headers, HttpStatus.CREATED);
    }

}
