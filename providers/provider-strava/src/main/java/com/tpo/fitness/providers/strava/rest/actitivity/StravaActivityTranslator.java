package com.tpo.fitness.providers.strava.rest.actitivity;

import com.tpo.fitness.domain.activity.Activity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tiberiu
 * @since 08.10.17
 */
public class StravaActivityTranslator {

    public static List<Activity> translate(List<StravaActivity> stravaActivities) {
        return stravaActivities.parallelStream()
                .map(StravaActivityTranslator::translate)
                .collect(Collectors.toList());
    }

    public static Activity translate(StravaActivity stravaActivity) {
        Activity activity = new Activity();
        activity.setExternalId(stravaActivity.getId());
        activity.setAthleteId(stravaActivity.getAthlete().getId());
        activity.setName(stravaActivity.getName());
        activity.setStartDate(stravaActivity.getStartDate());
        activity.setCalories(stravaActivity.getCalories());

        activity.setDistance(stravaActivity.getDistance() / 1000);
        activity.setElevation(stravaActivity.getTotal_elevation_gain());
        activity.setCalories(stravaActivity.getCalories());
        activity.setCommute(stravaActivity.isCommute());
        activity.setDuration(stravaActivity.getMovingTime() / 60);
        activity.setType(stravaActivity.getType());
        return activity;
    }
}
