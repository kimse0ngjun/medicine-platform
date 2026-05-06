package org.cloud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_medicine")
@Getter @Setter
public class UserMedicine {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userMedicineId;
	
	private String lotNumber;
	
	private Boolean isActive;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "medicine_id")
	private Medicine medicine;
}
