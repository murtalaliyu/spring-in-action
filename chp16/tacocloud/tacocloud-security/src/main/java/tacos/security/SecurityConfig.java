package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS).permitAll()    // needed for Angular/CORS
//                .antMatchers(HttpMethod.POST, "/api/ingredients").permitAll()
//                .antMatchers("/api/tacos", "/api/orders/**").permitAll()
//                .antMatchers(HttpMethod.PATCH, "/api/ingredients").permitAll()
//                .antMatchers(HttpMethod.POST, "/actuator/env").permitAll()
//                .antMatchers(HttpMethod.DELETE, "/actuator/env").permitAll()
//                .antMatchers("/**").access("permitAll")
//
//                .and().formLogin().loginPage("/login")
//
//                .and().httpBasic().realmName("Taco Cloud")
//
//                .and().logout().logoutSuccessUrl("/")
//
//                .and().csrf()
//                .ignoringAntMatchers("/h2-console/**", "/api/**")
//                .ignoringAntMatchers("/actuator/env")
//
//                .and().headers().frameOptions().sameOrigin();   // Allow pages to be loaded in frames from the same origin; needed for H2-Console
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder encoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

}
