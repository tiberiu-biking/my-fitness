package com.tpo.fitme.gui.view.dashboard;

import com.tpo.fitme.domain.Sport;
import com.tpo.fitme.gui.component.textfield.ReadOnlyTextField;
import com.tpo.fitme.gui.constants.SportIcon;
import com.tpo.fitme.gui.domain.UserSession;
import com.tpo.fitme.service.statistics.StatisticsService;
import com.tpo.fitme.service.summary.ActivitiesSummaryService;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import lombok.val;

import javax.annotation.PostConstruct;
import java.time.Month;

import static java.lang.String.valueOf;

/**
 * @author Tiberiu
 * @since 13.06.18
 */
abstract class MonthlyStatisticsPanel extends HorizontalLayout {
    abstract Month getMonth();

    private final Long athleteId;
    private final StatisticsService statisticsService;
    private final ActivitiesSummaryService activitiesSummaryService;

    public MonthlyStatisticsPanel(UserSession userSession,
                                  StatisticsService statisticsService,
                                  ActivitiesSummaryService activitiesSummaryService) {
        super();
        this.athleteId = userSession.getUser().getId();
        this.statisticsService = statisticsService;
        this.activitiesSummaryService = activitiesSummaryService;
    }

    @PostConstruct
    public void init() {
        setVisuals();

        FormLayout details = buildRoot();
        details.addComponent(buildTitleLabel());
        addStatisticsPanels(details);
        addComponentsAndExpand(details);
    }

    private void addStatisticsPanels(FormLayout details) {
        int activeDays = activitiesSummaryService.getActiveDays(athleteId, getMonth());
        int lastMontActiveDays = activitiesSummaryService.getActiveDays(athleteId, getMonth().minus(1));

        int vs = activeDays - lastMontActiveDays;
        val activeDaysField = new TextField("Active days", String.valueOf(activeDays));
        details.addComponent(activeDaysField);

        val activeDaysVsLastMonthField = new TextField("Vs last month", vs > 0 ? "+" : "" + vs);
        details.addComponent(activeDaysVsLastMonthField);

        long duration = statisticsService.getTotalDuration(athleteId, getMonth());
        val durationField = new TextField("Duration", String.valueOf(duration) + " minutes");
        details.addComponent(durationField);

        float distance = statisticsService.getTotalDistance(athleteId, getMonth());
        val distanceField = new TextField("Distance", String.valueOf(distance) + " km");
        details.addComponent(distanceField);
    }

    private FormLayout buildRoot() {
        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        return details;
    }

    private Label buildTitleLabel() {
        Label section = new Label(getTitle());
        section.addStyleName(ValoTheme.LABEL_H3);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        return section;
    }

    private ReadOnlyTextField buildField(Sport sport, float value, SportIcon icon, String unit) {
        return new ReadOnlyTextField(sport.getCaption(), valueOf(value) + " " + unit, icon);
    }

    private void setVisuals() {
        setCaption(getTitle());
        setWidth(100.0f, Unit.PERCENTAGE);
        setMargin(true);
    }

    String getTitle() {
        return getMonth().name();
    }

}
