package Core.GuiController.Graphs;

import Core.Company;
import Core.Market;
import Core.MarketAnalysisData;
import javafx.scene.chart.*;
import javafx.scene.layout.Pane;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarChartController
{
    final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";

    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);

private Company company;
    final CategoryAxis x = new CategoryAxis();
    final NumberAxis y = new NumberAxis();
final BarChart<String, Number> budgetsBarChart = new BarChart<String, Number>(x,y);

    public BarChartController(Company company)
    {
        this.company = company;

        bc.setTitle("Country Summary");
        xAxis.setLabel("Country");
        yAxis.setLabel("Value");

        XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
        series1.setName("2003");
        series1.getData().

                add(new XYChart.Data(austria, 25601.34));
        series1.getData().

                add(new XYChart.Data(brazil, 20148.82));
        series1.getData().

                add(new XYChart.Data(france, 10000));
        series1.getData().

                add(new XYChart.Data(italy, 35407.15));
        series1.getData().

                add(new XYChart.Data(usa, 12000));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("2004");
        series2.getData().

                add(new XYChart.Data(austria, 57401.85));
        series2.getData().

                add(new XYChart.Data(brazil, 41941.19));
        series2.getData().

                add(new XYChart.Data(france, 45263.37));
        series2.getData().

                add(new XYChart.Data(italy, 117320.16));
        series2.getData().

                add(new XYChart.Data(usa, 14845.27));

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("2005");
        series3.getData().

                add(new XYChart.Data(austria, 45000.65));
        series3.getData().

                add(new XYChart.Data(brazil, 44835.76));
        series3.getData().

                add(new XYChart.Data(france, 18722.18));
        series3.getData().

                add(new XYChart.Data(italy, 17557.31));
        series3.getData().

                add(new XYChart.Data(usa, 92633.68));


        bc.getData().addAll(series1, series2, series3);
    }

    public Pane load()
    {
        return new Pane(bc);
    }

}
