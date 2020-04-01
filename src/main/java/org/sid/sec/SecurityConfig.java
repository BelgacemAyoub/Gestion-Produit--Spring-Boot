package org.sid.sec;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
	/*	auth.inMemoryAuthentication().withUser("admin").password("1234").roles("USER", "ADMIN");  
		auth.inMemoryAuthentication().withUser("user").password("1234").roles("USER");  */
		
		auth.jdbcAuthentication()
			.dataSource(dataSource)  											
			.usersByUsernameQuery("select login as principal,pass as credentials,active from users where login=?")  		
			.authoritiesByUsernameQuery("select login as principal, role as role from users_roles where login=? ")			
			.passwordEncoder(new Md5PasswordEncoder())       
			.rolePrefix("ROLE_");
	
	}			
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.formLogin().loginPage("/login");																	
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/user/*"	/* "/index" */).hasRole("USER");							
		http.authorizeRequests().antMatchers("/admin/*"	/* "/form", "/save", "/edit", "/delete" */).hasRole("ADMIN");
		http.exceptionHandling().accessDeniedPage("/403");
	}

}
