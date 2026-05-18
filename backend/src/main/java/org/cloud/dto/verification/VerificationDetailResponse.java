package org.cloud.dto.verification;

import java.time.LocalDateTime;

import org.cloud.dto.recall.RecallResultResponse;
import org.cloud.entity.Verification;
import org.cloud.enums.VerificationStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificationDetailResponse {

    private Long id;

    private VerificationStatus status;

    private String message;

    private String inputText;
    private String result;
    private String errorMessage;

    private String lotNumber;

    private LocalDateTime createdAt;

    private RecallResultResponse recallResult;

    public static VerificationDetailResponse from(Verification entity) {

        VerificationDetailResponse res = new VerificationDetailResponse();

        res.setId(entity.getId());
        res.setStatus(entity.getStatus());

        res.setMessage(
                entity.getErrorMessage() != null
                        ? entity.getErrorMessage()
                        : entity.getResult()
        );

        res.setInputText(entity.getInputText());
        res.setResult(entity.getResult());
        res.setErrorMessage(entity.getErrorMessage());

        res.setLotNumber(entity.getLotNumber());
        res.setCreatedAt(entity.getCreatedAt());

        return res;
    }
}