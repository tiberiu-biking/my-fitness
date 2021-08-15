package com.tpo.strava.persistence.service;

import com.tpo.fitme.domain.Sport;
import com.tpo.fitme.domain.activity.Activity;
import com.tpo.strava.persistence.entities.ActivityEntity;
import com.tpo.strava.persistence.repository.ActivityRepository;
import com.tpo.strava.persistence.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tpo.strava.persistence.service.utils.Calendar.*;

/**
 * @author Tiberiu
 * @since 06.10.17
 */
@Service
class DatabaseActivityService implements ActivityService {

    private static final LocalDateTime BEGINNING_OF_TIME = LocalDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault());
    private final ActivityRepository activityRepository;
    private final Mapper<ActivityEntity, Activity> activityEntityMapper;

    @Autowired
    public DatabaseActivityService(ActivityRepository activityRepository,
                                   Mapper<ActivityEntity, Activity> activityEntityMapper) {
        this.activityRepository = activityRepository;
        this.activityEntityMapper = activityEntityMapper;
    }

    @Override
    @Transactional
    public Activity save(Activity activity) {
        ActivityEntity activityEntity = activityEntityMapper.from(activity);
        activityEntity.setInsertDate(Instant.now().getEpochSecond());
        return activityEntityMapper.to(activityRepository.saveAndFlush(activityEntity));
    }

    @Override
    public List<Activity> findAll(Long athleteId) {
        List<ActivityEntity> activityEntities = activityRepository.findAllByAthleteId(athleteId);
        return activityEntities.parallelStream()
                .map(activityEntityMapper::to)
                .collect(Collectors.toList());
    }

    @Override
    public List<Activity> findAllBySport(Long athleteId, Sport sport) {
        List<ActivityEntity> activityEntities = activityRepository.findByAthleteIdAndSport(athleteId, sport);
        return activityEntities.parallelStream()
                .map(activityEntityMapper::to)
                .collect(Collectors.toList());
    }

    @Override
    public List<Activity> findAllBySportAndYear(Long athleteId, Sport sport, int year) {
        LocalDateTime startOfTheYear = startOfTheYear(year);
        LocalDateTime endOfTheYear = endOfTheYear(year);
        List<ActivityEntity> activityEntities = activityRepository.findByAthleteIdAndSportAndStartDateBetween(athleteId, sport, startOfTheYear, endOfTheYear);
        return activityEntities.parallelStream()
                .map(activityEntityMapper::to)
                .collect(Collectors.toList());
    }

    @Override
    public List<Activity> findAllByYear(Long athleteId, int year) {
        LocalDateTime startOfTheYear = startOfTheYear(year);
        LocalDateTime endOfTheYear = endOfTheYear(year);
        List<ActivityEntity> activityEntities = activityRepository.findByAthleteIdAndStartDateBetween(athleteId, startOfTheYear, endOfTheYear);
        return activityEntities.parallelStream()
                .map(activityEntityMapper::to)
                .collect(Collectors.toList());

    }

    @Override
    public List<Activity> findAllByMonth(Long athleteId, Month month) {
        LocalDateTime startOfTheMonth = startOfTheMonth(month);
        LocalDateTime endOfTheMonth = endOfTheMonth(month);
        List<ActivityEntity> activityEntities = activityRepository.findByAthleteIdAndStartDateBetween(athleteId, startOfTheMonth, endOfTheMonth);
        return activityEntities.parallelStream()
                .map(activityEntityMapper::to)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Activity> findByExternalId(Long athleteId, String id) {
        ActivityEntity activityEntity = activityRepository.findByAthleteIdAndExternalId(athleteId, id);
        if (activityEntity != null) {
            return Optional.of(activityEntityMapper.to(activityEntity));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Activity> findAllForTheLast(Long athleteId, Duration duration) {
        LocalDateTime after = LocalDateTime.now().minusNanos(duration.toNanos());
        List<ActivityEntity> activityEntities = activityRepository.findAllByAthleteIdAndStartDateAfter(athleteId, after);
        return activityEntities.parallelStream()
                .map(activityEntityMapper::to)
                .collect(Collectors.toList());
    }

    @Override
    public List<Activity> findAllInChronologicalOrder(Long athleteId) {
        return activityRepository.findAllByAthleteIdOrderByStartDateDesc(athleteId)
                .stream()
                .map(activityEntityMapper::to)
                .collect(Collectors.toList());
    }

    @Override
    public Activity findFirstByOrderByInsertDateDesc(Long athleteId) {
        return activityEntityMapper.to(activityRepository.findFirstByAthleteIdOrderByStartDateDesc(athleteId));
    }

    @Override
    public LocalDateTime getLastStartDateByAthlete(Long athleteId) {
        ActivityEntity lastActivity = activityRepository.findFirstByAthleteIdOrderByStartDateDesc(athleteId);
        if (lastActivity != null) {
            return lastActivity.getStartDate();
        } else {
            return BEGINNING_OF_TIME;
        }
    }
}
