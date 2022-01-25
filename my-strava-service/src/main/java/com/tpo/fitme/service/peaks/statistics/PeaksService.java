package com.tpo.fitme.service.peaks.statistics;

import com.tpo.fitme.domain.Athlete;
import com.tpo.fitme.domain.Sport;
import com.tpo.fitme.domain.activity.Activity;
import com.tpo.fitme.service.peaks.Peak;
import com.tpo.strava.persistence.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tiberiu
 * @since 19.06.18
 */
@Slf4j
@Service
public class PeaksService {

    private final ActivityService activityService;

    @Autowired
    public PeaksService(ActivityService activityService) {
        this.activityService = activityService;
    }

    public List<Peak> findAllPeaks(Athlete athlete) {
        List<Peak> peaks = activityService.findAllBySport(athlete.getId(), Sport.HIKE)
                .stream()
                .filter(Activity::isHikeToAPeak)
                .map(Peak::of)
                .collect(Collectors.toList());
        log.info("Found {} peaks", peaks.size());
        return peaks;
    }

}
