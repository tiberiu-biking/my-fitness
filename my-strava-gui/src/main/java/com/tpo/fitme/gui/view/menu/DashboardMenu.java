package com.tpo.fitme.gui.view.menu;

import com.tpo.fitme.domain.Athlete;
import com.tpo.fitme.gui.navigator.DashboardViewType;
import com.tpo.fitme.service.sync.Synchronizer;
import com.tpo.strava.persistence.service.ActivitiesService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

import static com.vaadin.ui.UI.getCurrent;

public final class DashboardMenu extends CustomComponent {

    public static final  String  ID                     = "dashboard-menu";
    public static final  String  REPORTS_BADGE_ID       = "dashboard-menu-reports-badge";
    public static final  String  NOTIFICATIONS_BADGE_ID = "dashboard-menu-notifications-badge";
    private static final String  STYLE_VISIBLE          = "valo-menu-visible";
    private final        Athlete athlete;
    private final Synchronizer synchronizer;
    private final ActivitiesService activitiesService;

    private Label    notificationsBadge;
    private Label    reportsBadge;
    private MenuItem settingsItem;

    public DashboardMenu(Athlete athlete, Synchronizer groupSynchronizer, ActivitiesService activitiesService) {
        this.athlete = athlete;
        this.synchronizer = groupSynchronizer;
        this.activitiesService = activitiesService;
        setPrimaryStyleName("valo-menu");
        setId(ID);
        setSizeUndefined();
        setCompositionRoot(buildContent());
    }

    private Component buildContent() {
        final CssLayout menuContent = new CssLayout();
        menuContent.addStyleName("sidebar");
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleName("no-vertical-drag-hints");
        menuContent.addStyleName("no-horizontal-drag-hints");
        menuContent.setWidth(null);
        menuContent.setHeight("100%");

        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildUserMenu());
        menuContent.addComponent(buildToggleButton());
        menuContent.addComponent(buildMenuItems());

        return menuContent;
    }

    private Component buildTitle() {
        Label logo = new Label("My <strong>Strava</strong>", ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("valo-menu-title");
        return logoWrapper;
    }


    private Component buildUserMenu() {
        final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
        settingsItem = settings.addItem("", new ExternalResource(athlete.getProfileMediumPicture()), null);

        settingsItem.addItem("Sync", VaadinIcons.REFRESH, (MenuBar.Command) selectedItem -> {
            Notification.show("Synchronization started!");
            synchronizer.sync(athlete);
        });
        settingsItem.addItem("Resync", VaadinIcons.FIRE, (MenuBar.Command) selectedItem -> {
            activitiesService.removeAll(athlete);
            Notification.show("Cleanup done. Synchronization started!");
            synchronizer.sync(athlete);
        });

        /*
        settingsItem.addItem("Edit Profile", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
//                ProfilePreferencesWindow.open(user, false);
            }
        });
        settingsItem.addItem("Preferences", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
//                ProfilePreferencesWindow.open(user, true);
            }
        });
 */
        settingsItem.addSeparator();
        settingsItem.addItem("Sign Out", new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
//                DashboardEventBus.post(new UserLoggedOutEvent());
            }
        });
        return settings;
    }

    private Component buildToggleButton() {
        Button valoMenuToggleButton = new Button("Menu", new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                if (getCompositionRoot().getStyleName().contains(STYLE_VISIBLE)) {
                    getCompositionRoot().removeStyleName(STYLE_VISIBLE);
                } else {
                    getCompositionRoot().addStyleName(STYLE_VISIBLE);
                }
            }
        });
        valoMenuToggleButton.setIcon(VaadinIcons.LIST);
        valoMenuToggleButton.addStyleName("valo-menu-toggle");
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
        return valoMenuToggleButton;
    }

    private Component buildMenuItems() {
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");

        for (final DashboardViewType view : DashboardViewType.values()) {
            Component menuItemComponent = new ValoMenuItemButton(view);
            menuItemsLayout.addComponent(menuItemComponent);
        }
        return menuItemsLayout;

    }

    private Component buildBadgeWrapper(final Component menuItemButton,
                                        final Component badgeLabel) {
        CssLayout dashboardWrapper = new CssLayout(menuItemButton);
        dashboardWrapper.addStyleName("badgewrapper");
        dashboardWrapper.addStyleName(ValoTheme.MENU_ITEM);
        badgeLabel.addStyleName(ValoTheme.MENU_BADGE);
        badgeLabel.setWidthUndefined();
        badgeLabel.setVisible(false);
        dashboardWrapper.addComponent(badgeLabel);
        return dashboardWrapper;
    }

    @Override
    public void attach() {
        super.attach();
//        updateNotificationsCount(null);
    }

//    @Subscribe
//    public void updateNotificationsCount(
//            final NotificationsCountUpdatedEvent event) {
//        int unreadNotificationsCount = DashboardUI.getDataProvider()
//                .getUnreadNotificationsCount();
//        notificationsBadge.setValue(String.valueOf(unreadNotificationsCount));
//        notificationsBadge.setVisible(unreadNotificationsCount > 0);
//    }

//    @Subscribe
//    public void updateReportsCount(final ReportsCountUpdatedEvent event) {
//        reportsBadge.setValue(String.valueOf(event.getCount()));
//        reportsBadge.setVisible(event.getCount() > 0);
//    }

    public final class ValoMenuItemButton extends Button {

        private static final String STYLE_SELECTED = "selected";

        private final DashboardViewType view;

        public ValoMenuItemButton(final DashboardViewType view) {
            this.view = view;
            setPrimaryStyleName("valo-menu-item");
            setIcon(view.getIcon());
            setCaption(view.getCaption());
            addClickListener((ClickListener) event -> getCurrent().getNavigator().navigateTo(view.getViewName()));
        }
    }
}
