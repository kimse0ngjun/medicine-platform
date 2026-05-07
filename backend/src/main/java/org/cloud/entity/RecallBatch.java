package org.cloud.entity;

import java.time.LocalDate;

import org.cloud.enums.DangerLevel;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "recall_batch")
@Getter @Setter
public class RecallBatch {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long recallBatchId;
	
	private String lotNumber;
	
	@Enumerated(EnumType.STRING)
	private DangerLevel dangerLevel;
	
	@Lob
	private String recallReason;
	
	private LocalDate expirationDate;
	
	@ManyToOne
	@JoinColumn(name = "medicine_id")
	private Medicine medicine;
}
