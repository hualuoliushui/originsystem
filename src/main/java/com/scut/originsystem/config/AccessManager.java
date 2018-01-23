package com.scut.originsystem.config;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Arrays;
import java.util.List;
public class AccessManager {
    private static PathMatcher matcher = new AntPathMatcher();

    // 无需验证列表(包括token验证和角色验证)
    private static List<String> uncheckedURLList = Arrays.asList(
            "/admin/test",
            "/area/getProvinces",
            "/area/getNext",
            "/admin/getName",
            "/user/signUp",
            "/user/login",
            "/user/modifyPassword",
            "/user/modifyEmail",
            "/user/findPassword",
            "/qrcode/findValid",
            "/qrcode/scanQRCode/**",
            "/qrcode/validQRCode",
            "/merchant/registerMerchant",
            //无法设置token
            "/company/getPicture",
            "/company/uploadPicture",
            "/admin/getLogo",
            "/admin/setLogo",
            //下载
            "/merchant/getPictureForWaitForChecking",
            "/merchant/uploadPictureForWaitForChecking",
            "/qrcode/downloadQRCode",

            "/manager/uploadOperationFile",
            "/manager/downloadOperationFile",

            "/manager/uploadPictureForPromotion",
            "/manager/getPictureForPromotion",

            "/qrcode/downloadHistory",
            "/good/uploadPicture",

            "/manager/uploadPictureForOperation",
            "/manager/getPictureForOperation",

//            "/admin/download_findMerchantUsersByConditions",
//            "/good/download_findGoodTypesByGoodNameOrGoodCode",
            "/good/download_findGoodByTypeId",
            //访问静态资源
            "/images/upload/**",
            "/good/getPicture"
    );

    // 需要进行token验证列表
    // 无用
    private static List<String> checkedTokenURLList = Arrays.asList(
            "/XXXXX"
    );

    // ADMIN才能访问的uri
    private static List<String> adminURLList = Arrays.asList(
            "/**"
    );

    // TREASURER能访问的uri
    private static List<String> financeURLList = Arrays.asList(
            "/area/**",
            "/admin/**",
            "/merchant/**",
            "/good/**",
            "/company/**",
            "/qrcode/**",
            "/manager/**"
    );

    // OPERATOR才能访问的uri
    private static List<String> operatoerURLList = Arrays.asList(
            "/area/**",
            "/admin/**",
            "/merchant/**",
            "/good/**",
            "/manager/**",
            "/company/**",
            "/qrcode/**"
    );

    // MERCHANT才能访问的uri
    private static List<String> merchantURLList = Arrays.asList(
            "/area/**",
            "/qrcode/requestQRCode",
            "/voucher/**",
            "/qrcode/downloadQRCode",
            "/qrcode/findHistory",
            "/qrcode/downloadHistory",
            "/merchant/registerMerchant",
            "/merchant/insertWaitForCheckingMerchant",
            "/merchant/insertMerchant",
            "/merchant/modifyMerchant",
            "/merchant/getMerchantDetail",
            "/merchant/getMerchantByUserId",
            "/merchant/merchantReport",
            "/merchant/getMerchantForUpdate",
            "/merchant/selectiveGood",
            "/merchant/selectiveGoodType",
            "/good/**",
            "/manager/**",
            "/company/**",
            "/qrcode/findHistory",
            "/merchant/getMyCheck",
            "/good/getPicture"
    );


    /**
     * 判断url是否匹配uriList中的uri的正则表达式
     */
    public static boolean matchURIList(String url, List<String> uriList) {
        for (String pattern : uriList) {
            if (matcher.match(pattern, url)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 如果该url不需验证就返回true, 否则返回false
     */
    public static boolean matchUncheckedURIList(String uri) {
        return matchURIList(uri, uncheckedURLList);
    }

    /**
     * 判断url是否匹配需要token验证的uri的正则表达式，匹配则返回true
     */
    public static boolean matchTokenURIList(String uri) {
        return matchURIList(uri, checkedTokenURLList);
    }

    /**
     * 判断url是否匹配需要admin验证的uri的正则表达式，匹配则返回true
     */
    public static boolean matchAdminURIList(String url) {
        return matchURIList(url, adminURLList);
    }

    /**
     * 判断url是否匹配需要treasurer验证的uri的正则表达式，匹配则返回true
     */
    public static boolean matchFinanceURIList(String uri) {
        return matchURIList(uri, financeURLList);
    }

    /**
     * 判断url是否匹配需要operator验证的uri的正则表达式，匹配则返回true
     */
    public static boolean matchOperatorURIList(String uri) {
        return matchURIList(uri, operatoerURLList);
    }

    /**
     * 判断url是否匹配需要merchant验证的uri的正则表达式，匹配则返回true
     */
    public static boolean matchMerchantURIList(String uri) {
        return matchURIList(uri, merchantURLList);
    }




    /**
     *
     *
     *
     *
     */
    public static List<String> getUncheckedURIList() {
        return uncheckedURLList;
    }

    public static List<String> getCheckedTokenURIList() {
        return checkedTokenURLList;
    }

    public static List<String> getAdminURIList() {
        return adminURLList;
    }

    public static List<String> getTreasurerURIList() {
        return financeURLList;
    }

    public static List<String> getOperatoerURIList() {
        return operatoerURLList;
    }

    public static List<String> getMerchantURIList() {
        return merchantURLList;
    }
}
