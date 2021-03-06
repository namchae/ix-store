package com.kakaoix.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket memberGroup() {
        return buildGroup("회원 API", "com.kakaoix.store.member", "Member API", "회원관련 API 정의 (회원가입, 로그인, 로그아웃)");
    }

    @Bean
    public Docket productGroup() {
        return buildGroup("상품 API", "com.kakaoix.store.product", "Product API", "상품관련 API 정의 (상품등록, 상품리스트, 상품삭제)");
    }

    @Bean
    public Docket orderGroup() {
        return buildGroup("주문 API", "com.kakaoix.store.order", "Order API", "주문관련 API 정의 (주문요청, 주문취소, 사용자요청 주문조회, 주문번호 조회)");
    }

    private Docket buildGroup(String groupName, String packages, String apiName, String apiDesc) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage(packages))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalDateTime.class, String.class)
                .apiInfo(new ApiInfo(apiName, apiDesc, "", "", ApiInfo.DEFAULT_CONTACT, "", "", new ArrayList<>()));
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .validatorUrl(null)
                .build();
    }
}