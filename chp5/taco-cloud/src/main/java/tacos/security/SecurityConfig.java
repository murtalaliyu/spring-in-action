package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import tacos.User;
import tacos.data.UserRepository;

@Configuration
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
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
  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository) {
    return username -> {
      User user = userRepository.findByUsername(username);
      if (user != null) return user;

      throw new UsernameNotFoundException("User '" + username + "' not found");
    };
  }

  // Securing requests
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.authorizeRequests()
               .mvcMatchers("/design", "/orders").hasRole("USER")
               .anyRequest().permitAll()
               .and().formLogin().loginPage("/login")
               .and().logout().logoutSuccessUrl("/")
               .and().csrf().ignoringAntMatchers("/h2-console/**")  // Make H2-Console non-secured. For debugging purposes
               .and().headers().frameOptions().sameOrigin() // Allow pages to be loaded in frames from the same origin; needed for H2-Console
               .and().build();
  }
  
}
