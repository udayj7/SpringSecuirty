package com.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.coyote.http11.Http11InputBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfigurationClass {

	
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public InMemoryUserDetailsManager configure() {
		UserDetails u1 = User.withUsername("Dinga").password(getPasswordEncoder().encode("Dinga@123")).roles("ADMIN").build();
		UserDetails u2 = User.withUsername("Raju").password(getPasswordEncoder().encode("Raju@123")).roles("HR").build();
		UserDetails u3 = User.withUsername("Dimple").password(getPasswordEncoder().encode("Dimple@123")).roles("ADMIN", "HR").build();

		List<UserDetails> l = new ArrayList<>();
		l.add(u1);
		l.add(u2);
		l.add(u3);

		return new InMemoryUserDetailsManager(l);

	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
		.authorizeHttpRequests().requestMatchers("/hr").hasRole("HR")
		.and()
		.authorizeHttpRequests().requestMatchers("/admin").hasRole("ADMIN")
		.and()
		.authorizeHttpRequests().requestMatchers("/home").permitAll()
		.and()
//		.formLogin();
		.httpBasic();
		return http.build();

	}

}
