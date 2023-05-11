package com.posod.kafkaadminserver.dto.infiRay.dsd;

import com.posod.kafkaadminserver.dto.infiRay.types.Point;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TempData {

    private boolean Enable;
    private int ID;
    private Point MaxTempPoint;
    private Point MinTempPoint;
    private float TempAvg;
    private float TempCenter;
    private float TempMax;
    private float TempMin;
    private float TempRangeMax;
    private float TempRangeMin;
}
