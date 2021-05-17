package com.sellandsign.test.controller.web;

import com.sellandsign.test.model.user.User;
import com.sellandsign.test.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for all users.
 */
@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String showAll(Model model) {
		model.addAttribute("users", userService.listUsersExceptAdmin());
		model.addAttribute("apikeys", userService.listApiKeys());
		return "user";
	}

	@PostMapping("/update")
	public String updateUser(@RequestParam("id") String userId, @RequestParam("role") User.Role role) {
		role = role.equals(User.Role.READ_ONLY) ? User.Role.WRITE : User.Role.READ_ONLY;
		userService.updateUser(userId, role);
		return "redirect:/user";
	}


}
