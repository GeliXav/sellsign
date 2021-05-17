package com.sellandsign.test.controller.web;

import com.sellandsign.test.dto.UserDto;
import com.sellandsign.test.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Web controller for signup
 */
@Controller
@RequestMapping("/signup")
public class SignupController {
	private final UserService userService;

	public SignupController(UserService userService) {
		this.userService = userService;
	}

	@ModelAttribute("user")
	public UserDto userRegistrationDto() {
		return new UserDto();
	}

	@PostMapping
	public String signUp(@Valid @ModelAttribute UserDto userDto) {
		userService.saveUser(userDto);
		return "redirect:/signup?success";
	}

	@GetMapping
	public String showRegistrationForm() {
		return "signup";
	}

	@PostMapping("/apikey")
	public String signUpApiKey() {
		UUID key = userService.createApiKey();
		return "redirect:/user?key=" + key;
	}
}
