package com.github.losemy.sso.client.config;

import com.github.losemy.sso.client.filter.SSOFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: sso
 *
 * @author lose on 2019/9/2 9:24
 */
@Configuration
public class SSOAutoConfig {

    @Bean
    public SSOConfig buildSSOConfig(){
        return new SSOConfig();
    }

    @Bean
    public SSOFilter buildSSOFilter(){
        return new SSOFilter();
    }
}
