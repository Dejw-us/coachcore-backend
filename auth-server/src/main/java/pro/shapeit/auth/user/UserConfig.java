package pro.shapeit.auth.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration(proxyBeanMethods = false)
public class UserConfig {
  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    var user = User.withUsername("test")
        .password(passwordEncoder.encode("test"))
        .roles("test")
        .build();
    return new InMemoryUserDetailsManager(user);
  }
}
