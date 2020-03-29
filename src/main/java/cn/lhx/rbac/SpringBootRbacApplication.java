package cn.lhx.rbac;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Hhhhhhhhhh
 */
@SpringBootApplication
@MapperScan("cn.lhx.rbac.dao")
public class SpringBootRbacApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRbacApplication.class, args);
    }

}
