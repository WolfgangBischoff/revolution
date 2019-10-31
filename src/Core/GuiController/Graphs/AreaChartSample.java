package Core.GuiController.Graphs;

import Core.Company;
import Core.Market;
import Core.MarketAnalysisData;
import Core.Society;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AreaChartSample
{
    final NumberAxis xAxis;
    final NumberAxis yAxis;
    final AreaChart<Number, Number> ac;
    Company company;

    public AreaChartSample(Company company)
    {
        this.company = company;
        List<MarketAnalysisData> marketAnalysisData = Market.getMarket().getMarketAnalysisData(company.getIndustry());

        xAxis = new NumberAxis(1, Society.getSociety().getPeople().size(), 1);
        yAxis = new NumberAxis();
        ac = new AreaChart<Number, Number>(xAxis, yAxis);

        List<XYChart.Series> seriesList = new ArrayList<>();


        //System.out.println("AREA CHART");
        for (MarketAnalysisData dayentry : marketAnalysisData)
        {
            XYChart.Series s1 = new XYChart.Series();
            seriesList.add(s1);
            MarketAnalysisData current = dayentry;
            TreeMap<Integer, Integer> customerData = current.getNumCustomerPerBudget();
            s1.setName(current.getDate().toString());
            Integer sumPersons = 0;
            s1.getData().add(new XYChart.Data(0, 0));
            for (Map.Entry<Integer, Integer> entry : customerData.entrySet())
            {
                //System.out.println("Key: " + entry.getKey() +" value"+ entry.getValue());
                sumPersons += entry.getValue();
                s1.getData().add(new XYChart.Data(sumPersons, entry.getKey()));
            }
        }


        ac.setTitle("Temperature Monitoring (in Degrees C)");
        for (XYChart.Series series : seriesList)
            ac.getData().addAll(series);
        /*
        XYChart.Series seriesApril = new XYChart.Series();
        seriesApril.setName("April");
        seriesApril.getData().add(new XYChart.Data(1, 4));
        seriesApril.getData().add(new XYChart.Data(3, 10));
        seriesApril.getData().add(new XYChart.Data(6, 15));
        seriesApril.getData().add(new XYChart.Data(9, 8));
        seriesApril.getData().add(new XYChart.Data(12, 5));
        seriesApril.getData().add(new XYChart.Data(15, 18));
        seriesApril.getData().add(new XYChart.Data(18, 15));
        seriesApril.getData().add(new XYChart.Data(21, 13));
        seriesApril.getData().add(new XYChart.Data(24, 19));
        seriesApril.getData().add(new XYChart.Data(27, 21));
        seriesApril.getData().add(new XYChart.Data(30, 21));

        XYChart.Series seriesMay = new XYChart.Series();
        seriesMay.setName("May");
        seriesMay.getData().add(new XYChart.Data(1, 20));
        seriesMay.getData().add(new XYChart.Data(3, 15));
        seriesMay.getData().add(new XYChart.Data(6, 13));
        seriesMay.getData().add(new XYChart.Data(9, 12));
        seriesMay.getData().add(new XYChart.Data(12, 14));
        seriesMay.getData().add(new XYChart.Data(15, 18));
        seriesMay.getData().add(new XYChart.Data(18, 25));
        seriesMay.getData().add(new XYChart.Data(21, 25));
        seriesMay.getData().add(new XYChart.Data(24, 23));
        seriesMay.getData().add(new XYChart.Data(27, 26));
        seriesMay.getData().add(new XYChart.Data(31, 26));

        ac.getData().addAll(seriesApril, seriesMay);*/

    }

    public Pane load()
    {
        return new Pane(ac);
    }
}

