package jsh.project.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jsh.project.security.service.MemberService;
import lombok.AllArgsConstructor;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private MemberService memberService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web){
        //Spring Security가 무시할 수 있도록 설정(기본 경로 : resources/static)
        web.ignoring().antMatchers("/css/**","/js/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{

        http.authorizeRequests()
        //페이지 권한 설정  
            .antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/user/myinfo").hasRole("MEMBER")
        .antMatchers("/**").permitAll()
        .and()
        
            .formLogin()//로그인 설정
        .loginPage("/user/login")
        .defaultSuccessUrl("/user/login/result")
        .permitAll()
        .and()
            .logout()//로그아웃 설정(기본적으로 http 세션을 제거한다.)
        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
        .logoutSuccessUrl("/user/logout/result")
        .invalidateHttpSession(true)
        .and()
        
        .exceptionHandling().accessDeniedPage("/user/denied");
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }



}