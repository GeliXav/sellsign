package com.sellandsign.test.controller.web;

import com.sellandsign.test.dto.RecipientDTO;
import com.sellandsign.test.service.RecipientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Web controller for recipients pages
 */
@Controller
@RequestMapping("/recipients")
public class RecipientWebController {

	private final RecipientService recipientService;

	public RecipientWebController(RecipientService recipientService) {
		this.recipientService = recipientService;
	}

	@ModelAttribute("recipient")
	public RecipientDTO userRegistrationDto() {
		return new RecipientDTO();
	}

	@GetMapping
	public String showAll(Model model) {
		model.addAttribute("recipients", recipientService.getRecipients());
		return "recipients";
	}
	@GetMapping("/add")
	public String recipientForm() {
		return "recipients-add";
	}
	@PostMapping()
	public String addRecipient(@ModelAttribute RecipientDTO recipientDTO) {
		recipientService.addRecipient(recipientDTO.toEntity());
		return "recipients-add";
	}
}
