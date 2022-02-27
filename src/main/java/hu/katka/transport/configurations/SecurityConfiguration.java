package hu.katka.transport.configurations;

import hu.katka.transport.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  JwtAuthFilter jwtAuthFilter;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .inMemoryAuthentication().passwordEncoder(passwordEncoder())
        .withUser("addressUser")
        .authorities("AddressManager")
        .password(passwordEncoder().encode("pass"))
        .and()
        .withUser("transportUser")
        .authorities("TransportManager")
        .password(passwordEncoder().encode("pass"))
        .and()
        .withUser("Administrator")
        .authorities("AddressManager", "TransportManager")
        .password(passwordEncoder().encode("pass"));
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/api/login/**").permitAll()
        .antMatchers("/api/addresses/**").hasAuthority("AddressManager")
        .antMatchers("/api/transportPlans/**").hasAuthority("TransportManager")
        .antMatchers("/api/sections/**").hasAuthority("TransportManager")
        .antMatchers("/api/milestones/**").hasAuthority("TransportManager")
        .anyRequest().denyAll();

    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
  }




}
