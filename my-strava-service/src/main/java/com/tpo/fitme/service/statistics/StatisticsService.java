package com.tpo.fitme.service.statistics;

import com.tpo.fitme.domain.Sport;

import java.time.Month;

/**
 * @author Tiberiu
 * @since 19.06.18
 */
public interface StatisticsService {

    float getTotalDistance(Long athleteId, Month month);

    float getTotalDistance(Long athleteId, Sport sport);

    float getTotalDistance(Long athleteId, Sport sport, int year);

    long getTotalDuration(Long athleteId, Month month);

    long getTotalDuration(Long athleteId, Sport sport, int year);

    long getTotalDuration(Long athleteId, Sport sport);
}
