package com.scut.originsystem.service;

import com.scut.originsystem.entity.*;
import com.scut.originsystem.enums.ActivationCode;
import com.scut.originsystem.enums.RoleType;
import com.scut.originsystem.mapper.*;
import com.scut.originsystem.returnentity.Report;
import com.scut.originsystem.util.PathUtil;
import com.scut.originsystem.util.PictureUtil;
import com.scut.originsystem.util.ResultUtil;
import com.scut.originsystem.util.SqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MerchantService {

    @Autowired
    MerchantMapper merchantMapper;
    @Autowired
    MerchantService merchantService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @Autowired
    CompanyMapper companyMapper;
    @Autowired
    CompanyService companyService;
    @Autowired
    GoodMapper goodMapper;
    @Autowired
    GoodManagerMapper goodManagerMapper;
    @Autowired
    QRCodeMapper qrCodeMapper;
    @Autowired
    VoucherMapper voucherMapper;
    @Autowired
    TransactionTemplate txTemplate;
    @Autowired
    WaitForCheckingMerchantMapper waitForCheckingMerchantMapper;
    @Autowired
    QRCodeForCheckMapper qrCodeForCheckMapper;
    @Autowired
    private TokenSessionService tokenSessionService;

    // 保存图片的相对路径
    private static String sub_dir = "/static/images/upload/";

    private static Logger logger = LoggerFactory.getLogger(MerchantService.class);
    protected static Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Result registerMerchant(RegisterMerchantInfo registerMerchantInfo){
        try{
            int retVal = txTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    //数据冗余 area_code
                    registerMerchantInfo.getUser().setArea_code(registerMerchantInfo.getCompany().getArea_code());
                    Result result = userService.signUp(registerMerchantInfo.getUser());
                    if(result.getErrCode()!=0) throw new RuntimeException(result.getMsg());
                    registerMerchantInfo.getMerchant().setUser_id(registerMerchantInfo.getUser().getId());
                    result = merchantService.insertMerchant(registerMerchantInfo.getMerchant());
                    if(result.getErrCode()!=0) throw new RuntimeException(result.getMsg());
                    registerMerchantInfo.getCompany().setMerchant_id(registerMerchantInfo.getMerchant().getId());
                    result = companyService.insertCompany(registerMerchantInfo.getCompany());
                    if(result.getErrCode()!=0) throw new RuntimeException(result.getMsg());
                    return 0;
                }
            });
            logger.info("register merchant:" + registerMerchantInfo.getMerchant().getMerchant_name() +
                    " ,company_name:" + registerMerchantInfo.getCompany().getCompany_name() +
                    " ,user_id is " + registerMerchantInfo.getUser().getId());
            return ResultUtil.resultGoodReturner(registerMerchantInfo);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.resultBadReturner(e.getMessage());
        }
    }

    //用户注册后是商户角色，注册商户信息
    public Result insertMerchant(Merchant merchant){
        User user = userMapper.findUserById(merchant.getUser_id());
        if (!user.getRole().equals(RoleType.MERCHANT.getRole())){
            return ResultUtil.resultBadReturner("该用户不是商户");
        }
        List temMerchant = merchantMapper.findMerchantByMerchantName(merchant.getMerchant_name());
        if (temMerchant.size()!=0){
            return ResultUtil.resultBadReturner("商户名已被使用");
        }
        merchant.setCreate_date(format.format(new Date()));
        merchantMapper.insertMerchant(merchant);
        logger.info("Insert merchant:" + merchant.getMerchant_name() + " is insert by merchant user,user_id is " + merchant.getUser_id());
        return ResultUtil.resultGoodReturner();
    }

    //
    public Result getNoActiveMerchant(int merchant_id){
        Merchant merchant = merchantMapper.findMerchantById(merchant_id);
        User user = userMapper.findUserById(merchant.getUser_id());
        if (user.getActivation_code()!= ActivationCode.NOT_ACTIVATE.getCode()){
            return ResultUtil.resultBadReturner("商户状态错误，当前状态为"+user.getActivation_code());
        }
        Company company = companyMapper.findCompanyByMerchantId(merchant_id);
        WaitForCheckingMerchant waitForCheckingMerchant = new WaitForCheckingMerchant();
        waitForCheckingMerchant.setUser(user);
        waitForCheckingMerchant.setMerchant(merchant);
        waitForCheckingMerchant.setCompany(company);
        return ResultUtil.resultGoodReturner(waitForCheckingMerchant);
    }

    //修改商户名称
    public Result modifyMerchant(String merchant_name,int user_id){
        merchantMapper.modifyMerchant(merchant_name,user_id);
        logger.info("Modify merchant:merchant user id is" + user_id + " ,change name into " + merchant_name);
        return ResultUtil.resultGoodReturner(merchant_name);
    }

    //查看某个商户的全部信息，包括商户信息，公司信息
    public Result getMerchantDetail(int merchant_id){
        Map<String,Object> map = new HashMap<>();
        Merchant merchant = merchantMapper.findMerchantById(merchant_id);
        Company company = companyMapper.findCompanyByMerchantId(merchant_id);
        map.put("merchant",merchant);
        map.put("company",company);
        return ResultUtil.resultGoodReturner(map);
    }

    //用user_id查看某商户全部信息
    public Result getMerchantByUserId(int user_id){
        Map<String,Object> map = new HashMap<>();
        Merchant merchant = merchantMapper.findMerchantByUserId(user_id);
        Company company = companyMapper.findCompanyByMerchantId(merchant.getId());
        map.put("merchant",merchant);
        map.put("company",company);
        return ResultUtil.resultGoodReturner(map);
    }

    //获取商户可修改信息
    public Result getMerchantForUpdate(int merchant_id){
        WaitForCheckingMerchant waitForCheckingMerchant=
                waitForCheckingMerchantMapper.findNoCheckedInfoByMerchant(merchant_id);
        if(waitForCheckingMerchant==null){
            Merchant merchant = merchantMapper.findMerchantById(merchant_id);
            User user = userMapper.findUserById(merchant.getUser_id());
            Company company = companyMapper.findCompanyByMerchantId(merchant_id);
            waitForCheckingMerchant = new WaitForCheckingMerchant();
            waitForCheckingMerchant.setUser(user);
            waitForCheckingMerchant.setMerchant(merchant);
            waitForCheckingMerchant.setCompany(company);
        }
        return ResultUtil.resultGoodReturner(waitForCheckingMerchant);
    }

    public Result uploadPictureForWaitForChecking(MultipartFile file){
        return PictureUtil.uploadSinglePicture(file,sub_dir);
    }

    public void getPictureForWaitForChecking(String filename, HttpServletResponse response){
        try{
            File path = PathUtil.getSaveDir(sub_dir);
            String s = path + File.separator + filename;
            byte[] data = PictureUtil.getPicture(s);
            PictureUtil.setResponseWithPicture(response,data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //提交商户待审核的修改信息
    public Result insertWaitForCheckingMerchant(WaitForCheckingMerchant waitForCheckingMerchant){
        try{
            int retVal = txTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    Merchant merchant = merchantMapper.findMerchantById(waitForCheckingMerchant.getMerchant_id());
                    if (merchant==null)
                        throw new RuntimeException("指定商户不存在");
                    User user = userMapper.findUserById(merchant.getUser_id());
                    if(user.getActivation_code()!=1)
                        throw new RuntimeException("用户未激活或已销户");
                    String today = format.format(new Date());
                    waitForCheckingMerchantMapper.setExpired(waitForCheckingMerchant.getMerchant_id());
                    waitForCheckingMerchant.setCreate_date(today);
                    waitForCheckingMerchantMapper.insertWaitForCheckingMerchant(waitForCheckingMerchant);
                    return 0;
                }
            });
            logger.info("register merchant:" + waitForCheckingMerchant.getMerchant_name() +
                    " ,company_name:" + waitForCheckingMerchant.getCompany_name() +
                    " ,merchant id is " + waitForCheckingMerchant.getMerchant_id());
            return ResultUtil.resultGoodReturner(waitForCheckingMerchant);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.resultBadReturner(e.getMessage());
        }
    }

    //获取指定商户的（信息修改）的商户信息
    public Result findWaitForCheckingMerchantByMerchant(int merchant_id){
        try{
            WaitForCheckingMerchant waitForCheckingMerchant = waitForCheckingMerchantMapper.findWaitForCheckingMerchantByMerchantId(merchant_id);
            return ResultUtil.resultGoodReturner(waitForCheckingMerchant);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.resultBadReturner(e.getMessage());
        }
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

   //按商户名、区域查询商户
    public Result conditionMerchant(HttpServletRequest request,
                                    String merchant_name, String company_area, int page){
        String area_code = this.getArea_code(request);
        int total = merchantMapper.getTotal_conditionMerchant(area_code,merchant_name,company_area);
        List<Merchant> merchantList = merchantMapper.conditionMerchant(area_code,merchant_name,company_area,
                SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(merchantList,total));
    }

    //报表
    public Result merchantReport(int merchant_id){
        Report report = new Report();
        List<String> goodNames= goodMapper.findGoodNames(merchant_id);
        int qRCodeTotal = goodMapper.countQRCode(merchant_id);
        int goodTotal = goodMapper.countGoodNumber(merchant_id);
        double voucherTotal = voucherMapper.amountAllVoucher(merchant_id) * 1.00;
        int voucherNumber = voucherMapper.countAllVoucher(merchant_id);
        int voucherUsedNumber = voucherMapper.amountUsedVoucher(merchant_id);
        Map<String ,Integer> goodAndQRCodeTotal = new TreeMap<>();
        Map<String ,Integer> goodAndGoodTotal = new TreeMap<>();
        for(String name : goodNames){
            goodAndQRCodeTotal.put(name,goodMapper.goodAndQRCodeTotal(merchant_id,name));
        }
        for(String name : goodNames){
            goodAndGoodTotal.put(name,goodMapper.goodAndGoodTotal(merchant_id,name));
        }
        report.setGoodNames(goodNames);
        report.setqRCodeTotal(qRCodeTotal);
        report.setGoodTotal(goodTotal);
        report.setGoodAndGoodTotal(goodAndGoodTotal);
        report.setGoodAndQRCodeTotal(goodAndQRCodeTotal);
        report.setVoucherTotal(voucherTotal);
        report.setVoucherNumber(voucherNumber);
        report.setVoucherUsedNumber(voucherUsedNumber);

        return ResultUtil.resultGoodReturner(report);
    }

    //
    public Result findMerchantsByAreaCodeOrCreateDateOrActivationCode(String area_code,
                                                                      String create_date_start,
                                                                      String create_date_end,
                                                                      int activation_code,
                                                                      int page,
                                                                      HttpServletRequest request){
        String user_area_code = getArea_code(request);
        int total = merchantMapper.getTotal_findMerchantsByAreaCodeOrCreateDateOrActivationCode(user_area_code,area_code,
                create_date_start,create_date_end,activation_code);
        List list = merchantMapper.findMerchantsByAreaCodeOrCreateDateOrActivationCodeWithPage(user_area_code,area_code,
                create_date_start,create_date_end,activation_code,
                page > 0 ? SqlUtil.PAGE_LIMIT : 0, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(list,total));
    }

    //查看我的二维码审核表
    public Result getMyCheck(int merchant_id, int status, int page){
        List<QRCodeForCheck> list = qrCodeForCheckMapper.getMyCheck(merchant_id,status,SqlUtil.getOffset(page));
        Map map = SqlUtil.resultHelper(list,qrCodeForCheckMapper.getMyCheckCount(merchant_id,status));
        return ResultUtil.resultGoodReturner(map);
    }

}
