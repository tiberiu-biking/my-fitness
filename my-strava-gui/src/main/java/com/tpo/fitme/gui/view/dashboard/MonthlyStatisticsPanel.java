package com.tpo.fitme.gui.view.dashboard;

import com.tpo.fitme.gui.domain.UserSession;
import com.tpo.fitme.service.statistics.StatisticsService;
import com.tpo.fitme.service.summary.ActivitiesSummaryService;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import lombok.val;

import javax.annotation.PostConstruct;
import java.time.Month;

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
        val activeDays = activitiesSummaryService.getActiveDays(athleteId, getMonth());
        val lastMontActiveDays = activitiesSummaryService.getActiveDays(athleteId, getMonth().minus(1));
        details.addComponent(buildH4Label("ACTIVE DAYS"));
        details.addComponent(textArea(activeDays, lastMontActiveDays));

        val minutes = statisticsService.getTotalDuration(athleteId, getMonth());
        val lastMonthMinutes = statisticsService.getTotalDuration(athleteId, getMonth().minus(1));
        details.addComponent(buildH4Label("MINUTES"));
        details.addComponent(textArea(minutes, lastMonthMinutes));

        val km = statisticsService.getTotalDistance(athleteId, getMonth());
        val lastMonthKM = statisticsService.getTotalDistance(athleteId, getMonth().minus(1));
        details.addComponent(buildH4Label("KM"));
        details.addComponent(textArea((long) km, (long) lastMonthKM));
    }

    private TextArea textArea(long currentMonth, long lastMonth) {
        val vs = currentMonth - lastMonth;
        val line2 = (vs > 0 ? "+" : "") + vs + " FROM LAST MONTH";
        val textArea = new TextArea();
        textArea.setRows(2);
        textArea.setReadOnly(true);
        textArea.setValue(currentMonth + System.lineSeparator() + line2);
        return textArea;
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

    private Label buildH4Label(String caption) {
        Label section = new Label(caption);
        section.addStyleName(ValoTheme.LABEL_H4);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        return section;
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
