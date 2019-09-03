package com.github.losemy.sso.client.annotations;

import com.github.losemy.sso.client.config.SSOAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Description: sso
 * Created by lose on 2019/8/30 20:25
 * @author lose
 * 使用enable类型注解 实现bean的自动装配
 * 直接@Component比较low
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SSOAutoConfig.class)
@Documented
@Inherited
public @interface EnableSSO {
}
