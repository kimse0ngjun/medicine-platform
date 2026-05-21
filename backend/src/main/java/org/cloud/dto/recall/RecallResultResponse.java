package org.cloud.dto.recall;

import java.time.LocalDate;

import org.cloud.enums.recall.DangerLevel;
import org.cloud.enums.recall.RecallStatus;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter // LOT, image
public class RecallResultResponse {
    private RecallStatus status;   
    private String productName;
    private String recallReason;
    private DangerLevel dangerLevel;
    private LocalDate expirationDate;
    private String lotNumber;
    private Long recallCount;
}
