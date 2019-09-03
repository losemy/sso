package com.github.losemy.sso.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: sso
 * Created by lose on 2019/8/29 21:35
 */
@RestController
public class LoginController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

}
