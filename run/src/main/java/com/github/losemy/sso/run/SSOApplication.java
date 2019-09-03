package com.github.losemy.sso.run;

import com.github.losemy.sso.client.annotations.EnableSSO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Description: sso
 * Created by lose on 2019/8/26 20:57
 */
@SpringBootApplication
@ComponentScan("com.github.losemy")
@EnableSSO
public class SSOApplication {

    public static void main(String[] args) {
        SpringApplication.run(SSOApplication.class,args);
    }
}
