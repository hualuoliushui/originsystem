package com.scut.originsystem.filter;

import com.alibaba.fastjson.JSONObject;
import com.scut.originsystem.config.AccessManager;
import com.scut.originsystem.enums.ErrorCode;
import com.scut.originsystem.enums.RoleType;
import com.scut.originsystem.session.TokenSession;
import com.scut.originsystem.util.HttpUtil;
import com.scut.originsystem.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Access Token验证过滤器
 */
//@WebFilter(filterName = "tokenFilter", urlPatterns = "/**")
public class TokenFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Expose-Headers", "*");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "0");
        response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token,Access-Token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("XDomainRequestAllowed","1");
        String url = request.getRequestURI();
        String accessToken = request.getHeader("Access-Token");
        if(StringUtils.isEmpty(accessToken)){
            // 区分大小写
            accessToken = request.getParameter("Access-Token");
        }
        TokenSession tokenSession = null;

        // 判断当前请求是否为OPTIONS（在跨域请求中，询问服务器是否运行访问的 第一步）
        if(request.getMethod().equals("OPTIONS")){
            filterChain.doFilter(request, response);
            return;
        }

        // 不需要token和role验证的url
        if (AccessManager.matchUncheckedURIList(url)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 需要token验证的url
        if (true) {
            // 检测token是否存在
            //如果不存在
            if (StringUtils.isEmpty(accessToken)) {
                JSONObject responseBody =  ErrorCode.FORBIDDEN_NEED_ACCESS_TOKEN.makeResponseBody();
                logger.error("Access protect url without token!");
                HttpUtil.setResponse(response, ErrorCode.FORBIDDEN_NEED_ACCESS_TOKEN.getCode()/10000,responseBody);
                return;
            }
            //如果存在就找出来
            tokenSession = RedisUtil.getTokenSession(accessToken);
            //判断是否过期
            //没过期
            if (tokenSession != null) {
                request.setAttribute("tokenSession",tokenSession);
                // 更新token生命周期
                RedisUtil.updateToken(accessToken);
                //需要admin验证的url
                String role = tokenSession.getRole();
                if (AccessManager.matchAdminURIList(url)&&role.equals(RoleType.ADMIN.getRole())) {
                    filterChain.doFilter(request, response);
                    return;
                }else if (AccessManager.matchFinanceURIList(url)&&role.equals(RoleType.FINANCE.getRole())){
                    filterChain.doFilter(request, response);
                    return;
                }else if (AccessManager.matchMerchantURIList(url)&&role.equals(RoleType.MERCHANT.getRole())){
                    filterChain.doFilter(request, response);
                    return;
                }else if (AccessManager.matchOperatorURIList(url)&&role.equals(RoleType.OPERATOR.getRole())){
                    filterChain.doFilter(request, response);
                    return;
                }else {
                    JSONObject responseBody =  ErrorCode.FORBIDDEN_NEED_ROLE.makeResponseBody();
                    logger.error("Access without right!");
                    HttpUtil.setResponse(response, ErrorCode.FORBIDDEN_NEED_ROLE.getCode()/10000, responseBody);
                    return;
                }
            }else {
                //token无效或者过期
                JSONObject responseBody =  ErrorCode.FORBIDDEN_INVALID_ACCESS_TOKEN.makeResponseBody();
                logger.error("Token invalid:" + accessToken);
                HttpUtil.setResponse(response, ErrorCode.FORBIDDEN_INVALID_ACCESS_TOKEN.getCode()/10000, responseBody);
                return;
            }

        }
    }

}
