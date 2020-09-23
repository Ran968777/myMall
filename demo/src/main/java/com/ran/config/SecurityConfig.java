package com.ran.config;


import com.ran.bo.AdminUserDetails;
import com.ran.component.JwtAuthenticationTokenFilter;
import com.ran.component.RestAuthenticationEntryPoint;
import com.ran.component.RestfulAccessDeniedHandler;
import com.ran.model.UmsAdmin;
import com.ran.model.UmsAdminExample;
import com.ran.mapper.UmsAdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * SpringSecurity相关配置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UmsAdminMapper umsAdminMapper;

    @Autowired
    RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/register")
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .anyRequest()
                .authenticated();
        // 禁用缓存
        http.headers().cacheControl();
        // 添加JWT filter
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);

//        http.authorizeRequests()//配置权限
////                .antMatchers("/").access("hasRole('TEST')")//该路径需要TEST角色
//                .antMatchers("/brand/list").hasAuthority("TEST")//该路径需要TEST权限
//                .antMatchers("/**").permitAll()
//                .and()//启用基于http的认证
//                .httpBasic()
//                .realmName("/")
//                .and()//配置登录页面
//                .formLogin()
//                .loginPage("/login")
//                .failureUrl("/login?error=true")
//                .and()//配置退出路径
//                .logout()
//                .logoutSuccessUrl("/")
////                .and()//记住密码功能
////                .rememberMe()
////                .tokenValiditySeconds(60*60*24)
////                .key("rememberMeKey")
//                .and()//关闭跨域伪造
//                .csrf()
//                .disable()
//                .headers()//去除X-Frame-Options
//                .frameOptions()
//                .disable();
    }

    /**
     * 用于配置UserDetailsService及PasswordEncoder
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UmsAdminExample example = new UmsAdminExample();
                example.createCriteria().andUsernameEqualTo(username);
                List<UmsAdmin> umsAdminList = umsAdminMapper.selectByExample(example);
                if (umsAdminList != null && umsAdminList.size() > 0) {
                    return new AdminUserDetails(umsAdminList.get(0));
                }
                throw new UsernameNotFoundException("用户名或密码错误");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
}
