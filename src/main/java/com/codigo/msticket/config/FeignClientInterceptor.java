package com.codigo.msticket.config;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignClientInterceptor implements RequestInterceptor {
    private final String token;

    public FeignClientInterceptor(@Value("${your.token}") String token) {
        this.token = token;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", "Bearer " + token);
    }
}




//public class FeignClientInterceptor implements RequestInterceptor {
//
//    private String token;
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    @Override
//    public void apply(RequestTemplate template) {
//        if (token != null) {
//            template.header("Authorization", "Bearer " + token);
//        }
//    }
//}


