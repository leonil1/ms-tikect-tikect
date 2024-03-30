package com.codigo.msticket.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//

@Configuration
public class FeignClientConfig {
    @Value("${your.token}")
    private String token;

    @Bean
    public FeignClientInterceptor feignClientInterceptor() {
        return new FeignClientInterceptor(token);
    }
}

//@Configuration
//public class FeignClientConfig {
//
//    @Bean
//    public FeignClientInterceptor feignClientInterceptor() {
//        return new FeignClientInterceptor();
//    }
//}
