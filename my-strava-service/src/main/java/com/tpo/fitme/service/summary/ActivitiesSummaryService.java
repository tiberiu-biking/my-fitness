package com.tpo.fitme.service.summary;

import com.tpo.fitme.domain.summary.Summary;

import java.time.Duration;
import java.time.Month;

/**
 * @author Tiberiu
 * @since 05.06.18
 */
public interface ActivitiesSummaryService {

    Summary generateSummary(Long athleteId);

    Summary generateSummarySince(Long athleteId, Duration duration);

    float getTripsAroundTheWorld(Long athleteId);

    float getTripsToTheMoon(Long athleteId);

    long getBeersBurned(Long athleteId);

    long getTimesClimbedEverest(Long athleteId);

    long getBurgerBurned(Long athleteId);

    int getActiveDays(Long athleteId, int year);

    int getActiveDays(Long athleteId, Month month);
}
