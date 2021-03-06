package com.tpo.fitme.gui.view.dashboard;

import com.tpo.fitme.gui.component.card.SparkCard;
import com.tpo.fitme.gui.domain.UserSession;
import com.tpo.fitme.service.summary.ActivitiesSummaryService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import static java.lang.String.valueOf;

@UIScope
@SpringView(name = DashboardView.VIEW_NAME)
public final class DashboardView extends Panel implements View {

    public static final String VIEW_NAME = "";

    private final VerticalLayout root;
    private CssLayout dashboardPanels;

    @Autowired
    private ThisYearStatisticsPanel thisYearStatisticsPanel;

    @Autowired
    private LastYearStatisticsPanel lastYearStatisticsPanel;

    @Autowired
    private AllTimePanel allTimePanel;

    @Autowired
    private ActivitiesSummaryService activitiesSummaryService;

    @Autowired
    private UserSession userSession;
    private Long athleteId;

    public DashboardView() {
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();

        root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.addStyleName("dashboard-view");
        setContent(root);
        Responsive.makeResponsive(root);
    }

    @PostConstruct
    public void init() {
        root.addComponent(buildSparkCards());
        Component content = buildContent();
        root.addComponent(content);
        root.setExpandRatio(content, 1);
    }


    private Component buildContent() {
        dashboardPanels = new CssLayout();
        dashboardPanels.addStyleName("dashboard-panels");
        Responsive.makeResponsive(dashboardPanels);
        dashboardPanels.addComponent(createContentWrapper(thisYearStatisticsPanel));
        dashboardPanels.addComponent(createContentWrapper(lastYearStatisticsPanel));
        dashboardPanels.addComponent(createContentWrapper(allTimePanel));
        return dashboardPanels;
    }

    private Component buildSparkCards() {
        CssLayout sparks = new CssLayout();
        sparks.addStyleName("sparks");
        sparks.setWidth("100%");
        Responsive.makeResponsive(sparks);

        athleteId = userSession.getUser().getId();
        SparkCard worldTripsSparkCard = new SparkCard("trips around the world", valueOf(activitiesSummaryService.getTripsAroundTheWorld(athleteId)));
        sparks.addComponent(worldTripsSparkCard);

        SparkCard moonTripsSparkCard = new SparkCard("trips to the moon", valueOf(activitiesSummaryService.getTripsToTheMoon(athleteId)));
        sparks.addComponent(moonTripsSparkCard);

        SparkCard burgersBurnedSparkCard = new SparkCard("burgers burned", valueOf(activitiesSummaryService.getBurgerBurned(athleteId)));
        sparks.addComponent(burgersBurnedSparkCard);

        SparkCard biersBurnedSparkCard = new SparkCard("beers burned", valueOf(activitiesSummaryService.getBeersBurned(athleteId)));
        sparks.addComponent(biersBurnedSparkCard);

        SparkCard everestClimbedSparkCard = new SparkCard("times climbed Everest", valueOf(activitiesSummaryService.getTimesClimbedEverest(athleteId)));
        sparks.addComponent(everestClimbedSparkCard);

        return sparks;
    }

    private Component createContentWrapper(final Component content) {
        final CssLayout slot = new CssLayout();
        slot.setWidth("100%");
        slot.addStyleName("dashboard-panel-slot");

        CssLayout card = new CssLayout();
        card.setWidth("100%");
        card.addStyleName(ValoTheme.LAYOUT_CARD);

        Label caption = new Label(content.getCaption());
        caption.addStyleName(ValoTheme.LABEL_H4);
        caption.addStyleName(ValoTheme.LABEL_COLORED);
        caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        content.setCaption(null);

        card.addComponents(content);
        slot.addComponent(card);
        return slot;
    }


    @Override
    public void enter(ViewChangeEvent event) {
    }
}
