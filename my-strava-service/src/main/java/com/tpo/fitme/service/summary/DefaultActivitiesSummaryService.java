package com.tpo.fitme.service.summary;

import com.tpo.fitme.domain.activity.Activity;
import com.tpo.fitme.domain.summary.ActivitiesSummary;
import com.tpo.fitme.domain.summary.Summary;
import com.tpo.strava.persistence.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.tpo.fitme.service.constants.Constants.*;

/**
 * @author Tiberiu
 * @since 05.06.18
 */
@Service
public class DefaultActivitiesSummaryService implements ActivitiesSummaryService {

    private final ActivityService activityService;

    @Autowired
    public DefaultActivitiesSummaryService(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public Summary generateSummary() {
        return generateSummarySince(Duration.ZERO);
    }

    @Override
    public Summary generateSummarySince(Duration duration) {
        List<ActivitiesSummary> activitiesSummaries = Collections.emptyList();
        List<Activity> activities;
        if (duration.equals(Duration.ZERO)) {
            activities = activityService.findAll();
        } else {
            activities = activityService.findAllForTheLast(duration);
        }
        if (!activities.isEmpty()) {
            LocalDateTime firstStartDate = activities.get(0).getStartDate();
            activitiesSummaries = generateEmptySummaries(firstStartDate.getYear());

            for (Activity activity : activities) {
                LocalDateTime startDate = activity.getStartDate();

                for (ActivitiesSummary summary : activitiesSummaries) {
                    if ((summary.getMonth() == startDate.getMonthValue()) &&
                            (summary.getYear() == startDate.getYear())) {
                        summary.setCalories(summary.getCalories() + activity.getCalories());
                        summary.setDistance(summary.getDistance() + activity.getDistance());
                    }
                }
            }
        }
        return new Summary(activitiesSummaries);
    }

    @Override
    public float getTripsAroundTheWorld() {
        return divide(generateSummary().getTotalKm(), ECUATOR_LENGTH_KM);
    }

    @Override
    public float getTripsToTheMoon() {
        return divide(generateSummary().getTotalKm(), DISTANCE_TO_THE_MOON_KM);
    }

    @Override
    public long getBeersBurned() {
        return Math.round(divide(generateSummary().getTotalCalories(), CALORIES_PER_BEER));
    }

    @Override
    public long getBurgerBurned() {
        return Math.round(divide(generateSummary().getTotalCalories(), CALORIES_PER_BURGER));
    }

    private float divide(float number, float by) {
        float i = number / by;
        return (float) Math.round(i * 100000) / 100000;
    }

    private List<ActivitiesSummary> generateEmptySummaries(int startYear) {
        ArrayList<ActivitiesSummary> resultList = new ArrayList<>();

        int currentYear = LocalDateTime.now().getYear();
        int currentMonth = LocalDateTime.now().getMonthValue();

        for (int year = startYear; year <= currentYear; year++)
            for (int month = 1; month <= currentMonth; month++) {
                ActivitiesSummary newSummary = new ActivitiesSummary();
                newSummary.setMonth(month);
                newSummary.setYear(year);
                newSummary.setDateTime(LocalDateTime.of(year, month, 1, 0, 0, 0));
                resultList.add(newSummary);
            }
        return resultList;
    }

}
