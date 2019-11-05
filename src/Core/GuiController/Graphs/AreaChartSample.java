package Core.GuiController.Graphs;

import Core.*;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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
            XYChart.Series s1 = new XYChart.Series();
            demandSeriesList.add(s1);
            s1.setName(current.getDate().toString());

            //Length of X axis
            if (current.getMarketTotalDemand() > maxTotalDemandVisible)
                maxTotalDemandVisible = current.getMarketTotalDemand();

            //add datapoints demand
            Integer sumPersons = current.getMarketTotalDemand();
            for (Map.Entry<Integer, Integer> entry : customerData.entrySet())
            {
                //Number Persons, one point per person
                //for (int n = 0; n < entry.getValue(); n++)
                {
                    s1.getData().add(new XYChart.Data<Integer, Integer>(sumPersons, entry.getKey()));
                    //sumPersons--;
                    sumPersons -= entry.getValue();
                    s1.getData().add(new XYChart.Data<Integer, Integer>(sumPersons, entry.getKey()));
                    addAvgData(sumPersons, entry.getKey());
                }
            }

            //add company offers
            List<LuxuryPriceGroup> companyOffers = current.getSupplierOffers();
            if(i < consideredOfferPeriods)
            for (int j = 0; j < companyOffers.size(); j++)
            {
                LuxuryPriceGroup offer = companyOffers.get(j);
                XYChart.Series offerSeries = new XYChart.Series();
                offerSeries.setName(offer.getCompany().getName());
                offerSeriesList.add(offerSeries);

                //System.out.println(current.getDate() + " Price " + offer.getPrice() + " " + offer.getCompany().getName());
                offerSeries.getData().add(new XYChart.Data<Double, Integer>(0d, offer.getPrice()));
                offerSeries.getData().add(new XYChart.Data<Double, Integer>(Society.getSociety().getPeople().size() + 0.5, offer.getPrice()));

                /*
                offerSeries.getNode().lookup(".chart-series-area-line");
                Color color = Color.RED; // or any other color
                String rgb = String.format("%d, %d, %d",
                        (int) (color.getRed() * 255),
                        (int) (color.getGreen() * 255),
                        (int) (color.getBlue() * 255));
                offerSeries.getNode().setStyle("-fx-stroke: rgba(" + rgb + ", 1.0);");*/
            }
        }


        //create chart
        xAxis = new NumberAxis("Demand",0, maxTotalDemandVisible + 0.5, 1);//String axisLabel, double lowerBound, double upperBound, double tickUnit
        yAxis = new NumberAxis();
        yAxis.setLabel("Budget");
        //ac = new AreaChart<Number, Number>(xAxis, yAxis);
        ac = new LineChart<Number, Number>(xAxis, yAxis);
        ac.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
        ac.getData().add(calcAvgData());
        ac.setTitle("Market Budgets");


        for (int i = 0; i < consideredDemandPeriods; i++)
        {
            ac.getData().addAll(demandSeriesList.get(i));
        }
        //Just added latest prices
        for (int i = 0; i < offerSeriesList.size(); i++)
        {
            ac.getData().addAll(offerSeriesList.get(i));
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

    /*

.chart-series-line {
    -fx-stroke-width: 2px;
    -fx-effect: null;
}

    /
     */
}

