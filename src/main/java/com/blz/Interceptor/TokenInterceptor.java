package com.blz.Interceptor;

import com.blz.utils.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");

        String token = null;
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies){
            if(cookie.getName().equals("token")){
                token = cookie.getValue();
                break;
            }
        }

        if (token != null && !token.equals("")){
            boolean result = TokenUtil.verify(token);
            if(result){
                //System.out.println("通过拦截器");
                return true;
            }else {
                //System.out.println("登录失效");
                response.setHeader("refresh","3;/index.html");
                response.getWriter().write("登录失效，3s后跳转到网站首页<a href='"+request.getContextPath()+"/index.html'>立即跳转</a>...");
                return false;
            }
        }else {
            //System.out.println("认证失败");
            response.setHeader("refresh","3;/index.html");
            response.getWriter().write("登录失败，3s后跳转到网站首页<a href='"+request.getContextPath()+"/index.html'>立即跳转</a>...");
            return false;
        }


    }

}