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
        try {
            activity.setExternalId(stravaActivity.getId());
            activity.setAthleteId(stravaActivity.getAthlete().getId());
            activity.setName(stravaActivity.getName());
            activity.setStartDate(stravaActivity.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            activity.setCalories(stravaActivity.getCalories());
            activity.setDistance(stravaActivity.getDistance() / 1000);
            activity.setDuration(stravaActivity.getMovingTime() / 60);
            activity.setSport(StravaSportTranslator.translate(stravaActivity));
            activity.setElevation(stravaActivity.getTotalElevationGain());
            activity.setCalories(Calories.calculate(stravaActivity.getCalories(), activity.getDuration(), activity.getSport()));
            activity.setCommute(stravaActivity.isCommute());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activity;
    }

}
