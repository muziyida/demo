package com.mzyd.demomongodbswagger.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * 这里是swagger2的配置类
 *
 * @author 李奕@megvii.com
 * @date 2021-06-22 10:24
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {


    @Bean
    public Docket adminApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                //只显示admin路径下的页面
                //.paths(Predicates.and(PathSelectors.regex("/controller/.*")))
                .build();

    }



    private ApiInfo adminApiInfo(){

        return new ApiInfoBuilder()
                .title("后台管理系统-API文档")
                .description("本文档描述了mongodb功能定义")
                .version("1.0")
                .contact(new Contact("liyi03","www.megvii.com","liyi03@megvii.com"))
                .build();
    }


}
