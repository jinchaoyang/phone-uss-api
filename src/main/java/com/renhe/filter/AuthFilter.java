package com.renhe.filter;

import com.alibaba.fastjson.JSON;
import com.renhe.base.Result;
import com.renhe.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

/**
 * 系统拦截器
 */
//@WebFilter
public class AuthFilter implements Filter {

    private final static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String referer = StringUtil.trim(req.getHeader("Referer"));
        if(StringUtil.isPresent(referer)){
            //if(StringUtil.isPresent(referer) && referer.equals(".unicss.com")){
            chain.doFilter(request,response);
        }else{
            chain.doFilter(request,response);
//            Result<String> result = new Result<>();
//            result.setCode(-1);
//            result.setMessage("非法访问");
//            resp.setCharacterEncoding("UTF-8");
//            PrintWriter writer = resp.getWriter();
//            writer.write(JSON.toJSONString(result));
//            writer.flush();

        }
    }

    @Override
    public void destroy() {

    }
}
