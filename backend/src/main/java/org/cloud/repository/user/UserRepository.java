package org.cloud.repository.user;

import java.util.Optional;

import org.cloud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);
	Optional<User> findByNameAndEmail(String name, String email);
}
