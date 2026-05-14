package org.cloud.dto.recall;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecallDetailResponse {

    private String lotNumber;
    private String productName;
    private String recallReason;
    private String dangerLevel;
    private LocalDate recallDate;
    private LocalDate expirationDate;
}
