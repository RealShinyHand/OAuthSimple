package com.realshinyhand.springtest.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserRegistrationService {
	Logger logger = LoggerFactory.getLogger(UserRegistrationService.class);
	private final UserFindService userFindService;
	private final UserRepository userRepository;
	
	public void register( 
			final String name,
			final String email) {
		final boolean exists = userFindService.existsByEmail(email);
		if(exists == false) {
			final User user = new User(name,email);
			 userRepository.save(user);
		}else {
			logger.info("email 중복");
		}
	}
}
