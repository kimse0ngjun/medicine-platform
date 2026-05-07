package org.cloud.service;

import org.cloud.dto.RecallResultResponse;
import org.cloud.entity.RecallBatch;
import org.cloud.mapper.RecallMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecallService {

    private final RecallMapper recallMapper;

    public RecallResultResponse checkRecall(String lotNumber) {

        RecallBatch reason = recallMapper.findByLotNumber(lotNumber);

        RecallResultResponse result = new RecallResultResponse();

        if (reason == null) {
            result.setStatus("SAFE");
            result.setMessage("정상 의약품");
            return result;
        }

        result.setDangerLevel(reason.getDangerLevel());
        result.setExpirationDate(reason.getExpirationDate());

        if (reason.getExpirationDate() != null &&
            reason.getExpirationDate().isBefore(java.time.LocalDate.now())) {

            result.setStatus("RECALL");
            result.setMessage("즉시 회수 대상: 유통기한 초과");
            return result;
        }

        int level = reason.getDangerLevel();

        if (level == 3) {
            result.setStatus("RECALL");
            result.setMessage(reason.getRecallReason());
        } else if (level == 2) {
            result.setStatus("WARNING");
            result.setMessage(reason.getRecallReason());
        } else if (level == 1) {
            result.setStatus("SAFE");
            result.setMessage("회수 이력 있음(낮은 위험도)");
        } else {
            result.setStatus("UNKNOWN");
            result.setMessage("데이터 존재하지만 판단 불가");
        }

        return result;
    }
}
