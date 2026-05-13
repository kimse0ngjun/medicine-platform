package org.cloud.repository.recall;

import java.util.Optional;
import org.cloud.entity.RecallBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecallRepository extends JpaRepository<RecallBatch, Long> {

    @Query("SELECT rb FROM RecallBatch rb JOIN FETCH rb.medicine " +
           "WHERE rb.lotNumber = :lotNumber")
    Optional<RecallBatch> findByLotNumberWithMedicine(@Param("lotNumber") String lotNumber);
}