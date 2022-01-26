package com.tpo.fitme.gui.view.chart;

import com.tpo.fitme.gui.domain.UserSession;
import com.tpo.fitme.gui.view.grid.AbstractGridView;
import com.tpo.strava.persistence.service.ActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Tiberiu
 * @since 04/12/15.
 */
public abstract class AbstractActivitiesView extends AbstractGridView {

    protected final ActivitiesService activitiesService;
    protected final Long athleteId;

    @Autowired
    public AbstractActivitiesView(UserSession userSession, ActivitiesService activitiesService) {
        this.activitiesService = activitiesService;
        this.athleteId = userSession.getUser().getId();
    }

}
