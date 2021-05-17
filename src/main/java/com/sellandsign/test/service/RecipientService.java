package com.sellandsign.test.service;

import com.sellandsign.test.repository.recipient.RecipientRepository;
import com.sellandsign.test.model.recipients.Recipient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for recipients.
 */
@Service
public class RecipientService {

	private final RecipientRepository recipientRepository;
	Logger log = LoggerFactory.getLogger(RecipientService.class);

	public RecipientService(RecipientRepository recipientRepository) {
		this.recipientRepository = recipientRepository;
	}

	public List<Recipient> getRecipients() {
		log.info("Fetching recipients");
		return recipientRepository.findAll();
	}

	public Optional<Recipient> getRecipientByEmail(String email) {
		log.info("Fetching recipient with email : {}", email);
		return recipientRepository.findFirstByEmail(email);
	}

	public Long addRecipient(Recipient recipient) {
		Recipient savedRecipient = recipientRepository.save(recipient);
		log.info("Saving recipient : {}", recipient);
		return savedRecipient.getId();
	}
}
