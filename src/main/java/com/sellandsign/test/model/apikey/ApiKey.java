package com.sellandsign.test.model.apikey;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

/**
 * Entity class  for ApiKey user.
 */
@Entity
@Getter
public class ApiKey {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private UUID key;

	public ApiKey() {
		key = UUID.randomUUID();
	}
}
