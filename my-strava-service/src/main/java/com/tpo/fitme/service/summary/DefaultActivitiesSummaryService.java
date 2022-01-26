package com.tpo.fitme.service.summary;

import com.tpo.fitme.domain.activity.Activity;
import com.tpo.fitme.domain.summary.ActivitiesSummary;
import com.tpo.fitme.domain.summary.Summary;
import com.tpo.strava.persistence.service.ActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.tpo.fitme.service.constants.Constants.*;
import static com.tpo.fitme.service.utils.StupidMath.divide;

/**
 * @author Tiberiu
 * @since 05.06.18
 */
@Service
public class DefaultActivitiesSummaryService implements ActivitiesSummaryService {

    private final ActivitiesService activitiesService;

    @Autowired
    public DefaultActivitiesSummaryService(ActivitiesService activitiesService) {
        this.activitiesService = activitiesService;
    }

    @Override
    public Summary generateSummary(Long athleteId) {
        return generateSummarySince(athleteId, Duration.ZERO);
    }

    @Override
    public Summary generateSummarySince(Long athleteId, Duration duration) {
        List<ActivitiesSummary> activitiesSummaries = Collections.emptyList();
        List<Activity> activities;
        if (duration.equals(Duration.ZERO)) {
            activities = activitiesService.findAll(athleteId);
        } else {
            activities = activitiesService.findAllForTheLast(athleteId, duration);
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
                        summary.setElevation(summary.getElevation() + activity.getElevation());
                    }
                }
            }
        }
        return new Summary(activitiesSummaries);
    }

    @Override
    public float getTripsAroundTheWorld(Long athleteId) {
        return divide(generateSummary(athleteId).getTotalKm(), ECUATOR_LENGTH_KM);
    }

    @Override
    public float getTripsToTheMoon(Long athleteId) {
        return divide(generateSummary(athleteId).getTotalKm(), DISTANCE_TO_THE_MOON_KM);
    }

    @Override
    public long getBeersBurned(Long athleteId) {
        return Math.round(divide(generateSummary(athleteId).getTotalCalories(), CALORIES_PER_BEER));
    }

    @Override
    public long getTimesClimbedEverest(Long athleteId) {
        return Math.round(divide(generateSummary(athleteId).getTotalElevation(), HEIGHT_EVEREST));
    }

    @Override
    public long getBurgerBurned(Long athleteId) {
        return Math.round(divide(generateSummary(athleteId).getTotalCalories(), CALORIES_PER_BURGER));
    }

    @Override
    public int getActiveDays(Long athleteId, int year) {
        List<Activity> activities = activitiesService.findAllByYear(athleteId, year);
        int result = 0;
        int day = 0;
        for (Activity activity : activities) {
            if (activity.getStartDate().getDayOfMonth() != day) {
                result++;
                day = activity.getStartDate().getDayOfMonth();
            }
        }
        return result;
    }

    @Override
    public int getActiveDays(Long athleteId, Month month) {
        List<Activity> activities = activitiesService.findAllByMonth(athleteId, month);
        int result = 0;
        int day = 0;
        for (Activity activity : activities) {
            if (activity.getStartDate().getDayOfMonth() != day) {
                result++;
                day = activity.getStartDate().getDayOfMonth();
            }
        }
        return result;
    }


    private List<ActivitiesSummary> generateEmptySummaries(int startYear) {
        ArrayList<ActivitiesSummary> resultList = new ArrayList<>();

        int currentYear = LocalDateTime.now().getYear();
        int currentMonth = LocalDateTime.now().getMonthValue();

        for (int year = startYear; year <= currentYear; year++)
            for (int month = 1; month <= 12; month++) {
                ActivitiesSummary newSummary = new ActivitiesSummary();
                newSummary.setMonth(month);
                newSummary.setYear(year);
                newSummary.setDateTime(LocalDateTime.of(year, month, 1, 0, 0, 0));
                resultList.add(newSummary);
                if (year == currentYear && month == currentMonth) {
                    break;
                }
            }
        return resultList;
    }

}
