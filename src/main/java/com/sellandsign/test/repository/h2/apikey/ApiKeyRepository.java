package com.sellandsign.test.repository.h2.apikey;

import com.sellandsign.test.model.apikey.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * ApiKey user repository.
 */
@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
	Optional<ApiKey> findByKey(UUID key);
}
