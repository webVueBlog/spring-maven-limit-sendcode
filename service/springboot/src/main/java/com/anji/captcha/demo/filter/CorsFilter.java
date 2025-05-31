
package com.anji.captcha.demo.filter;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CorsFilter ") // 指定过滤器名称为CorsFilter
@Configuration // 指定该类为配置类
public class CorsFilter implements Filter {
    //初始化方法
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 过滤方法
     * @param servletRequest 获取客户端发送的请求信息
     * @param servletResponse 向客户端发送响应信息
     * @param filterChain 过滤器链，用于将请求传递给下一个过滤器或目标资源
     * @throws ServletException 如果发生Servlet异常，则抛出此异常
     * @throws IOException 如果发生I/O异常，则抛出此异常
     */
    @Override
    public  void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //设置响应内容类型
        response.setContentType("application/json; charset=utf-8");
        //设置响应字符编码
        response.setCharacterEncoding("UTF-8");
        //设置Access-Control-Max-Age，表示在3600秒内不需要再次发送预检请求
        response.setHeader("Access-Control-Max-Age", "3600");
        //设置允许的HTTP请求方法
        response.setHeader("Access-Control-Allow-Methods", "POST, GET,PUT, OPTIONS, DELETE");
        //设置允许的跨域请求来源
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        //设置是否允许发送Cookie
        response.setHeader("Access-Control-Allow-Credentials", "true");
        //设置允许的请求头
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token");
        //继续执行过滤器链
        filterChain.doFilter(request, response);
    }

    //销毁方法
    @Override
    public void destroy() {

    }
}