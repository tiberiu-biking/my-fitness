package com.tpo.fitme.strava.client.rest.actitivity;

import com.tpo.fitme.domain.Sport;
import com.tpo.fitme.domain.WorkoutType;
import lombok.experimental.UtilityClass;
import lombok.val;

import static com.tpo.fitme.domain.Sport.*;

/**
 * @author Tiberiu
 * @since 21.06.18
 */
@UtilityClass
class StravaSportTranslator {

    static Sport translate(StravaActivity stravaActivity) {
        Sport result = Sport.valueOf(stravaActivity.getType().toUpperCase());

        if (RIDE.equals(result)) {
            return translateRide(stravaActivity);
        } else if (WORKOUT.equals(result)) {
            return translateWorkout(result, stravaActivity.getName());
        } else {
            return result;
        }
    }

    private static Sport translateWorkout(Sport sport, String activityName) {
        Sport result = sport;
        if (activityName != null) {
            activityName = activityName.toUpperCase();
            if ((activityName.contains("SOCCER") || activityName.contains("FOOTBALL"))) {
                result = SOCCER;
            } else if (activityName.contains("TRX")) {
                result = TRX;
            } else if (activityName.contains("TT") || activityName.contains("Table Tennis") || activityName.contains("Tischtennis")) {
                result = TABLETENNIS;
            } else if ((activityName.contains("HIIT"))) {
                result = HIIT;
            }
        }
        return result;
    }

    private static Sport translateRide(StravaActivity stravaActivity) {
        if (stravaActivity.isCommute())
            return COMMUTE;

        val workoutType = stravaActivity.getWorkoutType();
        if (workoutType != null && WorkoutType.RACE.getValue() == workoutType) {
            return ROAD;
        } else {
            return GRAVEL;
        }
    }
}
