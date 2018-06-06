package com.tpo.fitness.providers.api.service;


import com.tpo.fitness.domain.Athlete;
import com.tpo.fitness.domain.activity.Activity;

import java.util.List;

/**
 * A REST client for retrieving activities from an activity provider.
 *
 * @author Tiberiu
 * @since 08.10.17
 */
public interface ActivityRestClient {

    List<Activity> findAll(Athlete athlete);

    List<Activity> findAllAfter(Athlete athlete, long after);

    Activity getOne(Athlete athlete, String activityId);
}
