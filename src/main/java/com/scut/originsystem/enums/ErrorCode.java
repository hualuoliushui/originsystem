package com.scut.originsystem.enums;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 错误码枚举类
 */
public enum ErrorCode {
    //状态码400
    PARAM_ERR_COMMON(4001000, "参数错误"),
    PARAM_ERR_REQUEST_DATA_FIELD_UNPASS(4001001, "请求数据字段验证不通过"),
    PARAM_ERR_REQUEST_DATA_REQUIED_FIELD_IS_NULL(4001002, "请求数据必须字段不可为空"),
    PARAM_ERR_PHONE_VERIFY_CODE_NO_FOUND(4001003, "手机验证码不存在"),
    PARAM_ERR_WRONG_PHONE_VERIFY_CODE(4001004, "手机验证码错误"),
    PARAM_ERR_PHONE_OCCUPIED(4001005, "手机号已绑定到其它帐号"),
    PARAM_ERR_EMAIL_OCCUPIED(4001006, "邮箱已绑定到其它帐号"),
    PARAM_ERR_PASSWORD(4001007, "密码错误"),
    PARAM_ERR_INVALID_REFRESH_TOKEN(4001010, "刷新token不合法"),
    PARAM_ERR_PRODUCT_HAS_RELEASED(4001016, "该产品或该设备所属产品已发布，不能删除"),
    PARAM_ERR_FIRMWARE_VERSION_HAS_ALREADY_EXISTED(4001017, "固件版本已存在"),
    PARAM_ERR_INDEX_OCCUPIED(4001019, "数据端点索引已存在"),
    PARAM_ERR_DATAPOINT_HAS_RELEASED(4001020,"已发布产品的数据端点不可删除"),
    PARAM_ERR_DEVICE_MAC_ADDR_HAS_ALREADY_EXISTED(4001021, "该产品下设备MAC地址重复"),
    PARAM_ERR_EMAIL_VERIFY_CODE_NO_FOUND(4001028, "邮箱验证码不存在"),
    PARAM_ERR_WRONG_EMAIL_VERIFY_CODE(4001029, "邮箱验证码错误"),
    PARAM_ERR_USER_EMAIL_WITHOUT_ACTIVATE(4001032, "用户邮箱尚未验证"),
    PARAM_ERR_USER_ALREADY_BINDED_DEVICE(4001033, "用户已经绑定设备"),
    PARAM_ERR_USER_NOT_BIND_DEVICE(4001034, "用户没有绑定该设备"),
    PARAM_ERR_WRONG_BIND_DATA(4001041, "设备绑定密钥错误"),
    PARAM_ERR_SMS_FREQUENCY_LIMIT_REACH(4001052, "手机短信发送超过频率限制"),
    PARAM_ERR_DEVICE_OFFLINE(4001054, "设备不在线"),
    PARAM_ERR_USER_EMAIL_ALREADY_ACTIVATE(4001063, "用户邮箱已经激活"),
    PARAM_ERR_USERNAME_OCCUPIED(4001112, "用户名已被占用"),
    PARAM_ERR_EMAIL_FREQUENCY_LIMIT_REACH(4001113, "邮件发送超过频率限制"),
    PARAM_ERR_USER_IS_BINDING_DEVICE(4001114, "设备正在被当前用户绑定"),
    PARAM_ERR_APP_VERSION_ALREADY_EXISTED(4001115, "APP版本已存在"),
    PARAM_ERR_FIRMWARE_ALREADY_RELEASE(4001116, "固件已发布，不能改为未发布"),
    PARAM_ERR_FIRMWARE_TARGET_VERSION_MUST_GREATER_THAN_FROM_VERSION(4001117, "固件目标版本必须大于起始版本"),
    PARAM_ERR_CAN_NOT_DELETE(4001118, "存在相关联信息，不能删除"),
    PARAM_ERR_CORPORATION_NAME_OCCUPIED(4001121, "企业名称已被占用"),
    PARAM_ERR_DEVICE_NUM_ALREADY_EXISTED(4001122, "设备号重复"),
    PARAM_ERR_USER_ACCOUNT_INACTIVE(4001123, "用户账号已停用"),
    PARAM_ERR_FIRMWARE_VERSION_NOT_RELEASE(4001124,	"固件起始版本或目标版本未发布"),
    PARAM_ERR_DEVICE_SN_CODE_ALREADY_EXISTED(4001125, "设备SN码重复"),
    PARAM_ERR_OAUTH_CODE(4001126, "第三方平台授权码错误"),
    PARAM_ERR_NAME_OCCUPIED(4001128, "名称已被占用"),


    //状态码403
    FORBIDDEN_NEED_ACCESS_TOKEN(4031002, "禁止访问,需要Access-Token"),
    FORBIDDEN_INVALID_ACCESS_TOKEN(4031003, "无效的Access-Token"),
    FORBIDDEN_CANNOT_BIND_DEVICE(4031013, "设备不能被绑定"),
    FORBIDDEN_NEED_ROLE(4031014, "需要相应的权限"),  //TODO 细分用户，管理员权限错误，暂时不做
    FORBIDDEN_INVALID_OAUTH_ACCESS_TOKEN(4031015, "无效的第三方平台AccessToken"),


    //状态码404
    NOT_FOUND_URL(4041001, "URL找不到"),
    NOT_FOUND_ADMIN(4041002, "管理员不存在"),
    NOT_FOUND_SUPER_ADMIN(4041003, "超级管理员不存在"),
    NOT_FOUND_CORP_ADMIN(4041004, "企业管理员不存在"),
    NOT_FOUND_USER(4041011, "用户不存在"),
    NOT_FOUND_PRODUCT(4041005, "产品信息不存在"),
    NOT_FOUND_FIRMWARE(4041006, "产品固件不存在"),
    NOT_FOUND_DATAPOINT(4041007, "数据端点不存在"),
    NOT_FOUND_DEVICE(4041008, "设备不存在"),
    NOT_FOUND_FIRMWARE_UPGRADE_TASK(4041013, "升级任务不存在"),
    NOT_FOUND_ALERT_RULE(4041015, "告警规则不存在"),
    NOT_FOUND_APP(4041020, "APP不存在"),
    NOT_FOUND_EMAIL_ACTIVATE_TOKEN(4041030, "激活信息已失效"),
    NOT_FOUND_CORPORATION(4041010, "企业不存在"),
    NOT_FOUND_BIND_USER(4041032, "设备没有绑定用户"),
    NOT_FOUND_FIRMWARE_VERSION(4041033, "固件版本不存在"),
    NOT_FOUND_APP_VERSION(4041034, "APP版本不存在"),
    NOT_FOUND_DATAPOINT_BY_PRODUCT(4041031, "产品下的该数据端点不存在"),
    NOT_FOUND_OAUTH_TYPE(4041035, "第三方平台类型不存在"),
    NOT_FOUND_PLATFORM(4041037, "平台不存在"),

    //状态码408
    TIMEOUT_BIND_DEVICE(4081001, "绑定设备超时"),

    // 服务器异常503
    SERVER_EXCEPTION(5031001, "服务器发生异常"),
    VERIFY_CODE_SEND_FAIL(5031002, "验证码发送失败"),
    JSON_PARSE_FAIL(5031003, "JSON解析失败"),
    NOT_FOUND_REQUEST_BODY(5031004, "请求body为空");

    private final Integer code;
    private final String msg;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 生成错误响应
     * Body格式为 {"error":{"code":"错误码","msg":"错误码信息"}}
     */
    public ResponseEntity<JSONObject> makeResponse() {

        JSONObject error = makeResponseBody();

        HttpStatus httpStatus = getHttpStatus(getCode());

        logger.error(error.toJSONString());

        return new ResponseEntity<JSONObject>(error, httpStatus);
    }

    public JSONObject makeResponseBody() {
//        JSONObject codeMsg = new JSONObject();
//        codeMsg.put("code", getCode());
//        codeMsg.put("msg", getMsg());
        JSONObject error = new JSONObject();
        error.put("errCode", 1);
        error.put("msg",getMsg());
        error.put("data",null);

        return error;
    }

    public HttpStatus getHttpStatus(int code) {
        HttpStatus httpStatus;
        try {
            httpStatus = HttpStatus.valueOf(code/10000);
        }
        catch (IllegalArgumentException e) {
            httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
        }
        return httpStatus;
    }
}
