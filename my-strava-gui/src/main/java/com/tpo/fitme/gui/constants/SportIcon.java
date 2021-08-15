package com.tpo.fitme.gui.constants;

import com.tpo.fitme.domain.Sport;
import com.vaadin.server.ThemeResource;
import lombok.Getter;

/**
 * @author Tiberiu
 * @since 19.06.18
 */
@Getter
public enum SportIcon {

    SOCCER("img/soccer.png"),
    WALK("img/walk.png"),
    CROSSFIT("img/crossfit.png"),
    SWIM("img/swim.png"),
    WORKOUT("img/workout.png"),
    HIKE("img/hike.png"),
    ALPINESKI("img/ski.png"),
    ICESKATE("img/ice-skating.png"),
    GRAVEL("img/gravel.png"),
    RIDE("img/ride.png"),
    ROAD("img/road.png"),
    COMMUTE("img/commute.png"),
    RUN("img/run.png"),
    YOGA("img/yoga.png"),
    WEIGHTTRAINING("img/weightlifting.png"),
    TRX("img/trx.png"),
    TABLETENNIS("img/table-tennis.png"),
    HIIT("img/hiit.png"),
    SYNC("img/sync.png"),
    STRAVA("img/strava-black.png");

    private final ThemeResource resource;

    SportIcon(String themedResourceId) {
        resource = new ThemeResource(themedResourceId);
    }

    public static SportIcon forSport(Sport sport) {
        return SportIcon.valueOf(sport.name());
    }
}
