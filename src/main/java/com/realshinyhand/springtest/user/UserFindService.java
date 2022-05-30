package com.realshinyhand.springtest.user;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFindService {
	private final UserRepository userRepository;
	
	public boolean existsByEmail(final String email) {
		return userRepository.existsByEmail(email);
	}
}
