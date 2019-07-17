package cn.zx.config;

import cn.zx.security.CustomAuthenticationProvider;
import cn.zx.service.CustomUserDetailsService;
import cn.zx.service.MyFilterSecurityInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.sql.DataSource;

/**
 * @author zhaoxiang
 * @date 2019/6/18 17:17
 */
@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 所有请求均可访问
         http
             .authorizeRequests()
                 .antMatchers("/","/login","/css/**")
                 .permitAll();
        // 其余所有请求均需要权限，登录后可访问
        http.authorizeRequests()
                .anyRequest()
                .authenticated();

        // 配置登录页面的表单 action 必须是 '/login', 用户名和密码的参数名必须是 'username' 和 'password'，
        // 登录失败的 url 是 '/login-error'
        http
             .formLogin()
                 .loginPage("/login")
                 .loginProcessingUrl("/login")
                 .usernameParameter("username")
                 .passwordParameter("password")
                 .failureUrl("/login?error")
                 .and()

             .logout()
                 .logoutSuccessUrl("/login")
                 .permitAll()
                 .and()
         //登录后记住用户，下次自动登录
         //数据库中必须存在名为persistent_logins的表
                .rememberMe()
                .rememberMeServices(MyRememberMeService())
                .key("INTERNAL_SECRET_KEY");

        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }

    @Bean
    public RememberMeServices MyRememberMeService() {
        JdbcTokenRepositoryImpl rememberMeTokenRepository  = new JdbcTokenRepositoryImpl();
        rememberMeTokenRepository.setDataSource(dataSource);
        PersistentTokenBasedRememberMeServices rememberMeServices =
                new PersistentTokenBasedRememberMeServices("INTERNAL_SECRET_KEY", customUserDetailsService, rememberMeTokenRepository);
        rememberMeServices.setParameter("remember-me");
        return rememberMeServices;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        //inMemoryAuthentication 从内存中获取
        /*String password = new BCryptPasswordEncoder().encode("admin");
        auth
            .inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin").password(password).roles("USER");*/
//        auth.authenticationProvider()
        auth.userDetailsService(customUserDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
