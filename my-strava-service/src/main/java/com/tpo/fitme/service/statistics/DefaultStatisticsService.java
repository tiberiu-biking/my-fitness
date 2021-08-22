package com.tpo.fitme.service.statistics;

import com.tpo.fitme.domain.Sport;
import com.tpo.fitme.domain.activity.Activity;
import com.tpo.strava.persistence.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;

/**
 * @author Tiberiu
 * @since 19.06.18
 */
@Service
public class DefaultStatisticsService implements StatisticsService {

    private final ActivityService activityService;

    @Autowired
    public DefaultStatisticsService(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public float getTotalDistance(Long athleteId, Sport sport) {
        return activityService.findAllBySport(athleteId, sport)
                .stream()
                .map(Activity::getDistance)
                .reduce(0f, Float::sum)
                .longValue();
    }

    @Override
    public float getTotalElevation(Long athleteId, Month month) {
        return activityService.findAllByMonth(athleteId, month)
                .stream()
                .map(Activity::getElevation)
                .reduce(0f, Float::sum)
                .longValue();
    }

    @Override
    public float getTotalDistance(Long athleteId, Month month) {
        return activityService.findAllByMonth(athleteId, month)
                .stream()
                .map(Activity::getDistance)
                .reduce(0f, Float::sum)
                .longValue();
    }

    @Override
    public float getTotalDistance(Long athleteId, Sport sport, int year) {
        return activityService.findAllBySportAndYear(athleteId, sport, year)
                .stream()
                .map(Activity::getDistance)
                .reduce(0f, Float::sum)
                .longValue();
    }

    @Override
    public long getTotalDuration(Long athleteId, Month month) {
        return activityService.findAllByMonth(athleteId, month)
                .stream()
                .map(Activity::getDuration)
                .reduce(0L, Long::sum);
    }

    @Override
    public long getTotalDuration(Long athleteId, Sport sport, int year) {
        return activityService.findAllBySportAndYear(athleteId, sport, year)
                .stream()
                .map(Activity::getDuration)
                .reduce(0L, Long::sum);
    }

    @Override
    public long getTotalDuration(Long athleteId, Sport sport) {
        return activityService.findAllBySport(athleteId, sport)
                .stream()
                .map(Activity::getDuration)
                .reduce(0L, Long::sum);
    }
}
