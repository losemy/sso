package com.github.losemy.sso.web.controller;

import com.github.losemy.sso.client.Constant;
import com.github.losemy.sso.client.jwt.JwtTokenUtil;
import com.github.losemy.sso.client.util.CookieUtils;
import com.github.losemy.sso.dal.model.UserDO;
import com.github.losemy.sso.service.UserService;
import com.github.losemy.sso.service.dto.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Description: sso
 * Created by lose on 2019/8/29 21:35
 */
@Controller
public class LoginController {
    private static final String LOGIN_PATH = "/login";

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String index(HttpServletRequest request) {
        String token = CookieUtils.getCookie(request, Constant.SSO_TOKEN_NAME);
        if (token == null || JwtTokenUtil.isExpired(token) ) {
            return "redirect:/login";
        } else {
            return "index";
        }
    }

    @GetMapping("/login")
    public String login(Model model,String backUrl, HttpServletRequest request) {
        // 需要base64 一下 避免？ get形式的参数无法被获取
        String token = CookieUtils.getCookie(request, Constant.SSO_TOKEN_NAME);
        if (StringUtils.isNotBlank(token) && !JwtTokenUtil.isExpired(token)) {
            return "redirect:" + authBackUrl(backUrl, token);
        } else {
            //到页面
            model.addAttribute(Constant.BACK_URL,backUrl);
            return goLoginPath(backUrl, request);
        }
    }

    @GetMapping("/logout")
    public void logout( HttpServletRequest request,HttpServletResponse response,String backUrl) throws IOException {
        //清除当前 同时也需要清除 远程
        CookieUtils.invalidate(request);
        response.sendRedirect(backUrl);
    }

    @PostMapping("/login")
    public String login(String backUrl, UserDTO userDTO,
            HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        UserDO user = userService.findByNameAndPassword(userDTO);
        if(user == null){
            request.setAttribute("errorMessage", "用户不存在");
            return goLoginPath(backUrl, request);
        } else {
            String token = CookieUtils.getCookie(request, Constant.SSO_TOKEN_NAME);
            // 没有登录的情况
            if (StringUtils.isBlank(token) || JwtTokenUtil.isExpired(token)) {
                token = JwtTokenUtil.generate(user.getId());
                addTokenInCookie(token, request, response);
            }

            // 跳转到原请求
            backUrl = URLDecoder.decode(backUrl, "utf-8");
            return "redirect:" + authBackUrl(backUrl, token);
        }
    }

    private String goLoginPath(String backUrl, HttpServletRequest request) {
        request.setAttribute("backUrl", backUrl);
        return LOGIN_PATH;
    }

    private String authBackUrl(String backUrl, String token) {
        StringBuilder sbf = new StringBuilder(backUrl);
        if (backUrl.indexOf("?") > 0) {
            sbf.append("&");
        } else {
            sbf.append("?");
        }
        sbf.append(Constant.SSO_TOKEN_NAME).append("=").append(token);
        return sbf.toString();
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
}
