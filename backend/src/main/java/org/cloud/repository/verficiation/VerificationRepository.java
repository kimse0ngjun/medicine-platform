package org.cloud.repository.verficiation;

import java.util.List;

import org.cloud.entity.User;
import org.cloud.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
	 List<Verification> findByUser(User user);
}
