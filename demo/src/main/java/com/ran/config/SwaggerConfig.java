package com.ran.config;


import com.ran.common.config.BaseSwaggerConfig;
import com.ran.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.applet.AudioClip;
import java.awt.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Swagger相关配置
 * Created by macro on 2019/4/8.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {

        return SwaggerProperties.builder()
                .apiBasePackage("com.ran.controller")
                .title("mall-demo系统")
                .description("SpringBoot版本中的一些示例")
                .contactName("macro")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }

    public static void main(String[] args) {
    }

}
