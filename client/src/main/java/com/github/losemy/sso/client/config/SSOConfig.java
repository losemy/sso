package com.github.losemy.sso.client.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

/**
 * Description: sso
 * Created by lose on 2019/8/30 9:13
 */
public class SSOConfig {

    @Getter
    @Setter
    @Value("${sso.ssoServerUrl}")
    private String ssoServerUrl;

}
