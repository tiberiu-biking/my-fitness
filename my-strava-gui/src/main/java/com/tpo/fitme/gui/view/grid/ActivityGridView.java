package com.tpo.fitme.gui.view.grid;

import com.tpo.fitme.domain.activity.Activity;
import com.tpo.fitme.gui.domain.UserSession;
import com.tpo.fitme.gui.view.chart.AbstractActivitiesView;
import com.tpo.strava.persistence.service.ActivitiesService;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;

import java.time.format.FormatStyle;

import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;
import static org.apache.commons.lang3.time.DurationFormatUtils.formatDuration;

/**
 * @author Tiberiu
 * @since 14.10.17
 */
@SpringView(name = ActivityGridView.VIEW_NAME)
public class ActivityGridView extends AbstractActivitiesView {

    public static final String VIEW_NAME = "activities";

    public ActivityGridView(UserSession userSession, ActivitiesService activitiesService) {
        super(userSession, activitiesService);
        Grid<Activity> grid = buildGrid();
        addComponent(grid);
    }

    private Grid<Activity> buildGrid() {
        Grid<Activity> grid = createGrid();
        grid.setItems(activitiesService.findAllInChronologicalOrder(athleteId));
        grid.addColumn(Activity::getName).setCaption("Name").setHidable(false);
        grid.addColumn(Activity::getSport).setCaption("Type").setHidable(false);

        grid.addColumn(activity -> activity.getStartDate().format(ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .setCaption("Date")
                .setHidable(true);

        grid.addColumn(activity -> activity.getCalories() + " kcal")
                .setCaption("Calories")
                .setHidable(false);

        grid.addColumn(activity -> formatDuration(activity.getDuration() * 60 * 1000, "HH:mm:ss"))
                .setCaption("Duration")
                .setHidable(false);

        grid.addColumn(activity -> activity.getDistance().longValue() + " km")
                .setCaption("Distance")
                .setHidable(true);

        grid.addColumn(activity -> activity.getElevation().longValue() + " m")
                .setCaption("Elevation")
                .setHidable(true);


        return grid;
    }

    private Grid<Activity> createGrid() {
        Grid<Activity> grid = new Grid<>();
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        return grid;
    }

}
