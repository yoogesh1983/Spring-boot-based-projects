package com.codetutr.config.springSecurity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

public class AuthenticationConfig {
	
	private AuthenticationManagerBuilder authenticationManagerBuilder;
	
	@Autowired
	private AuthenticationConfig(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		this.authenticationManagerBuilder = authenticationManagerBuilder;
	}
	
	/**
	 * {@link InMemoryUserDetailsManager} extends UserDetailsService and will be the default userDetailsService if you din’t registered a custom one<p>
	 */
	@Bean
	private UserDetailsService getUserDetailsService() throws Exception {
		return this.authenticationManagerBuilder.inMemoryAuthentication().getUserDetailsService();
	}

	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		List<AuthenticationProvider> authenticationProviders = new ArrayList<>();
		authenticationProviders.add(getDaoauthenticationProvider());
		return new ProviderManager(authenticationProviders);
	}

	@Bean
	public DaoAuthenticationProvider getDaoauthenticationProvider() throws Exception {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(getUserDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
