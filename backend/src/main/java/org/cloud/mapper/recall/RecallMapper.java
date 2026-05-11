package org.cloud.mapper.recall;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cloud.entity.RecallBatch;

@Mapper
public interface RecallMapper {

//	String findRecallResponse(@Param("lotNumber") String lotNumber);
	RecallBatch findByLotNumber(@Param("lotNumber") String lotNumber);
}
