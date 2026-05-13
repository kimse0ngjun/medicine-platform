package org.cloud.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "medicine")
@Getter @Setter
public class Medicine {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long medicineId;
	
	@Column(unique = true, nullable = false)
	private String stardardCode;
	
	private String productName;
	
	private String recallObligator;
	
	private Boolean isCleansed;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updateAt;
	
	@OneToMany(mappedBy = "medicine")
	private List<UserMedicine> userMedicines;
	
	@OneToMany(mappedBy = "medicine")
	private List<RecallBatch> recallBatches;
	
	@OneToMany(mappedBy = "medicine")
	private List<SideEffect> sideEffects;
}
