package com.sellandsign.test.repository.h2.user;

import com.sellandsign.test.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Classic user repository.
 */
public interface UserRepository extends JpaRepository<User, Long> {

	@Transactional
	@Modifying
	@Query("update User u set u.role = :role WHERE u.id = :id")
	void updateUserRole(@Param("id") Long id, @Param("role") User.Role role);

	List<User> findAllByRoleNot(User.Role role);

	Optional<User> findByUsername(String username);
}
