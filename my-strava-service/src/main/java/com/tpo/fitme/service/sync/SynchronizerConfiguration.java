package com.tpo.fitme.service.sync;

import com.tpo.fitme.strava.client.rest.ActivityRestClient;
import com.tpo.fitme.strava.client.rest.AthleteRestClient;
import com.tpo.strava.persistence.service.ActivitiesService;
import com.tpo.strava.persistence.service.GearService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import static java.util.Arrays.asList;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

/**
 * @author Tiberiu
 * @since 17.07.18
 */
@Configuration
public class SynchronizerConfiguration {

    @Bean
    @Scope(scopeName = SCOPE_SINGLETON)
    public Synchronizer activitySynchronizer(ActivityRestClient activityRestClient,
                                             ActivitiesService activitiesService) {
        return new ActivitySynchronizer(activityRestClient, activitiesService);
    }

    @Bean
    @Scope(scopeName = SCOPE_SINGLETON)
    public Synchronizer athleteSynchronizer(AthleteRestClient athleteRestClient, GearService gearService) {
        return new AthleteSynchronizer(athleteRestClient, gearService);
    }


    @Bean
    @Scope(scopeName = SCOPE_SINGLETON)
    @Primary
    public Synchronizer groupSynchronizer(Synchronizer activitySynchronizer, Synchronizer athleteSynchronizer) {
        return new GroupSynchronizer(asList(activitySynchronizer, athleteSynchronizer));
    }
}
