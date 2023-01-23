package com.condigence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                // .apis().
                .apis(RequestHandlerSelectors.basePackage("com.condigence.controller"))
                // .paths(PathSelectors.ant("/bs/*"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, newArrayList(
                        new ResponseBuilder().code("500")
                                .description("500 message").build(),
                        new ResponseBuilder().code("403")
                                .description("Forbidden!!!!!").build()
                ));
    }


//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .securityContexts(Arrays.asList(securityContext()))
//                .securitySchemes(Arrays.asList(apiKey()))
//                .select()
//               // .apis(RequestHandlerSelectors.any())
//                .apis(RequestHandlerSelectors.basePackage("com.spring.mongo.demo.controller"))
//                .paths(PathSelectors.any())
//                .build();
//    }


    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("Brahma Shakti REST API", "Some custom description of API.", "API TOS", "Terms of service", new Contact("Condigence", "www.condigence.com", "admin@condigence.com"), "License of API", "API license URL", Collections.emptyList());
        return apiInfo;
    }

    private ApiKey apiKey() {
        return new ApiKey("token", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("token", authorizationScopes));
    }
}
