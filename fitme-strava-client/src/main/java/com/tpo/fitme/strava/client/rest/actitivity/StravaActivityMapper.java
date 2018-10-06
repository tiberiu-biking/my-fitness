package com.tpo.fitme.strava.client.rest.actitivity;

import com.tpo.fitme.domain.activity.Activity;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

/**
 * @author Tiberiu
 * @since 08.10.17
 */
@Component
class StravaActivityMapper {

    Activity map(StravaActivity stravaActivity) {
        Activity activity = new Activity();
        activity.setExternalId(stravaActivity.getId());
        activity.setAthleteId(stravaActivity.getAthlete().getId());
        activity.setName(stravaActivity.getName());
        activity.setStartDate(stravaActivity.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        activity.setCalories(stravaActivity.getCalories());

        activity.setDistance(stravaActivity.getDistance() / 1000);
        activity.setElevation(stravaActivity.getTotalElevationGain());
        activity.setCalories(stravaActivity.getCalories());
        activity.setCommute(stravaActivity.isCommute());
        activity.setDuration(stravaActivity.getMovingTime() / 60);
        activity.setSport(StravaSportTranslator.translate(stravaActivity.getType(), stravaActivity.getWorkoutType(), stravaActivity.getName()));
        return activity;
    }

}
