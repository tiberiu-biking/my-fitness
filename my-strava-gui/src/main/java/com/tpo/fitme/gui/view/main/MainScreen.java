package com.tpo.fitme.gui.view.main;

import com.tpo.fitme.gui.domain.UserSession;
import com.tpo.fitme.gui.view.menu.DashboardMenu;
import com.tpo.fitme.service.sync.Synchronizer;
import com.tpo.strava.persistence.service.ActivitiesService;
import com.vaadin.navigator.Navigator;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringComponent
public class MainScreen extends CustomComponent {

    private final SpringViewProvider springViewProvider;
    private final UserSession userSession;
    private final Synchronizer synchronizer;
    private final ActivitiesService activitiesService;

    @Autowired
    public MainScreen(SpringViewProvider springViewProvider, UserSession userSession, Synchronizer synchronizer, ActivitiesService activitiesService) {
        this.springViewProvider = springViewProvider;
        this.userSession = userSession;
        this.synchronizer = synchronizer;
        this.activitiesService = activitiesService;
        addStyleName("mainview");
        setSizeFull();
    }

    public void refresh() {
        if (userSession.isLoggedIn()) {
            HorizontalLayout layout = new HorizontalLayout();
            layout.setSizeFull();
            setCompositionRoot(layout);
            CssLayout viewContainer = new CssLayout();
            viewContainer.setSizeFull();

            layout.addComponent(new DashboardMenu(userSession.getUser(), synchronizer, activitiesService));
            layout.addComponent(viewContainer);
            layout.setExpandRatio(viewContainer, 1f);

            Navigator navigator = new Navigator(UI.getCurrent(), viewContainer);
            navigator.addProvider(springViewProvider);
            navigator.setErrorView(ErrorView.class);
            navigator.navigateTo(navigator.getState());
        }
    }
}
