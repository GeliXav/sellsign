package com.sellandsign.test.controller.api.recipients;

import com.sellandsign.test.dto.RecipientDTO;
import com.sellandsign.test.exception.MailNotFoundException;
import com.sellandsign.test.model.recipients.Recipient;
import com.sellandsign.test.service.RecipientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for recipients
 */
@RestController
@RequestMapping("/v1/recipients")
@Tag(name = "Recipients", description = "Recipients endpoints")
public class RecipientController {

	private final RecipientService recipientService;

	public RecipientController(RecipientService recipientService) {
		this.recipientService = recipientService;
	}

	@GetMapping
	@Operation(summary = "Get all recipients", description = "Get all recipients.", tags = { "Recipients" })
	public List<Recipient> getRecipients() {
		return recipientService.getRecipients();
	}

	@GetMapping("/email")
	@Operation(summary = "Get recipient by email", description = "Get recipient by email", tags = { "Recipients" })
	public Recipient getRecipientByEmail(@RequestParam String email) {
		return recipientService.getRecipientByEmail(email).orElseThrow(() -> new MailNotFoundException(email));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Add recipient", description = "Append a new recipient", tags = { "Recipients" })
	public Long addRecipient(@RequestBody RecipientDTO recipient) {
		return recipientService.addRecipient(recipient.toEntity());
	}
}
