package com.tpo.fitme.gui.view.dashboard;

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

@UIScope
@SpringView(name = PremiumView.VIEW_NAME)
public final class PremiumView extends Panel implements View {

    public static final String VIEW_NAME = "premium";

    private final VerticalLayout root;
    private CssLayout dashboardPanels;

    @Autowired
    private ThisMonthStatisticsPanel thisMonthPanel;

    @Autowired
    private LastMonthStatisticsPanel lastMonthPanel;

    @Autowired
    private TwoMonthsAgoStatisticsPanel twoMonthsAgoPanel;

    @Autowired
    private ActivitiesSummaryService activitiesSummaryService;

    @Autowired
    private UserSession userSession;

    private Long athleteId;

    public PremiumView() {
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
        Component content = buildContent();
        root.addComponent(content);
        root.setExpandRatio(content, 1);
    }

    private Component buildContent() {
        dashboardPanels = new CssLayout();
        dashboardPanels.addStyleName("dashboard-panels");
        Responsive.makeResponsive(dashboardPanels);
        dashboardPanels.addComponent(createContentWrapper(thisMonthPanel));
        dashboardPanels.addComponent(createContentWrapper(lastMonthPanel));
        dashboardPanels.addComponent(createContentWrapper(twoMonthsAgoPanel));
        return dashboardPanels;
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
