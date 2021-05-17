package com.sellandsign.test.service;

import com.sellandsign.test.repository.h2.apikey.ApiKeyRepository;
import com.sellandsign.test.repository.h2.user.UserRepository;
import com.sellandsign.test.dto.UserDto;
import com.sellandsign.test.exception.DuplicateUserException;
import com.sellandsign.test.model.apikey.ApiKey;
import com.sellandsign.test.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for all users.
 */
@Service
public class UserService {
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;
	private final ApiKeyRepository apiKeyRepository;
	private Logger log = LoggerFactory.getLogger(RecipientService.class);

	public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, ApiKeyRepository apiKeyRepository) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userRepository = userRepository;
		this.apiKeyRepository = apiKeyRepository;
	}

	public Long saveUser(UserDto userDto) {
		Optional<User> user = userRepository.findByUsername(userDto.getUsername());
		if (user.isPresent()) {
			log.error("Username {} already exists", userDto.getUsername());
			throw new DuplicateUserException(userDto.getUsername());
		} else {
			userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
			return userRepository.save(userDto.toEntity()).getId();
		}
	}

	public List<User> listUsersExceptAdmin() {
		log.info("Fetching all users");
		return userRepository.findAllByRoleNot(User.Role.ADMIN);
	}

	public void updateUser(String userId, User.Role role) {
		log.info("Update userRole from userId : {} , with role : {}", userId, role);
		userRepository.updateUserRole(Long.valueOf(userId), role);
	}

	public UUID createApiKey() {
		ApiKey apiKey = apiKeyRepository.save(new ApiKey());
		log.info("New Api Key create : {}", apiKey.getKey());
		return apiKey.getKey();
	}

	public List<ApiKey> listApiKeys() {
		log.info("Fetching all api keys");
		return apiKeyRepository.findAll();
	}
}
