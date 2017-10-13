package com.tpo.strava.persistence;

import com.tpo.fitness.domain.activity.Activity;
import com.tpo.strava.persistence.entities.ActivityEntity;
import com.tpo.strava.persistence.repository.ActivityJpaRepository;
import com.tpo.strava.persistence.service.mapper.Translator;
import com.tpo.strava.persistence.service.mapper.activitiy.ActivityEntityTranslator;
import com.tpo.strava.persistence.service.repository.repository.ActivityDatabaseRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Tiberiu on 23/10/15.
 */
@Configuration
@EnableTransactionManagement
@EntityScan(basePackageClasses = ActivityEntity.class)
@EnableJpaRepositories(basePackageClasses = ActivityJpaRepository.class)
public class PersistenceConfiguration {

    @Bean
    public Translator<ActivityEntity, Activity> activityEntityTranslator() {
        return new ActivityEntityTranslator();
    }

    @Bean
    public ActivityDatabaseRepository activityDatabaseRepository(ActivityJpaRepository activityJpaRepository,
                                                                 Translator<ActivityEntity, Activity> activityEntityTranslator) {
        return new ActivityDatabaseRepository(activityJpaRepository, activityEntityTranslator);
    }
}
