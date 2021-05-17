package com.sellandsign.test.repository.recipient;

import com.sellandsign.test.model.recipients.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Recipients repository.
 */
public interface RecipientRepository extends JpaRepository<Recipient, Long> {
	Optional<Recipient> findByEmail(String email);
}
