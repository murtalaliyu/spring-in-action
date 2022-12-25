package tacos.security;

// TODO: Different

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
  }

  /*
  // In-memory implementation
  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder encoder) {
    List<UserDetails> usersList = new ArrayList<>();
    
    usersList.add(new User("buzz", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
    usersList.add(new User("woody", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
    return new InMemoryUserDetailsManager(usersList);
  }
  */

  // Custom UserDetailService implementation
  /*
  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository) {
    return username -> {
      User user = userRepository.findByUsername(username);
      if (user != null) return user;
      throw new UsernameNotFoundException("User '" + username + "' not found");
    };
  }
  */

  // Securing requests
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
//               .antMatchers("/design", "/orders").hasRole("USER")
//               .antMatchers("/", "/**").permitAll()
//               .and().formLogin().loginPage("/login")
//               .and().logout().logoutSuccessUrl("/")
//               .and().csrf().ignoringAntMatchers("/h2-console/**")  // Make H2-Console non-secured. For debugging purposes
//               .and().headers().frameOptions().sameOrigin(); // Allow pages to be loaded in frames from the same origin; needed for H2-Console
               //.and().build();
            .antMatchers(HttpMethod.OPTIONS).permitAll() // needed for Angular/CORS
            .antMatchers("/data-api/**")
            .permitAll()
            //.access("hasRole('USER')")
            .antMatchers(HttpMethod.PATCH, "/data-api/ingredients").permitAll()
            .antMatchers("/**").access("permitAll")

            .and()
            .formLogin()
            .loginPage("/login")

            .and()
            .httpBasic()
            .realmName("Taco Cloud")

            .and()
            .logout()
            .logoutSuccessUrl("/")

            .and()
            .csrf()
            .ignoringAntMatchers("/h2-console/**", "/data-api/**")

            // Allow pages to be loaded in frames from the same origin; needed for H2-Console
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()
    ;
  }
  
}
