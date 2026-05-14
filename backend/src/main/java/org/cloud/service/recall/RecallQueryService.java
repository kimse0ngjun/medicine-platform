package org.cloud.service.recall;

import java.util.List;

import org.cloud.dto.recall.RecallDetailResponse;
import org.cloud.dto.recall.RecallSearchResponse;
import org.cloud.entity.RecallBatch;
import org.cloud.repository.recall.RecallRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecallQueryService {

    private final RecallRepository repository;

    public Page<RecallBatch> findByLot(String lotNumber) {
    	
    	Pageable pageable = PageRequest.of(0,1);
    	
        return repository.findByLotNumber(lotNumber, pageable);
    }
    
    public List<RecallSearchResponse> findByProductName(String productName) {
        return repository.searchByProductName(productName);
    }
    
    public List<RecallDetailResponse> findRecallDetail(String productName) {
        return repository.findRecallDetailByProductName(productName);
    }
}