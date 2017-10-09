package com.tpo.fitness.providers.api.service;

import com.tpo.fitness.domain.Athlete;

/**
 * A REST client for retrieving activities from an activity provider.
 *
 * @author Tiberiu
 * @since 08.10.17
 */
public interface AthleteRestClient {
    Athlete getAthlete();
}
