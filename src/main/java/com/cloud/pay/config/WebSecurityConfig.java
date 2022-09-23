package com.cloud.pay.config;


import com.cloud.pay.security.KaptchaAuthenticationFilter;
import com.cloud.pay.security.UrlUserService;
import com.cloud.pay.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * <Description> <br>
 *
 * @author 丁宁
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019年7月28日 <br>
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UrlUserService urlUserService;
    @Autowired
    SessionRegistry sessionRegistry;

  /**
  *@Description:security配置文件,http中配置放行路径,登陆页面,登陆参数,登陆成功路径,失败路径
  *@Param:[http]
  *@return:
  *@Author:丁宁
  *@Data:2019/7/28
  *
  **/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new KaptchaAuthenticationFilter("/camera/login", "/login"), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/getKaptchaImage").permitAll()
                .antMatchers("/layuiadmin/**").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/fonts/**").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/wxpay/**").permitAll()
                .antMatchers("/test").permitAll()
                .antMatchers("/change").permitAll()
                .antMatchers("/wxpay/**").permitAll()
                .antMatchers("/address/city").permitAll()
                .antMatchers("/regist").permitAll()
                .antMatchers("/grade").permitAll()
                .antMatchers("/checkagentphone").permitAll()
                .antMatchers("/checkagentemail").permitAll()
                .antMatchers("/registagent").permitAll()
                .antMatchers("/codecamera").permitAll()
                .antMatchers("/camerareturn").permitAll()
                .antMatchers("/close").permitAll()
                .antMatchers("/startPic").permitAll()
                .antMatchers("/shutdownPic").permitAll()
                .antMatchers("/gainRtmp").permitAll()
                .antMatchers("/closeGainRtmp").permitAll()
                .antMatchers("/getCameraCode").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .and()
                .httpBasic()
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/camera/login")
                //.successForwardUrl("/index")
               // .defaultSuccessUrl("/index")
               // .failureUrl("/login?error")
                .successHandler(new AuthenticationSuccessHandler() {
                    //用户名和密码正确执行
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        if (principal != null && principal instanceof UserDetails) {
                            String a = ((UserDetails) principal).getUsername();
                            httpServletRequest.getSession().setAttribute("u_name",a);
                            httpServletResponse.setContentType("application/json;charset=utf-8");
                            PrintWriter out = httpServletResponse.getWriter();
                            out.write("{\"status\":\"ok\",\"message\":\"登录成功\"}");
                            out.flush();
                            out.close();
                        }
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    //用户名密码错误执行
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"status\":\"error\",\"message\":\"用户名或密码错误\"}");
                        out.flush();
                        out.close();
                    }
                })
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll();
    }
   /**
   *@Description:将密码进行加密
   *@Param:[auth]
   *@return:
   *@Author:丁宁
   *@Data:2019/7/28
   *
   **/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(urlUserService).passwordEncoder(new PasswordEncoder() {

            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.encode((String) rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(MD5Util.encode((String) rawPassword));
            }
        });
    }
    @Bean
    public SessionRegistry getSessionRegistry(){
        SessionRegistry sessionRegistry=new SessionRegistryImpl();
        return sessionRegistry;
    }

}