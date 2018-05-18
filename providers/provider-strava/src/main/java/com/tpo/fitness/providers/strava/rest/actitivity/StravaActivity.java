package com.tpo.fitness.providers.strava.rest.actitivity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tpo.fitness.domain.Athlete;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class StravaActivity {

    private String id;
    private int resource_state;
    private String external_id;
    private int upload_id;
    private Athlete athlete;
    private String name;
    private Float distance;
    @JsonProperty("moving_time")
    private Integer movingTime;
    @JsonProperty("elapsed_time")
    private Integer elapsedTime;
    @JsonProperty("total_elevation_gain")
    private Float total_elevation_gain;
    private String type;
    @JsonProperty("start_date")
    private Date startDate;
    @JsonProperty("start_date_local")
    private String startDateLocal;
    private String timezone;
    private String[] start_latlng;
    private String[] end_latlng;
    private String location_city;
    private String location_state;
    private int achievement_count;
    private int kudos_count;
    private int comment_count;
    private int athlete_count;
    private int photo_count;
    //    private Polyline map;
    private boolean trainer;
    private boolean commute;
    private boolean manual;
    @JsonProperty("private")
    private boolean isPrivate;
    private boolean flagged;
    private String gear_id;
    private Float average_speed;
    private Float max_speed;
    private Float average_cadence;
    private int average_temp;
    private Float average_watts;
    private Float kilojoules;
    private Float average_heartrate;
    private Float max_heartrate;
    private Float calories;
    private int truncated;
    private boolean has_kudoed;
//    private List<SegmentEf  fort> segment_efforts;
//    private List<SplitsMetric> splits_metric;
//    private List<SplitsStandard> splits_standard;
//    private List<SegmentEffort> best_efforts;
}