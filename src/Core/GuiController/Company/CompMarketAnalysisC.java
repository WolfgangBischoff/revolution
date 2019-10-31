package Core.GuiController.Company;

import Core.*;
import Core.GuiController.Graphs.AreaChartSample;
import Core.GuiController.Graphs.BarChartController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class CompMarketAnalysisC
{
    FXMLLoader loader;
    Company company;

    @FXML
    ListView<MarketAnalysisData> marketData;
    private ObservableList<MarketAnalysisData> observableListData = FXCollections.observableArrayList();
    private List<MarketAnalysisData> marketAnalysisData;
    @FXML Pane customerBudgets;

    //For other Comp
    CompMarketAnalysisC(Company company)
    {
        this.company = company;
        loader = new FXMLLoader(getClass().getResource("/fxml/company/compMarketAnalysis.fxml"));
        loader.setController(this);
    }

    @FXML
    private void initialize()
    {
        marketAnalysisData = Market.getMarket().getMarketAnalysisData(company.getIndustry());
        setListView();
        //customerBudgets.getChildren().add(new BarChartController(company).load());
        customerBudgets.getChildren().add(new AreaChartSample(company).load());
    }

    public void setListView()
    {
        observableListData.setAll(marketAnalysisData);
        marketData.setItems(observableListData);
        marketData.setCellFactory(new Callback<ListView<MarketAnalysisData>, ListCell<MarketAnalysisData>>()
        {
            @Override
            public ListCell<MarketAnalysisData> call(ListView<MarketAnalysisData> param)
            {
                return new CompMarketAnalysisListCellC();
            }
        });
    }

    public Pane load()
    {
        try
        {
            return loader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
