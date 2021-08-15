package com.tpo.strava.persistence.service;

import com.tpo.fitme.domain.Sport;
import com.tpo.fitme.domain.activity.Activity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

/**
 * @author Tiberiu
 * @since 06.10.17
 */
public interface ActivityService {

    Activity save(Activity activity);

    List<Activity> findAll(Long athleteId);

    List<Activity> findAllBySport(Long athleteId, Sport sport);

    List<Activity> findAllBySportAndYear(Long athleteId, Sport sport, int year);

    List<Activity> findAllByYear(Long athleteId, int year);

    List<Activity> findAllByMonth(Long athleteId, Month month);

    Optional<Activity> findByExternalId(Long athleteId, String externalId);

    List<Activity> findAllForTheLast(Long athleteId, Duration duration);

    List<Activity> findAllInChronologicalOrder(Long athleteId);

    Activity findFirstByOrderByInsertDateDesc(Long athleteId);

    LocalDateTime getLastStartDateByAthlete(Long athleteId);
}
