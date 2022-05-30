package com.realshinyhand.springtest.security;

import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
@AllArgsConstructor
public class SecurityConfig {

	private final Environment environment;
	private final String registration = "spring.security.oauth2.client.registration.";
	private final GoogleOAuth2UserService googleOAuth2UserService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		return http.authorizeRequests(
				authorize -> authorize.antMatchers("/login", "/index").permitAll().anyRequest().authenticated())
				.oauth2Login(oauth2 -> oauth2.clientRegistrationRepository(clientRegistrationRepository())
						.authorizedClientService(authorizedClientService())
						.userInfoEndpoint(user -> user
								.oidcUserService(googleOAuth2UserService) //google 인증 , OpenId Connect 1.0
								)
						).build();
	}

	@Bean
	public OAuth2AuthorizedClientService authorizedClientService() {
		return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
	}

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		final List<ClientRegistration> clientRegistrations = Arrays.asList(googleClientRegistration());

		return new InMemoryClientRegistrationRepository(clientRegistrations);
	}

	private ClientRegistration googleClientRegistration() {
		final String clientId = environment.getProperty(registration + "google.client-id");
		final String clientSecret = environment.getProperty(registration + "google.client-secret");

		return CommonOAuth2Provider.GOOGLE.getBuilder("google").clientId(clientId).clientSecret(clientSecret).build();
	}

}