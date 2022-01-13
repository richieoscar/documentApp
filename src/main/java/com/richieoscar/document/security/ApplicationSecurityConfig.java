package com.richieoscar.document.security;

//import com.richieoscar.document.auth.ApplicationUserService;
import com.richieoscar.document.auth.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

import static com.richieoscar.document.security.ApplicationRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    private PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
        this.passwordEncoder = passwordEncoder;
       // this.applicationUserService = applicationUserService;
        this.applicationUserService = applicationUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/showUpload").hasRole(ADMIN.name())
//                .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(DOCUMENT_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(DOCUMENT_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ADMIN.name(),ADMIN_TRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                //.httpBasic();
                .formLogin()
               // .successHandler(appAuthenticationSuccessHandler())
               // .loginPage("/login") .permitAll()
                .and()
                .rememberMe().tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                .key("verSecured")
                .and()
                .logout()
                .logoutUrl("/logout").clearAuthentication(true).invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login");
    }
    @Bean
    public AppAuthenticationSuccessHandler appAuthenticationSuccessHandler(){
        return new AppAuthenticationSuccessHandler();
    }

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails richie = User.builder().
//                username("richieoscar")
//                .password(passwordEncoder.encode("password"))
//                //.roles(ADMIN.name())
//                .authorities(ADMIN.getAuthorities())
//                .build();
//
//        UserDetails adams = User.builder().
//                username("adams")
//                .password(passwordEncoder.encode("password123"))
//                //.roles(USER.name())
//                .authorities(USER.getAuthorities())
//                .build();
//
//        UserDetails alex = User.builder().
//                username("alex")
//                .password(passwordEncoder.encode("password123"))
//                //.roles(ADMIN_TRAINEE.name())
//                .authorities(ADMIN_TRAINEE.getAuthorities())
//                .build();
//        return new InMemoryUserDetailsManager(richie,adams, alex);
//
//    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
}
