package com.demo.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.demo.common.filters.AjaxAuthenticationFilter;
import com.demo.common.filters.AjaxBasicAuthenticationFilter;
import com.demo.common.filters.EncodingFilter;
import com.demo.common.security.AjaxAccessDeniedHandler;
import com.demo.common.security.AjaxAuthenticationEntryPoint;
import com.demo.common.security.AjaxAuthenticationFailureHandler;
import com.demo.common.security.AjaxAuthenticationSuccessHandler;
import com.demo.common.security.AjaxLogoutSuccessHandler;
import com.demo.service.impl.TUserServiceImpl;


/** @Title: WebSecurityConfig
 */
@Configuration
@EnableWebSecurity	//注解开启Spring Security的功能
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
    AjaxAuthenticationEntryPoint authenticationEntryPoint;//未登陆时返回 JSON 格式的数据给前端（否则为 html）

    @Autowired
    AjaxAuthenticationSuccessHandler authenticationSuccessHandler; //登录成功返回的 JSON 格式数据给前端（否则为 html）

    @Autowired
    AjaxAuthenticationFailureHandler authenticationFailureHandler; //登录失败返回的 JSON 格式数据给前端（否则为 html）

    @Autowired
    AjaxLogoutSuccessHandler logoutSuccessHandler;//注销成功返回的 JSON 格式数据给前端（否则为 登录时的 html）

    @Autowired
    AjaxAccessDeniedHandler accessDeniedHandler;//无权访问返回的 JSON 格式数据给前端（否则为 403 html 页面）
    
    @Autowired
    AjaxBasicAuthenticationFilter ajaxBasicAuthenticationFilter;
    
    @Autowired 
	EncodingFilter encodingFilter;

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);	// 不需要session
		http
			.authorizeRequests()							//这是认证的请求
				.antMatchers("/","/home","/register","/swagger-ui.html").permitAll()		//这是不需要认证的请求
				.anyRequest().authenticated()				//任何一个认证都需要认证
				/*.and()
				.httpBasic().authenticationEntryPoint(authenticationEntryPoint)*/
				.and()
				.addFilter(ajaxAuthenticationFilter())
				.addFilter(ajaxBasicAuthenticationFilter())
                /*.and()
			.formLogin()
				.successHandler(authenticationSuccessHandler) 
	            .failureHandler(authenticationFailureHandler) 
                .loginPage("/login")
                .permitAll()*/
            .logout()
            	.logoutSuccessHandler(logoutSuccessHandler)
                .permitAll();
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
							    .accessDeniedHandler(accessDeniedHandler);
		
		//当系统引入springSecurity时，如果要实现自定义的过滤器，需要这样写，在实现springSecurity过滤器链之前执行我们自定义的过滤器
		http.addFilterBefore(encodingFilter, UsernamePasswordAuthenticationFilter.class);
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
	
	//注册自定义的UsernamePasswordAuthenticationFilter
    @Bean
    public AjaxAuthenticationFilter ajaxAuthenticationFilter() throws Exception {
    	AjaxAuthenticationFilter filter = new AjaxAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);	//登录成功执行
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);	//登录失败执行
        filter.setFilterProcessesUrl("/login");
        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
    
  //注册自定义的UsernamePasswordAuthenticationFilter
    @Bean
    public AjaxBasicAuthenticationFilter ajaxBasicAuthenticationFilter() throws Exception {
    	AjaxBasicAuthenticationFilter filter = new AjaxBasicAuthenticationFilter(authenticationManagerBean());
        return filter;
    }
    
    //注册自定义的过滤器
  	@Bean
  	public FilterRegistrationBean<Filter> registerFilter(){
  		FilterRegistrationBean<Filter> registerFilter = new FilterRegistrationBean<>();
  		registerFilter.setFilter(encodingFilter);
  		registerFilter.addUrlPatterns("/**");
  		registerFilter.setName("EncodingFilter");
  		registerFilter.setOrder(1);
  		return registerFilter;
  	}
  	
  	/**解决springboot跨域问题 */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
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
