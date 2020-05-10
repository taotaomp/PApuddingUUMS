package cn.papudding.uums;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "cn.papudding.uums.dao")
public class UumsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UumsApplication.class, args);
    }

}
