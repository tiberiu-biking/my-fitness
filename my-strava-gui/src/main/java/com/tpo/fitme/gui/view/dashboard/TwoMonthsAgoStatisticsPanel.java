package com.tpo.fitme.gui.view.dashboard;

import com.tpo.fitme.gui.domain.UserSession;
import com.tpo.fitme.service.statistics.StatisticsService;
import com.tpo.fitme.service.summary.ActivitiesSummaryService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * @author Tiberiu
 * @since 13.06.18
 */
@UIScope
@SpringComponent
public class TwoMonthsAgoStatisticsPanel extends MonthlyStatisticsPanel {

    @Autowired
    public TwoMonthsAgoStatisticsPanel(UserSession userSession,
                                       StatisticsService statisticsService,
                                       ActivitiesSummaryService activitiesSummaryService) {
        super(userSession, statisticsService,activitiesSummaryService);
    }

    @Override
    Month getMonth() {
        return LocalDateTime.now().minusMonths(2).getMonth();
    }



}
