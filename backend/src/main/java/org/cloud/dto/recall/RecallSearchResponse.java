package org.cloud.dto.recall;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
//@Setter // JPQL constructor projection 사용하기 위함
@AllArgsConstructor
public class RecallSearchResponse { 

	private String productName;
	
	private String recallObligator;
	
	private Long recallCount;
	
	private String highestSeverity;
}
