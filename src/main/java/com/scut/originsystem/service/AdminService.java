package com.scut.originsystem.service;

import com.scut.originsystem.entity.*;
import com.scut.originsystem.enums.ActivationCode;
import com.scut.originsystem.enums.RoleType;
import com.scut.originsystem.mapper.*;
import com.scut.originsystem.returnentity.MerchantUser;
import com.scut.originsystem.returnentity.QRCodeCount;
import com.scut.originsystem.util.*;
import com.scut.originsystem.validationInterface.InsertUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.File;
import java.lang.System;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdminService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    MerchantMapper merchantMapper;
    @Autowired
    CompanyMapper companyMapper;
    @Autowired
    SystemMapper systemMapper;
    @Autowired
    TransactionTemplate txTemplate;
    @Autowired
    WaitForCheckingMerchantMapper waitForCheckingMerchantMapper;
    @Autowired
    SystemLogMapper systemLogMapper;
    @Autowired
    QRCodeForCheckMapper qrCodeForCheckMapper;
    @Autowired
    GoodMapper goodMapper;
    @Autowired
    GoodManagerMapper goodManagerMapper;
    @Autowired
    TokenSessionService tokenSessionService;
    @Autowired
    OperationLogMapper operationLogMapper;
    @Autowired
    DetectionDeviceMapper detectionDeviceMapper;
    @Autowired
    VideoDeviceMapper videoDeviceMapper;
    @Autowired
    QRCodeMapper qrCodeMapper;

    protected static Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Logger logger = LoggerFactory.getLogger(AdminService.class);

    // 保存图片的相对路径
    private static String sub_dir = "/static/images/upload/";


    //新增用户
    public Result insertUser(User user) {
        User tempUser = userMapper.findUser(user.getUsername());
        if (tempUser != null) {
            return ResultUtil.resultBadReturner("该用户名已被注册");
        }
        String today = format.format(new Date());
        user.setCreate_date(today);
        user.setActivation_code(ActivationCode.ACTIVATED.getCode());
        user.setCheck_state(1);
        user.setPassword(EncryptUtil.encrypt(user.getPassword()));
        userMapper.insertUser(user);
        user = userMapper.findUser(user.getUsername());
        user.setPassword(null);
        logger.info("Sign up by admin:" + user.getUsername() + " sign up,user_id is " + user.getId());
        return ResultUtil.resultGoodReturner(user);
    }

    //删除用户
    public Result deleteUser(int user_id) {
        User tempUser = userMapper.findUserById(user_id);
        if (tempUser == null) {
            return ResultUtil.resultBadReturner("找不到该用户");
        } else {
            userMapper.deleteUser(user_id);
            logger.info("Delete user:" + tempUser.getUsername() + " is deleted,user_id is " + tempUser.getId());
            return ResultUtil.resultGoodReturner();
        }
    }

    //更新用户
    public Result updateUser(User user) {
        userMapper.updateUser2(user);
        return ResultUtil.resultGoodReturner();
    }

    //查看营运商列表
    public Result findAllUser(int page) {
        int total = userMapper.getTotal_findAllUserExceptMerchant();
        List<User> userList = userMapper.findAllUserExceptMerchantWithPage(
                SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        for (User user : userList) {
            user.setPassword(null);
        }
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(userList, total));
    }

    //通过用户名查看用户列表(排除商户）
    public Result findUsersByUserName(String user_name, int page) {
        int total = userMapper.getTotal_findUsersByUserNameExceptMerchant(user_name);
        List<User> userList = userMapper.findUsersByUserNameExceptMerchantWithPage(
                user_name, SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        for (User user : userList) {
            user.setPassword(null);
        }
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(userList, total));
    }

    //通过用户名查看用户列表(只是商户)
    public Result findUsersByUserNameOnlyMerchant(String user_name, int page) {
        int total = userMapper.getTotal_findUsersByUserNameOnlyMerchant(user_name);
        List<User> userList = userMapper.findUsersByUserNameOnlyMerchantWithPage(
                user_name, SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        for (User user : userList) {
            user.setPassword(null);
        }
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(userList, total));
    }

    //查看商户（全部）
    public Result findAllMerchant(HttpServletRequest request,
                                  int page) {
        String area_code = this.getArea_code(request);
        int total = merchantMapper.getTotal_findAllMerchant(area_code);
        List<Merchant> merchantList = merchantMapper.findAllMerchantWithPage(
                area_code,
                SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(merchantList, total));
    }

    //未激活商户列表
    public Result findAllNoActiveMerchant(HttpServletRequest request, int page) {
        String area_code = this.getArea_code(request);
        int total = merchantMapper.getTotal_findAllNoActiveMerchant(area_code);
        List<Merchant> merchantList = merchantMapper.findAllNoActiveMerchantWithPage(
                area_code, SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(merchantList, total));
    }

    ///注册审核不通过,activation_code=4,check_state=1
    @Transactional
    public Result cantActiveMerchant(int user_id,String reason) {
        User user = userMapper.findUserById(user_id);
        userMapper.setActivationCode(ActivationCode.NOT_PASS.getCode(), user_id);
        if (user.getRole().equals(RoleType.MERCHANT.getRole())) {
            merchantMapper.setActivationCodeByUserId(ActivationCode.NOT_PASS.getCode(), user_id);
            String username = user.getUsername();
            String receiveMailAccount = user.getEmail();
            try {
                notify_merchant_register_not_pass(username, receiveMailAccount, reason);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.resultBadReturner("操作成功，邮箱通知失败");
            }
        } else
            throw new RuntimeException("当前用户不是商户");

        logger.info("Cant user:user id is " + user_id);
        return ResultUtil.resultGoodReturner();
    }

    /**
     * 通知商户，注册信息，审核结果（不通过）
     * @param username
     * @param receiveMailAccount
     * @throws Exception
     */
    private void notify_merchant_register_not_pass(String username, String receiveMailAccount, String reason) throws Exception {
        String subject = "审核通知";
        StringBuilder content = new StringBuilder();
        content.append("<h1>"+subject+"</h1><br>");
        content.append(username + ",您好!<br>");
        content.append("您的注册信息，审核未通过!<br>");
        if (!StringUtils.isEmpty(reason))
            content.append("理由：" + reason);
        EmailUtil.sendEmail(receiveMailAccount,subject,content.toString(),username);
    }

    //激活,activation_code=1,check_state=1
//    @Transactional
    public Result activateMerchant(int user_id) {
        User user = userMapper.findUserById(user_id);
        if (user.getRole().equals(RoleType.MERCHANT.getRole())) {
            userMapper.setActivationCode(ActivationCode.ACTIVATED.getCode(), user_id);
            merchantMapper.setActivationCodeByUserId(ActivationCode.ACTIVATED.getCode(), user_id);
            String username = user.getUsername();
            String receiveMailAccount = user.getEmail();
            try {
                notify_merchant_register_pass(username, receiveMailAccount);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.resultBadReturner("操作成功，邮箱通知失败");
            }
        } else {
            throw new RuntimeException("当前用户不是商户");
        }
        logger.info("Activate user:user id is " + user_id);
        return ResultUtil.resultGoodReturner();
    }

    /**
     * 通知商户，注册信息，审核结果（通过）
     * @param username
     * @param receiveMailAccount
     * @throws Exception
     */
    private void notify_merchant_register_pass(String username, String receiveMailAccount) throws Exception {
        String subject = "审核通知";
        StringBuilder content = new StringBuilder();
        content.append("<h1>"+subject+"</h1><br>");
        content.append(username + ",您好!<br>");
        content.append("您的注册信息，审核已通过!<br>");
        EmailUtil.sendEmail(receiveMailAccount,subject,content.toString(),username);
    }

    //注销商户，把激活码设为2
    public Result cancelMerchant(int user_id) {
        User tempUser = userMapper.findUserById(user_id);
        if (tempUser == null) {
            return ResultUtil.resultBadReturner("找不到该用户");
        } else if (!tempUser.getRole().equals(RoleType.MERCHANT.getRole())) {
            return ResultUtil.resultBadReturner("该用户不是商户");
        } else {
            merchantMapper.setActivationCodeByUserId(ActivationCode.CANCEL.getCode(), user_id);
            userMapper.setActivationCode(ActivationCode.CANCEL.getCode(), user_id);
            String username = tempUser.getUsername();
            String receiveMailAccount = tempUser.getEmail();
            try {
                notify_merchant_has_been_cancel(username, receiveMailAccount);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.resultBadReturner("邮箱通知失败");
            }
            logger.info("Cancel merchant:" + tempUser.getUsername() + " is canceled,user_id is " + tempUser.getId());
            return ResultUtil.resultGoodReturner();
        }
    }

    /**
     * 通知商户，其账号已被注销
     * @param username
     * @param receiveMailAccount
     * @throws Exception
     */
    private void notify_merchant_has_been_cancel(String username, String receiveMailAccount) throws Exception {
        String subject = "审核通知";
        StringBuilder content = new StringBuilder();
        content.append("<h1>"+subject+"</h1><br>");
        content.append(username + ",您好!<br>");
        content.append("您的账号已被注销，如有疑问，请联系管理员!<br>");
        EmailUtil.sendEmail(receiveMailAccount,subject,content.toString(),username);
    }

    //查看某个用户信息
    public Result findUser(int user_id) {
        User user = userMapper.findUserById(user_id);
        user.setPassword(null);
        return ResultUtil.resultGoodReturner(user);
    }

    //获取平台信息
    public Result findSystem() {
        return ResultUtil.resultGoodReturner(systemMapper.findSystem());
    }

    //修改平台名字
    public Result setName(String name) {
        systemMapper.setName(name);
        logger.info("System name has changed into:" + name);
        return ResultUtil.resultGoodReturner(systemMapper.findSystem());
    }

    //获取平台名称
    public Result getName() {
        String name = systemMapper.getName();
        if (name == null) {
            name = "";
        }
        return ResultUtil.resultGoodReturner(name);
    }

    //修改平台logo
    public Result setLogo(MultipartFile file) {
        if (file == null)
            return ResultUtil.resultBadReturner("文件列表为空");
        Result result = PictureUtil.uploadSinglePicture(file, null);
        if (result.getErrCode()==0){
            String logo = (String)result.getData();
            systemMapper.setLogo(logo);
            logger.info("System logo has changed into:" + logo);
        }

        return result;
    }

    // 获取平台logo
    public void getLogo(String fileName, HttpServletResponse response) {
        try {
            String filename = systemMapper.getlogo();
            File path = PathUtil.getSaveDir(sub_dir);
            String s = path + File.separator + filename;
            System.out.println("path:" + s);

            byte[] data = PictureUtil.getPicture(s);
            PictureUtil.setResponseWithPicture(response, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //没有任何卵用只是用来测试API
    public Result api(TestEntity testEntity) {
        System.out.println(testEntity.toString());
        return ResultUtil.resultGoodReturner(new TestEntity("name2", "code2"));
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

    //获取待审核的（信息修改）商户列表(按区域）
    public Result findAllWaitForCheckingMerchant(HttpServletRequest request, int page) {
        String area_code = this.getArea_code(request);
        int total = waitForCheckingMerchantMapper.
                getTotal_findAllMerchantIDByActivationCodeAndAreaCode(
                        0, area_code);
        List<Integer> merchant_ids = waitForCheckingMerchantMapper
                .findAllMerchantIDByActivationCodeAndAreaCodeWithPage(
                        0, area_code, SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        List<Merchant> merchantList = new LinkedList<>();
        if (merchant_ids != null && !merchant_ids.isEmpty()) {
            for (Integer merchant_id : merchant_ids) {
                Merchant merchant = merchantMapper.findMerchantById(merchant_id);
                merchantList.add(merchant);
            }
        }
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(merchantList, total));
    }

    /**
     * 设置待审核的（信息修改）商户的状态
     * @param merchant_id
     * @param activation_code
     * @return
     */
    @Transactional
    public Result setWaitForCheckingMerchantActivationCode(int merchant_id, int activation_code) {
        WaitForCheckingMerchant waitForCheckingMerchant = waitForCheckingMerchantMapper.findWaitForCheckingMerchantByMerchantId(merchant_id);
        if (waitForCheckingMerchant == null)
            return ResultUtil.resultBadReturner("指定商户的待审核信息不存在");
        try {
            waitForCheckingMerchantMapper.setActivationMerchant(merchant_id, activation_code);
            if (activation_code == 1) {
                updateWaitForCheckingToMerchant(merchant_id, waitForCheckingMerchant);
                try {
                    notify_merchant_change_pass(waitForCheckingMerchant.getUsername(),waitForCheckingMerchant.getEmail());
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResultUtil.resultBadReturner("操作成功，邮箱通知失败");
                }
            }else{
                User user = userMapper.findUserByMerchant(merchant_id);
                String username = user.getUsername();
                String receiveMailAccount = user.getEmail();
                try {
                    notify_merchant_change_not_pass(username, receiveMailAccount);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResultUtil.resultBadReturner("操作成功，邮箱通知失败");
                }
            }
        logger.info("set merchant id:" + merchant_id + " ,activation code:" + activation_code);
        return ResultUtil.resultGoodReturner();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.resultBadReturner(e.getMessage());
        }
    }

    /**
     * 通知商户，信息修改的审核结果（通过）
     * @param username
     * @param receiveMailAccount
     * @throws Exception
     */
    private void notify_merchant_change_pass(String username, String receiveMailAccount) throws Exception {
        String subject = "审核通知";
        StringBuilder content = new StringBuilder();
        content.append("<h1>"+subject+"</h1><br>");
        content.append(username + ",您好!<br>");
        content.append("您的修改信息，审核已通过！<br>");
        EmailUtil.sendEmail(receiveMailAccount,subject,content.toString(),username);
    }

    /**
     * 通知商户，信息修改的审核结果（不通过）
     * @param receiveMailAccount
     * @param username
     * @throws Exception
     */
    private void notify_merchant_change_not_pass(String username,String receiveMailAccount) throws Exception {
        String subject = "审核通知";
        StringBuilder content = new StringBuilder();
        content.append("<h1>"+subject+"</h1><br>");
        content.append(username + ",您好!<br>");
        content.append("您的修改信息，审核不通过，请仔细阅读注册准则或联系管理员！<br>");
        EmailUtil.sendEmail(receiveMailAccount,subject,content.toString(),username);
    }

    /**
     * 将商户信息更新到正式表
     * @param merchant_id
     * @param waitForCheckingMerchant
     */
    private void updateWaitForCheckingToMerchant(int merchant_id, WaitForCheckingMerchant waitForCheckingMerchant) {
        Merchant merchant = merchantMapper.findMerchantById(merchant_id);
        User user = userMapper.findUserById(merchant.getUser_id());
        Company company = companyMapper.findCompanyByMerchantId(merchant_id);
        merchantMapper.modifyMerchant(waitForCheckingMerchant.getMerchant_name(), merchant.getUser_id());
//                        user.setUsername(waitForCheckingMerchant.getUsername());//不更新用户名
//        user.setPassword(waitForCheckingMerchant.getPassword());//不修改密码
        user.setContact_name(waitForCheckingMerchant.getContact_name());
        user.setPhone(waitForCheckingMerchant.getPhone());
        user.setEmail(waitForCheckingMerchant.getEmail());
        user.setArea_code(waitForCheckingMerchant.getArea_code());
        userMapper.updateUser(user);
        company.setCompany_name(waitForCheckingMerchant.getCompany_name());
        company.setCompany_introduction(waitForCheckingMerchant.getCompany_introduction());
        company.setGis_location(waitForCheckingMerchant.getGis_location());
        company.setBusiness_scope(waitForCheckingMerchant.getBusiness_scope());
        company.setCompany_location(waitForCheckingMerchant.getCompany_location());
        company.setCompany_area(waitForCheckingMerchant.getCompany_area());
        company.setLegal_person(waitForCheckingMerchant.getLegal_person());
        company.setCompany_code(waitForCheckingMerchant.getCompany_code());
        company.setCompany_picture(waitForCheckingMerchant.getCompany_picture());
        company.setArea_code(waitForCheckingMerchant.getArea_code());
        companyMapper.updateCompany(company);
    }

    //查看系统日志
    public Result findSystemLogs(String role, String start_time, String end_time, int page) {
        int total = systemLogMapper.getTotal_findSystemLogsWithPage(role, start_time, end_time);
        List<SystemLog> systemLogList = systemLogMapper.findSystemLogsWithPage(
                role, start_time, end_time, SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(systemLogList, total));
    }

    //查看当前管理员所在区域的商户列表
    public Result findMerchantsByArea(HttpServletRequest request, int page) {
        String area_code = this.getArea_code(request);
        int total = merchantMapper.getTotal_findMerchantsByArea(area_code);
        List<Merchant> merchantList = merchantMapper.findMerchantsByAreaWithPage(
                area_code, SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(merchantList, total));
    }

    /**
     * TODO 暂时没有确定如何通知商户审核状态
     */

    //查看有没有需要审核的二维码申请表
    public Result getCheck(int page) {
        int total = qrCodeForCheckMapper.getTotal_findUncheckedQRCode();
        List<QRCodeForCheck> qrCodeForCheckList = qrCodeForCheckMapper.findUncheckedQRCodeWithPage(
                SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(qrCodeForCheckList, total));
    }

    //审核二维码申请表，写详情，发送审核结果
    // TODO
    @Transactional
    public Result check(int check_state, int check_man_id, String check_detail, int check_id) {
        String today = format.format(new Date());
        qrCodeForCheckMapper.check(check_state, check_man_id, check_detail, today, check_id);
        logger.info("Check qrcode request by " + check_man_id + ",state is " + check_state);
        QRCodeForCheck qrCodeForCheck = qrCodeForCheckMapper.findOne(check_id);
        if (check_state == 1) {
            goodMapper.buyQRCode(qrCodeForCheck.getGood_id(), qrCodeForCheck.getQrcode_buy_number());
            goodManagerMapper.buyQRCode(qrCodeForCheck.getGood_id(), qrCodeForCheck.getQrcode_buy_number());
        }
        return ResultUtil.resultGoodReturner(qrCodeForCheck);
    }

    //查看某个二维码申请表
    public Result getOne(int id) {
        return ResultUtil.resultGoodReturner(qrCodeForCheckMapper.findOne(id));
    }

    //查看全部商户的用户信息
    public Result findMerchantUser(int page) {
        int total = userMapper.getTotal_findMerchantUser();
        List<User> userList = userMapper.findMerchantUserWithPage(
                SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(userList, total));
    }

    public Object test() {
//        return "gss";
        List<User> userList = userMapper.findMerchantUser();
        return ImExportUtil.download(ImExportUtil.sub_dir, userList, User.class);
    }

    public Result insertUserWithBatch(MultipartFile uploadfile) {
        Result result = FileUtil.uploadSingleFileForExcel(uploadfile,ImExportUtil.sub_dir);
        if(result.getErrCode()!=0)
            return result;
        String fileName = (String)result.getData();
        result = ImExportUtil.import_excel(fileName,User.class);
        if(result.getErrCode()!=0)
            return result;
        List<User> insertUserList = (List<User>)result.getData();
        Result sub_result;

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator  = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> set;
        int index = -1;
        Map<Integer,List<String>> integerListMap = new HashMap<>();
        List<String> stringList;
        for (User user : insertUserList) {
            index++;
            set = validator.validate(user, InsertUser.class);
            if(!set.isEmpty()){
                //出错
                stringList = new LinkedList<>();
                for (ConstraintViolation<User> insertUserConstraintViolation : set) {
                    stringList.add(insertUserConstraintViolation.getMessage());
                }
                integerListMap.put(index,stringList);
                continue;
            }
            sub_result = insertUser(user);
            if(sub_result.getErrCode()!=0){
                //出错
                stringList = new LinkedList<>();
                stringList.add(sub_result.getMsg());
                integerListMap.put(index,stringList);
                continue;
            }
        }
        FileUtil.deleteFile(ImExportUtil.sub_dir,fileName);
        if(!integerListMap.isEmpty()){
            return ResultUtil.resultBadReturner(integerListMap);
        }else{
            return ResultUtil.resultGoodReturner();
        }
    }

    //查找所有的掉线的视频
    public Result findAllDropVideo(int page){
        Map map = SqlUtil.resultHelper(videoDeviceMapper.findAllDrop(SqlUtil.PAGE_LIMIT,SqlUtil.getOffset(page)),videoDeviceMapper.findAllDropPage());
        return ResultUtil.resultGoodReturner(map);
    }

    //查找所有的掉线的检测
    public Result findAllDropDetection(int page){
        Map map = SqlUtil.resultHelper(detectionDeviceMapper.findAllDrop(SqlUtil.PAGE_LIMIT,SqlUtil.getOffset(page)),detectionDeviceMapper.findAllDropPage());
        return ResultUtil.resultGoodReturner(map);
    }

    //查找某商户掉线的视频
    public Result findMerchantDropVideo(int merchant_id, int page){
        Map map = SqlUtil.resultHelper(videoDeviceMapper.findMerchantDrop(merchant_id,SqlUtil.PAGE_LIMIT,SqlUtil.getOffset(page)),videoDeviceMapper.findMerchantDropPage(merchant_id));
        return ResultUtil.resultGoodReturner(map);
    }

    //查找某商户掉线的检测
    public Result findMerchantDropDetection(int merchant_id, int page){
        Map map = SqlUtil.resultHelper(detectionDeviceMapper.findMerchantDrop(merchant_id,SqlUtil.PAGE_LIMIT,SqlUtil.getOffset(page)),detectionDeviceMapper.findMerchantDropPage(merchant_id));
        return ResultUtil.resultGoodReturner(map);
    }

    //查找指定区域的商户信息
    public Result findMerchantByAreaCode(String area_code,int page){
        int total = merchantMapper.getTotal_findMerchantsByArea(area_code);
        List<Merchant> merchantList = merchantMapper.findMerchantsByAreaWithPage(
                area_code, SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(merchantList, total));
    }

    public Result findMerchantUserByAreaCode(String area_code,int page){
        int total = userMapper.getTotal_findMerchantUserByAreaCode(area_code);
        List<User> list = userMapper.findMerchantUserByAreaCodeWithPage(
                area_code, SqlUtil.PAGE_LIMIT, SqlUtil.getOffset(page));
        return ResultUtil.resultGoodReturner(SqlUtil.resultHelper(list, total));
    }


    //条件查询商户用户信息
    public Result findMerchantUsersByConditions(String area_code, String create_date_start,
                                                                          String create_date_end, int activation_code,
                                                                          int page, HttpServletRequest request) {
        String user_area_code = getArea_code(request);
        int total = userMapper.getTotal_findMerchantUsersByConditions(user_area_code,area_code,
                create_date_start,create_date_end,activation_code);
        List<MerchantUser> list = userMapper.findMerchantUsersByConditionsWithPage(user_area_code,area_code,
                create_date_start,create_date_end,activation_code,
                page > 0 ? SqlUtil.PAGE_LIMIT : 0, SqlUtil.getOffset(page));
        List area_codes = userMapper.getAreaCodeNum_findMerchantUsersByConditions(user_area_code,area_code,
                create_date_start,create_date_end,activation_code);
        Map map = SqlUtil.resultHelper(list,total);
        map.put("area_codes",area_codes);
        return ResultUtil.resultGoodReturner(map);
    }

    public Object download_findMerchantUsersByConditions(String area_code, String create_date_start,
                            String create_date_end, int activation_code,
                            int page, HttpServletRequest request){
        String user_area_code = getArea_code(request);
        List<MerchantUser> list = userMapper.findMerchantUsersByConditions(user_area_code,area_code,
                create_date_start,create_date_end,activation_code);
        return ImExportUtil.download(ImExportUtil.sub_dir, list, MerchantUser.class);
    }

    //按照区域查找该区域下的所有商户的二维码数量总数，已经扫描的二维码总数
    public Result findQRCodeByArea(String code){
        List<QRCodeCount> qrCodeCountList = qrCodeMapper.findQRCodeTotalByArea(code);
        for (QRCodeCount qrCodeCount : qrCodeCountList){
            String area_code = qrCodeCount.getArea_code();
            qrCodeCount.setQrcode_left(qrCodeMapper.findQRCodeLeftByArea(area_code));
        }
        return ResultUtil.resultGoodReturner(qrCodeCountList);
    }

}
