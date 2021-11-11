package org.study.grabyou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.study.grabyou.mapper")
public class GrabyouApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrabyouApplication.class, args);
    }
}
