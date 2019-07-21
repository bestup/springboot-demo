package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.demo.service.impl.TUserServiceImpl;

/** @Title: WebSecurityConfig
 */
@Configuration
@EnableWebSecurity	//注解开启Spring Security的功能
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()							//这是认证的请求
				.antMatchers("/","/home","/swagger-ui.html").permitAll()		//这是不需要认证的请求
				.anyRequest().authenticated()				//任何一个认证都需要认证
				.and()
			.formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
	}
	
	@Bean
    UserDetailsService detailsService() {
        return new TUserServiceImpl(); 
    }
	
	//配置数据库中的用户名和密码登录
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService());
    }
	
	@Bean
	public  BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	//为了配置swagger-ui.html在不登录的条件下可访问
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger-ui.html")
                .antMatchers("/webjars/**")
                .antMatchers("/v2/**")
                .antMatchers("/swagger-resources/**");
    }
	
	//从内存只用使用自定义的用户名和密码登录
	/*@Override		
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        //基于内存来存储用户信息
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("user").password(new BCryptPasswordEncoder().encode("123")).roles("USER").and()
                .withUser("admin").password(new BCryptPasswordEncoder().encode("456")).roles("USER","ADMIN");
    }*/

}
