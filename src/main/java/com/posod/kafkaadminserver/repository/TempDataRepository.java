package com.posod.kafkaadminserver.repository;

import com.posod.kafkaadminserver.dto.infiRay.dsd.TempData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TempDataRepository {

    void save(TempData tempData);
}
