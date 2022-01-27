package com.tpo.fitme.gui.view.grid;

import com.tpo.fitme.domain.Athlete;
import com.tpo.fitme.gui.domain.UserSession;
import com.tpo.fitme.service.peaks.Peak;
import com.tpo.fitme.service.peaks.statistics.PeaksService;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Tiberiu
 * @since 14.10.17
 */
@SpringView(name = PeaksGridView.VIEW_NAME)
public class PeaksGridView extends AbstractGridView {

    public static final String VIEW_NAME = "peaks";
    private final PeaksService peaksService;
    private final Athlete athlete;

    @Autowired
    public PeaksGridView(PeaksService peaksService, UserSession userSession) {
        this.peaksService = peaksService;
        this.athlete = userSession.getUser();
        Grid<Peak> grid = buildGrid();
        addComponent(grid);
    }

    private Grid<Peak> buildGrid() {
        Grid<Peak> grid = createGrid();
        grid.setItems(peaksService.findAllPeaks(athlete));
        grid.addColumn(Peak::getName).setCaption("Peak").setHidable(false);
        grid.addColumn(Peak::getAltitude).setCaption("Altitude").setHidable(false);
        grid.addColumn(a -> "<a href='" + a.getActivity() + "' target='_blank'>" + a.getActivity() + "</a>", new HtmlRenderer()).setCaption("url").setHidable(false);

        grid.addColumn(Peak::getDate).setCaption("Date").setHidable(false);
        return grid;
    }

    private Grid<Peak> createGrid() {
        Grid<Peak> grid = new Grid<>();
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        return grid;
    }
}
