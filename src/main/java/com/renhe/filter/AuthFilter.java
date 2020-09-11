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
import java.util.Arrays;


/**
 * 系统拦截器
 */
@WebFilter
public class AuthFilter implements Filter {

    private final static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    private final static String[] WHITE_URLS= new String[]{"/auth/login","/auth/logout","/phone/download"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String url = req.getRequestURI();
        String authorization = req.getHeader("Authorization");

        if(StringUtil.isPresent(authorization) || Arrays.asList(WHITE_URLS).contains(url)  ){
            chain.doFilter(request,response);
        }else{
          //  chain.doFilter(request,response);
            Result<String> result = new Result<>();
            result.setCode(-1);
            result.setMessage("非法访问");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            writer.write(JSON.toJSONString(result));
            writer.flush();

        }
    }

    @Override
    public void destroy() {

    }
}
