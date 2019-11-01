package Core.GuiController.Graphs;

import Core.Company;
import Core.Market;
import Core.MarketAnalysisData;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

import java.util.*;

public class AreaChartSample
{
    final NumberAxis xAxis;
    final NumberAxis yAxis;
    final AreaChart<Number, Number> ac;
    Company company;
    Map<Integer, List<Integer>> avgData = new TreeMap();
    Integer maxTotalDemandVisible = 0;
    int consideredPeriods = 3;

    public AreaChartSample(Company company)
    {
        this.company = company;
        List<MarketAnalysisData> marketAnalysisData = Market.getMarket().getMarketAnalysisData(company.getIndustry());
        List<XYChart.Series> seriesList = new ArrayList<>();

        if (marketAnalysisData.size() < consideredPeriods)
            consideredPeriods = marketAnalysisData.size();

        for (int i = 0; i < consideredPeriods; i++)
        {
            MarketAnalysisData current = marketAnalysisData.get(i);
            XYChart.Series s1 = new XYChart.Series();
            TreeMap<Integer, Integer> customerData = current.getNumCustomerPerBudget();
            s1.setName(current.getDate().toString());
            seriesList.add(s1);

            if (current.getMarketTotalDemand() > maxTotalDemandVisible)
                maxTotalDemandVisible = current.getMarketTotalDemand();

            Integer sumPersons = current.getMarketTotalDemand();
            for (Map.Entry<Integer, Integer> entry : customerData.entrySet())
            {
                for (int n = 0; n < entry.getValue(); n++)
                {
                    s1.getData().add(new XYChart.Data(sumPersons, entry.getKey()));
                    addAvgData(sumPersons, entry.getKey());
                    sumPersons--;
                }
            }

        }

        xAxis = new NumberAxis(0.5, maxTotalDemandVisible + 0.5, 1);
        xAxis.setLabel("Demand");
        yAxis = new NumberAxis();
        yAxis.setLabel("Budget");
        ac = new AreaChart<Number, Number>(xAxis, yAxis);
        ac.getData().add(calcAvgData());

        ac.setTitle("Market Budgets");
        for (int i = 0; i < consideredPeriods; i++)
        {
            ac.getData().addAll(seriesList.get(i));
        }

    }

    private XYChart.Series calcAvgData()
    {
        XYChart.Series avg = new XYChart.Series();
        avg.setName("Average Budgets");
        for (Map.Entry<Integer, List<Integer>> dataPoints : avgData.entrySet())
        {
            List<Integer> current = dataPoints.getValue();
            Integer sum = 0;
            for (Integer data : current)
                sum += data;
            Integer dataAvg = sum / current.size();
            avg.getData().add(new XYChart.Data(dataPoints.getKey(), dataAvg));
        }
        return avg;
    }

    private void addAvgData(Integer sumPersons, Integer budget)
    {
        if (!avgData.containsKey(sumPersons))
            avgData.put(sumPersons, new ArrayList<>());
        avgData.get(sumPersons).add(budget);
    }

    public Pane load()
    {
        Pane pane = new Pane(ac);
        pane.getStylesheets().add("css/areaChartSample.css");
        return pane;
    }
}

