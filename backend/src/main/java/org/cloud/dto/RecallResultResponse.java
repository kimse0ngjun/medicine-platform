package org.cloud.dto;

import java.time.LocalDate;

import org.cloud.enums.DangerLevel;
import org.cloud.enums.RecallStatus;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecallResultResponse {
    private RecallStatus status;   
    private String message;
    private DangerLevel dangerLevel;
    private LocalDate expirationDate;
}
