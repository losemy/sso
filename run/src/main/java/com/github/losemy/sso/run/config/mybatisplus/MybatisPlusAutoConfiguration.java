package com.github.losemy.sso.run.config.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: sso
 * Created by lose on 2019/8/27 9:09
 *
 */
@Configuration
public class MybatisPlusAutoConfiguration {
    /**
     * 乐观锁
     *
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 自动填充
     *
     * @return
     */
    @Bean
    public CommonMetaObjectHandler commonMetaObjectHandler() {
        return new CommonMetaObjectHandler();
    }
}
