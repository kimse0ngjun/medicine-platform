package org.cloud.repository.recall;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.cloud.dto.recall.RecallDetailResponse;
import org.cloud.dto.recall.RecallSearchResponse;
import org.cloud.entity.RecallBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecallRepository extends JpaRepository<RecallBatch, Long> {

	@Query("""
			SELECT new org.cloud.dto.recall.RecallSearchResponse(
			    m.productName,
			    m.recallObligator,
			    COUNT(rb),
			    'CHECK_REQUIRED'
			)
			FROM RecallBatch rb
			JOIN rb.medicine m
			WHERE m.productName LIKE %:productName%
			GROUP BY m.productName, m.recallObligator
			""")
			List<RecallSearchResponse> searchByProductName(
			        @Param("productName") String productName
			);
	
	@Query("""
		    SELECT rb
		    FROM RecallBatch rb
		    WHERE rb.lotNumber = :lotNumber
		    ORDER BY rb.recallDate DESC
		""")
		Page<RecallBatch> findByLotNumber(
		    @Param("lotNumber") String lotNumber,
		    Pageable pageable
		);
	
	@Query("""
		    SELECT new org.cloud.dto.recall.RecallDetailResponse(
		        rb.lotNumber,
		        m.productName,
		        rb.recallReason,
		        CAST(rb.dangerLevel AS string),
		        rb.recallDate,
		        rb.expirationDate
		    )
		    FROM RecallBatch rb
		    JOIN rb.medicine m
		    WHERE m.productName LIKE %:productName%
		    ORDER BY rb.recallDate DESC
		""")
		List<RecallDetailResponse> findRecallDetailByProductName(
		        @Param("productName") String productName
		);
}