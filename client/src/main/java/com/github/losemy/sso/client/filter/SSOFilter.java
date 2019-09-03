package com.github.losemy.sso.client.filter;

import cn.hutool.core.util.StrUtil;
import com.github.losemy.sso.client.Constant;
import com.github.losemy.sso.client.bean.SsoResultCode;
import com.github.losemy.sso.client.config.SSOConfig;
import com.github.losemy.sso.client.jwt.JwtTokenUtil;
import com.github.losemy.sso.client.util.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

/**
 * Description: sso
 * Created by lose on 2019/8/29 14:52
 * @author lose
 */
@Component
public class SSOFilter implements Filter {

    private SSOConfig ssoConfig;

    @Autowired
    public void setSsoConfig(SSOConfig ssoConfig) {
        this.ssoConfig = ssoConfig;
    }

    private void addTokenInCookie(String token, HttpServletRequest request, HttpServletResponse response) {
        // Cookie添加token
        Cookie cookie = new Cookie(Constant.SSO_TOKEN_NAME, token);
        cookie.setPath("/");
        if ("https".equals(request.getScheme())) {
            cookie.setSecure(true);
        }
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //判断token是否存在 cookie 跟 ? token
        // 单机版 sso？ 集群版处理 也就是token存放在 redis作为key
        String token = getCookieToken(request);
        if (token == null) {
            token = request.getParameter(Constant.SSO_TOKEN_NAME);
            if (token != null) {
                // 需要先存放 token
                addTokenInCookie(token,request,response);
                // 刷新数据 重定向
                response.sendRedirect(getRemoveTokenBackUrl(request));
                return false;
            }
        // 验证token是否有效
        }else if (!JwtTokenUtil.isExpired(token)) {
            token = request.getParameter(Constant.SSO_TOKEN_NAME);
            if(token != null){
                // 刷新数据 重定向
                response.sendRedirect(getRemoveTokenBackUrl(request));
                return false;
            }
            return true;
        }
        //拼接或者从request获取 处理backURL
        //需要保证获取的是 url

        // 第一次获取不到数据 走 redirectLogin
        // 第二次 backUrl存在 直接返回 走 login方法
        String backUrl = request.getQueryString();
        if(backUrl !=null && backUrl.startsWith(Constant.BACK_URL)){
            return true;
        }
        backUrl = (String) request.getAttribute(Constant.BACK_URL);
        if(StrUtil.isNotEmpty(backUrl)){
            request.setAttribute(Constant.BACK_URL,backUrl);
            return true;
        }
        backUrl = request.getParameter(Constant.BACK_URL);
        if(StrUtil.isNotEmpty(backUrl)){
            return true;
        }

        redirectLogin(request, response);
        return false;
    }


    private boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWith);
    }

    private void responseJson(HttpServletResponse response, String code, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(200);
        PrintWriter writer = response.getWriter();
        writer.write("{\"code\":" + code + ",\"message\":\"" + message + "\"}");
        writer.flush();
        writer.close();
    }


    /**
     * 获取Session中token
     *
     * @param request
     * @return
     */
    private String getCookieToken(HttpServletRequest request) {
        return CookieUtils.getCookie(request, Constant.SSO_TOKEN_NAME);
    }

    /**
     * 跳转登录
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void redirectLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isAjaxRequest(request)) {
            responseJson(response, SsoResultCode.SSO_TOKEN_ERROR, "未登录或已超时");
        } else {
            CookieUtils.invalidate(request);
            String ssoLoginUrl = new StringBuilder().append(ssoConfig.getSsoServerUrl())
                    .append("/login?backUrl=").append(URLEncoder.encode(getBackUrl(request), "utf-8")).toString();
            response.sendRedirect(ssoLoginUrl);
        }
    }

    /**
     * 去除返回地址中的token参数
     * @param request
     * @return
     */
    private String getRemoveTokenBackUrl(HttpServletRequest request) {
        String backUrl = getBackUrl(request);
        return backUrl.substring(0, backUrl.indexOf(Constant.SSO_TOKEN_NAME) - 1);
    }

    /**
     * 返回地址
     * @param request
     * @return
     */
    private String getBackUrl(HttpServletRequest request) {
        return new StringBuilder().append(request.getRequestURL())
                .append(request.getQueryString() == null ? "" : "?" + request.getQueryString()).toString();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        // 拦截器 .js .css .ftl 不需要拦截
        String url = req.getRequestURL().toString();
        //需要定义不被拦截的路径
        if(url.contains(".js") || url.contains(".ico") || url.contains(".css") || url.contains(".ftl") || url.contains(".png") || url.contains(".woff2")){
            filterChain.doFilter(req, res);
            return;
        }
        if(isAjaxRequest(req) && url.contains("/login")){
            filterChain.doFilter(req, res);
            return;
        }

        if(isAccessAllowed(req,res)){
            filterChain.doFilter(req, res);
        }

    }
}
