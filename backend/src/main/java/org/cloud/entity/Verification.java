package org.cloud.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "verification")
@Getter @Setter
public class Verification {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String status;
	
	@Lob
	private String inputText;
	
	private String lotNumber;
	
	@Lob
	private String result;
	
	@Lob
	private String errorMessage;
	
	private LocalDateTime createdAt;
}
