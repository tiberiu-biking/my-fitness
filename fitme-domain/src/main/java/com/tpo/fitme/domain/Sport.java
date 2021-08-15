package com.tpo.fitme.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Tiberiu
 * @since 19.06.18
 */
@Getter
public enum Sport {

    SOCCER("Soccer", Unit.MIN, 10),
    HIKE("Hiking", Unit.KM),
    ALPINESKI("Alpine Ski", Unit.KM, 7),
    ROAD("Road Cycling", Unit.KM),
    GRAVEL("Gravel", Unit.KM),
    WORKOUT("Workout", Unit.MIN),
    RIDE("Cycling", Unit.KM),
    COMMUTE("Commute", Unit.KM),
    RUN("Running", Unit.KM, 15),
    SWIM("Swimming", Unit.KM),
    WALK("Walking", Unit.KM),
    CROSSFIT("Crossfit", Unit.MIN),
    ICESKATE("Ice Skate", Unit.MIN),
    YOGA("Yoga", Unit.MIN),
    TRX("TRX", Unit.MIN),
    TABLETENNIS("Table Tennis", Unit.MIN),
    WEIGHTTRAINING("Weight Training", Unit.MIN),
    HIIT("HIIT", Unit.MIN);

    private final String caption;
    private final int caloriesPerMinute;
    private final Unit unit;

    Sport(String caption, Unit unit, int caloriesPerMinute) {
        this.caption = caption;
        this.unit = unit;
        this.caloriesPerMinute = caloriesPerMinute;
    }

    Sport(String caption, Unit unit) {
        this.caption = caption;
        this.unit = unit;
        this.caloriesPerMinute = 0;
    }


    @Getter
    @RequiredArgsConstructor
    public enum Unit {
        MIN("minutes"),
        KM("km");
        private final String unit;

    }
}
