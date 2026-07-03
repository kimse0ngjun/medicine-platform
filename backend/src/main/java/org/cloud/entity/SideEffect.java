package org.cloud.entity;

import jakarta.persistence.Entity;
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
@Table(name = "side_effect")
@Getter @Setter
public class SideEffect {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long sideEffectId;
	
	@Lob
	private String recallResponseRaw;
	
	@ManyToOne
	@JoinColumn(name = "medicine_id")
	private Medicine medicine;
}
