package cn.zx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author zhaoxiang
 * @date 2019/6/18 17:11
 */
@SpringBootApplication
@MapperScan("cn.zx.mapper")
public class SecurityTestApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
       SpringApplication.run(SecurityTestApplication.class, args);
    }

    @Override//为了打包springboot项目
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
