package com.Library.config;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//@Configuration
//public class AppConfig {
//
//	@Bean
//	public SecurityFilterChain springSecurityConfiguration(HttpSecurity http) throws Exception {
//		
//		http
//		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//		.and()
//		.csrf().disable()
//		.authorizeHttpRequests()
//		.requestMatchers(HttpMethod.POST, "/register/**")
//		.permitAll()
//		.requestMatchers("/swagger-ui*/**","/v3/api-docs/**").permitAll()
//		.requestMatchers(HttpMethod.POST,"/admin/**").hasRole("ADMIN")
//		.requestMatchers(HttpMethod.PUT,"/admin/**").hasRole("ADMIN")
//		.requestMatchers(HttpMethod.GET,"/admin/**").hasRole("ADMIN")
//		.requestMatchers(HttpMethod.DELETE,"/admin/**").hasRole("ADMIN")
//		.requestMatchers(HttpMethod.GET,"/students/**").hasAnyRole("ADMIN","USER","STUDENT")
//		.anyRequest()
//		.authenticated()
//		.and()
//		.addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
//		.addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
//		.formLogin()
//		.and()
//		.httpBasic();
//
//		return http.build();
//
//	}
//
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//
//		return new BCryptPasswordEncoder();
//
//	}


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
@Configuration
public class AppConfig {
@Bean
public SecurityFilterChain springSecurityConfiguration(HttpSecurity http) throws Exception {
	
		http.authorizeHttpRequests(auth ->{
			auth
			.requestMatchers(HttpMethod.POST, "/register/**")
			.permitAll()
			.requestMatchers("/swagger-ui*/**","/v3/api-docs/**").permitAll()
			.requestMatchers(HttpMethod.POST,"/admin/**").hasRole("ADMIN")
			.requestMatchers(HttpMethod.PUT,"/admin/**").hasRole("ADMIN")
			.requestMatchers(HttpMethod.GET,"/admin/**").hasRole("ADMIN")
			.requestMatchers(HttpMethod.DELETE,"/admin/**").hasRole("ADMIN")
			.requestMatchers(HttpMethod.GET,"/students/**").hasAnyRole("ADMIN","USER","STUDENT")
			.anyRequest().authenticated();
		})
		.csrf(csrf -> csrf.disable())
		.addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
		.addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
		.formLogin(Customizer.withDefaults())
		.httpBasic(Customizer.withDefaults());
		return http.build();
}
@Bean
public PasswordEncoder passwordEncoder() {
	
		return new BCryptPasswordEncoder();
	}


}