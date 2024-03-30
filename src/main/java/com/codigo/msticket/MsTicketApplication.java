package com.codigo.msticket;
import com.codigo.msticket.config.FeignClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.Import;
@SpringBootApplication
//@ComponentScan("com.codigo.msticket.client")
//@ComponentScan(basePackages = "com.codigo.msticket.client")
@EnableFeignClients("com.codigo.msticket.client")
@Import(FeignClientConfig.class)
//@EnableEurekaClient
//@EnableFeignClients
//@ImportAutoConfiguration({FeignAutoConfiguration.class})
//@ComponentScan("com.codigo.*")
//@EntityScan("com.codigo.client.*")
//@EnableFeignClients("com.codigo.*")
//@ImportAutoConfiguration({FeignAutoConfiguration.class})
//@EnableJpaRepositories("com.codigo.*")
//@OpenAPIDefinition
public class MsTicketApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsTicketApplication.class, args);
    }

}
