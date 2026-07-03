package org.cloud.entity;

import java.time.LocalDateTime;

import org.cloud.enums.recall.RecallStatus;
import org.cloud.enums.verification.SearchType;
import org.cloud.enums.verification.VerificationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "verification")
@Getter @Setter
public class Verification {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
	
	@Enumerated(EnumType.STRING)
	private VerificationStatus verificationStatus;
	
	@Enumerated(EnumType.STRING)
    private RecallStatus recallStatus;
	
	@Lob
	private String inputText;
	
	private String lotNumber;
	
	@Lob
	private String result;
	
	@Lob
	private String errorMessage;

	private LocalDateTime createdAt;
	
	@PrePersist
	public void prePersist() {
	    this.createdAt = LocalDateTime.now();
	}
	
	@Enumerated(EnumType.STRING)
	private SearchType type;

}
