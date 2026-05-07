package org.cloud.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecallResultResponse {
    private String status;   
    private String message;
    private Integer dangerLevel;
    private LocalDate expirationDate;
}
