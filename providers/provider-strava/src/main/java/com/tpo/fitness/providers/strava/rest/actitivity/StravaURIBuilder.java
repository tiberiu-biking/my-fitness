package com.tpo.fitness.providers.strava.rest.actitivity;

import org.springframework.web.util.UriComponentsBuilder;

import static com.tpo.fitness.providers.strava.rest.StravaRestConstants.*;

/**
 * @author Tiberiu
 * @since 08.10.17
 */
public class StravaURIBuilder {

    public static String buildAthleteActivitiesByPageUrl(String accessToken, int page) {
        return UriComponentsBuilder
                .fromUriString(ATHLETE_ACTIVITIES_URL)
                .queryParam(ACCESS_TOKEN, accessToken)
                .queryParam(PAGE, page)
                .toUriString();
    }

    public static String buildAthleteActivitiesAfterUrl(String accessToken, long after, int page) {
        return UriComponentsBuilder
                .fromUriString(ATHLETE_ACTIVITIES_URL)
                .queryParam(ACCESS_TOKEN, accessToken)
                .queryParam(AFTER, after)
                .queryParam(PAGE, page)
                .toUriString();
    }

    public static String buildActivityDetailsURL(String accessToken, String activityId) {
        return UriComponentsBuilder
                .fromUriString(ACTIVITIES_URL)
                .queryParam(ACCESS_TOKEN, accessToken)
                .pathSegment(activityId)
                .toUriString();
    }

}
