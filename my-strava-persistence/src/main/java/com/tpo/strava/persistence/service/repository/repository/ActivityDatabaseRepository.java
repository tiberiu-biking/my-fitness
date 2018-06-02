package com.tpo.strava.persistence.service.repository.repository;

import com.tpo.fitness.domain.Athlete;
import com.tpo.fitness.domain.activity.Activity;
import com.tpo.strava.persistence.entities.ActivityEntity;
import com.tpo.strava.persistence.repository.ActivityJpaRepository;
import com.tpo.strava.persistence.service.mapper.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tiberiu
 * @since 06.10.17
 */
@Service
public class ActivityDatabaseRepository implements ActivityRepository {

    private final ActivityJpaRepository activityJpaRepository;
    private final Translator<ActivityEntity, Activity> activityEntityTranslator;

    @Autowired
    public ActivityDatabaseRepository(ActivityJpaRepository activityJpaRepository,
                                      Translator<ActivityEntity, Activity> activityEntityTranslator) {
        this.activityJpaRepository = activityJpaRepository;
        this.activityEntityTranslator = activityEntityTranslator;
    }

    @Override
    public void save(Activity activity) {
        ActivityEntity activityEntity = activityEntityTranslator.from(activity);
        activityEntity.setInsertDate(Instant.now().getEpochSecond());
        activityJpaRepository.save(activityEntity);
    }

    @Override
    public List<Activity> getAll() {
        List<ActivityEntity> activityEntities = activityJpaRepository.findAll();
        return activityEntities.parallelStream()
                .map(activityEntityTranslator::to)
                .collect(Collectors.toList());
    }

    @Override
    public List<Activity> getAllInChronologicalOrder() {
        return activityJpaRepository.findAllByOrderByStartDateDesc()
                .stream()
                .map(activityEntityTranslator::to)
                .collect(Collectors.toList());
    }

    @Override
    public Activity findFirstByOrderByInsertDateDesc(Athlete athlete) {
        return activityEntityTranslator.to(activityJpaRepository.findFirstByAthleteIdOrderByStartDateDesc(athlete));
    }

    @Override
    public Long getLastStartDateByAthlete(Athlete athlete) {
        ActivityEntity lastActivity = activityJpaRepository.findFirstByAthleteIdOrderByStartDateDesc(athlete);
        if (lastActivity != null) {
            return lastActivity.getStartDate().toInstant().getEpochSecond();
        } else {
            return 0L;
        }
    }
}
