package com.tpo.fitme.service.sync;

import com.tpo.fitme.domain.Athlete;
import com.tpo.fitme.domain.activity.Activity;
import com.tpo.fitme.strava.client.rest.ActivityRestClient;
import com.tpo.strava.persistence.service.ActivitiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Tiberiu
 * @since 04.10.17
 */
@Slf4j
public class ActivitySynchronizer implements Synchronizer {

    private final ActivityRestClient activityRestClient;
    private final ActivitiesService activitiesService;
    private final AtomicBoolean isInProgress;

    @Autowired
    public ActivitySynchronizer(ActivityRestClient activityRestClient,
                                ActivitiesService activitiesService) {
        this.activityRestClient = activityRestClient;
        this.activitiesService = activitiesService;
        isInProgress = new AtomicBoolean(false);
    }

    @Async
    public void sync(Athlete athlete) {
        if (isInProgress.get()) {
            log.info("Sync in progress, exiting!");
        } else {
            try {
                isInProgress.set(true);
                log.info("Starting sync athlete {} activities...", athlete.getId());
                LocalDateTime lastStartDate = activitiesService.getLastStartDateByAthlete(athlete.getId());

                List<Activity> activities = activityRestClient.getAllAfter(athlete, lastStartDate);

                log.info("Found {} new activities", activities.size());

                activities.stream()
                        .filter(activity -> !activitiesService.findByExternalId(athlete.getId(), activity.getExternalId()).isPresent())
                        .map(activity -> activityRestClient.getOne(athlete, activity.getExternalId()))
                        .forEach(this::persistActivity);

                log.info("Sync done!");
            } finally {
                isInProgress.set(false);
            }
        }
    }

    private void persistActivity(Activity activity) {
        Activity newActivity = activitiesService.save(activity);
        log.info("Persisted activity {} with id {}", newActivity.getExternalId(), newActivity.getId());
    }
}
