package Core.GuiController.Graphs;

import Core.*;
import javafx.scene.chart.LineChart;
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
    //final AreaChart<Number, Number> ac;
    final LineChart<Number, Number> ac;
    Company company;
    Map<Integer, List<Integer>> avgData = new TreeMap();
    Integer maxTotalDemandVisible = 0;
    int consideredDemandPeriods = 3;
    int consideredOfferPeriods = 1;

    public AreaChartSample(Company company)
    {
        this.company = company;
        List<MarketAnalysisData> marketAnalysisData = Market.getMarket().getMarketAnalysisData(company.getIndustry());
        List<XYChart.Series> demandSeriesList = new ArrayList<>();
        ArrayList<XYChart.Series> offerSeriesList = new ArrayList<>();

        //Correct planned visible periods
        if (marketAnalysisData.size() < consideredDemandPeriods)
            consideredDemandPeriods = marketAnalysisData.size();
        if (marketAnalysisData.size() < consideredOfferPeriods)
            consideredOfferPeriods = marketAnalysisData.size();

        //foreach daily market dataset
        for (int i = 0; i < consideredDemandPeriods; i++)
        {
            MarketAnalysisData current = marketAnalysisData.get(i);
            TreeMap<Integer, Integer> customerData = current.getNumCustomerPerBudget();
            XYChart.Series demandSeries = new XYChart.Series();
            demandSeriesList.add(demandSeries);
            demandSeries.setName(current.getDate().toString());

            //Length of X axis
            if (current.getMarketTotalDemand() > maxTotalDemandVisible)
                maxTotalDemandVisible = current.getMarketTotalDemand();

            //add datapoints demand
            Integer sumPersons = current.getMarketTotalDemand();
            for (Map.Entry<Integer, Integer> entry : customerData.entrySet())
            {
                demandSeries.getData().add(new XYChart.Data<Integer, Integer>(sumPersons, entry.getKey()));
                addAvgData(sumPersons, entry.getKey());
                sumPersons -= entry.getValue();
                demandSeries.getData().add(new XYChart.Data<Integer, Integer>(sumPersons, entry.getKey()));
                addAvgData(sumPersons, entry.getKey());
                //System.out.println("Add Avg " + sumPersons +" "+ entry.getKey());
            }

            //add company offers
            List<LuxuryPriceGroup> companyOffers = current.getSupplierOffers();
            if (i < consideredOfferPeriods)
                for (int j = 0; j < companyOffers.size(); j++)
                {
                    LuxuryPriceGroup offer = companyOffers.get(j);
                    XYChart.Series offerSeries = new XYChart.Series();
                    offerSeries.setName(offer.getCompany().getName());
                    offerSeriesList.add(offerSeries);

                    offerSeries.getData().add(new XYChart.Data<Double, Integer>(0d, offer.getPrice()));
                    offerSeries.getData().add(new XYChart.Data<Double, Integer>(Society.getSociety().getPeople().size() + 0.5, offer.getPrice()));
                }
        }


        //create chart
        xAxis = new NumberAxis("Demand", 0, maxTotalDemandVisible + 0.5, 1);//String axisLabel, double lowerBound, double upperBound, double tickUnit
        yAxis = new NumberAxis();
        yAxis.setLabel("Budget");
        ac = new LineChart<Number, Number>(xAxis, yAxis);
        ac.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
        ac.setTitle("Market Budgets");
        ac.setCreateSymbols(false); //Hide dots


        for (int i = 0; i < consideredDemandPeriods; i++)
        {
            ac.getData().addAll(demandSeriesList.get(i));
            demandSeriesList.get(i).getNode().setStyle("-fx-stroke: blue; -fx-stroke-width: 0.5px;");

        }
        //Just added latest prices
        for (int i = 0; i < offerSeriesList.size(); i++)
        {
            ac.getData().addAll(offerSeriesList.get(i));
            offerSeriesList.get(i).getNode().setStyle("-fx-stroke: black; -fx-stroke-width: 2px;");

        }

        XYChart.Series avg = calcAvgData();
        ac.getData().add(avg);
        avg.getNode().setStyle("-fx-stroke: red; -fx-stroke-width: 4px;");
    }

    private XYChart.Series calcAvgData()
    {
        XYChart.Series avg = new XYChart.Series();
        avg.setName("Average Budgets");
        Integer lastDataAvg = null;
        for (Map.Entry<Integer, List<Integer>> dataPoints : avgData.entrySet())
        {
            List<Integer> current = dataPoints.getValue();
            Integer sum = 0;
            for (Integer data : current)
                sum += data;
            Integer dataAvg = sum / current.size();
            if(lastDataAvg == null || dataAvg < lastDataAvg) //Skip dots that lead to increasing graph
            {
                avg.getData().add(new XYChart.Data(dataPoints.getKey(), dataAvg));
                lastDataAvg = dataAvg;
            }
            //else
                //System.out.println("Not rendered: " + dataAvg);
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

