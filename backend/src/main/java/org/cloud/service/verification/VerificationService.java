package org.cloud.service.verification;

import org.cloud.dto.recall.RecallResultResponse;
import org.cloud.dto.verification.VerificationRequest;
import org.cloud.dto.verification.VerificationResponse;
import org.cloud.entity.Verification;
import org.cloud.enums.VerificationStatus;
import org.cloud.repository.verficiation.VerificationRepository;
import org.cloud.service.recall.RecallService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationRepository repository;
    private final RecallService recallService;

    public Long start(VerificationRequest req) {

        Verification v = new Verification();

        v.setStatus(VerificationStatus.PENDING);
        v.setInputText(req.getInputText());
        v.setLotNumber(req.getLotNumber());

        repository.save(v);

        processAsync(v.getId(), req);

        return v.getId();
    }

    @Async
    @Transactional
    public void processAsync(Long id, VerificationRequest req) {

        Verification v = repository.findById(id)
                .orElseThrow();

        v.setStatus(VerificationStatus.PROCESSING);

        try {

            RecallResultResponse result =
                    recallService.checkByLot(req.getLotNumber());

            v.setStatus(VerificationStatus.SUCCESS);

            v.setResult(
                    result.getStatus() + ":" +
                    result.getRecallReason()
            );

        } catch (Exception e) {

            v.setStatus(VerificationStatus.FAIL);

            v.setResult(
                    "검증 처리 중 오류가 발생했습니다."
            );
        }
    }

    public VerificationResponse get(Long id) {

        Verification v = repository.findById(id)
                .orElseThrow();

        VerificationResponse res =
                new VerificationResponse();

        res.setId(v.getId());
        res.setStatus(v.getStatus());

        switch (v.getStatus()) {

            case PENDING ->
                    res.setMessage(
                            "검증 요청이 접수되었습니다."
                    );

            case PROCESSING ->
                    res.setMessage(
                            "OCR 분석 진행 중입니다."
                    );

            case SUCCESS ->
                    res.setMessage(
                            "리콜 조회가 완료되었습니다."
                    );

            case FAIL ->
                    res.setMessage(
                            "검증 처리에 실패했습니다."
                    );
        }

        if (v.getStatus() == VerificationStatus.SUCCESS) {

            RecallResultResponse result =
                    recallService.checkByLot(
                            v.getLotNumber()
                    );

            res.setRecallResult(result);
        }

        return res;
    }
}