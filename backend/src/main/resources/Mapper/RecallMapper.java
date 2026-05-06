package Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecallMapper {

	String findRecallResponse(@Param("lotNumber") String lotNumber);
}
