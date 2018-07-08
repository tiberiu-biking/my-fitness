package com.tpo.fitme.service.statistics;

import com.tpo.fitme.domain.Sport;
import com.tpo.fitme.domain.activity.Activity;
import com.tpo.strava.persistence.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public float getTotalDistance(Sport sport) {
        return activityService.findAllBySport(sport)
                .stream()
                .map(Activity::getDistance)
                .reduce(0f, Float::sum)
                .longValue();
    }

    @Override
    public float getTotalDistance(Sport sport, int year) {
        return activityService.findAllBySportAndYear(sport, year)
                .stream()
                .map(Activity::getDistance)
                .reduce(0f, Float::sum)
                .longValue();
    }
}
