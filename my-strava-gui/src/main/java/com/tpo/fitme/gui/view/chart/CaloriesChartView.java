package com.tpo.fitme.gui.view.chart;

import at.downdrown.vaadinaddons.highchartsapi.model.data.HighChartsData;
import at.downdrown.vaadinaddons.highchartsapi.model.data.base.StringDoubleData;
import com.tpo.fitme.gui.component.chart.StravaChart;
import com.tpo.fitme.service.summary.ActivitiesSummaryService;
import com.tpo.fitness.domain.summary.ActivitiesSummary;
import com.tpo.fitness.domain.summary.Summary;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import static com.tpo.fitme.service.constants.Constants.DEFAULT_CHART_PERIOD;

@SpringView(name = CaloriesChartView.VIEW_NAME)
public class CaloriesChartView extends AbstractActivitiesSummaryView {

    public static final String VIEW_NAME = "calories";

    @Autowired
    public CaloriesChartView(ActivitiesSummaryService activitiesSummaryService) {
        super(activitiesSummaryService);
    }

    @PostConstruct
    public void init() {
        addComponent(buildChart(activitiesSummaryService.generateSummarySince(DEFAULT_CHART_PERIOD)));
    }

    private StravaChart buildChart(Summary summary) {
        return new StravaChart("Calories", summary.getActivitiesSummaries()) {
            @Override
            protected HighChartsData getColumnValue(ActivitiesSummary activitiesSummary) {
                return new StringDoubleData(activitiesSummary.getYear() + "/" + activitiesSummary.getMonth(), activitiesSummary.getCalories());
            }
        };
    }


}
