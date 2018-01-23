package com.scut.originsystem.filter;

import com.scut.originsystem.util.ResultUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String filterTest = httpServletRequest.getHeader("Filter-Test");
        if (!StringUtils.isEmpty(filterTest)){
            System.out.println("filterTest is not empty");
            setRespone(httpServletResponse);
        }else {
            doFilter(httpServletRequest,httpServletResponse,filterChain);
        }
    }

    @ResponseBody
    private Object setRespone(HttpServletResponse response){
        return ResultUtil.resultBadReturner("filterTest is not empty");
    }
}
