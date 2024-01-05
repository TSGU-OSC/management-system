package cn.osc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置信息
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 控制层包名，会自动扫描包里的所有接口
                .apis(RequestHandlerSelectors.basePackage("com.osc.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        // 展示在主页的信息
        return new ApiInfoBuilder()
                // 文档标题
                .title("knife4j")
                // 文档描述
                .description("knife4j文档")
                // 你的联系方式
                .contact(new Contact("fsq", "xxx", "xxx"))
                // 文档版本
                .version("1.0")
                .build();
    }



}
