package Core.GuiController.Company;

import Core.CompanyMarketData;
import Core.GuiController.Graphs.BarChartController;
import Core.MarketAnalysisData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CompMarketAnalysisListCellC extends ListCell<MarketAnalysisData>
{
    MarketAnalysisData marketAnalysisData;

    @FXML
    GridPane cell;
    @FXML
    Label date, totalDemand, customerBudgets, marketOffers;

    @Override
    public void updateItem(MarketAnalysisData data, boolean empty)
    {
        super.updateItem(data, empty);
        if (data != null)
        {
            marketAnalysisData = data;
            createCell();
            setInfo();
            setGraphic(cell);
        }
    }

    private void createCell()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../fxml/company/compMarketAnalysisListCell.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void setInfo()
    {
        date.setText(marketAnalysisData.getDate().toString());
        totalDemand.setText(marketAnalysisData.getMarketTotalDemand().toString());
        marketOffers.setText(marketAnalysisData.getOffersPerCustomerGroup().toString());
        customerBudgets.setText(marketAnalysisData.getNumCustomerPerBudget().toString());
    }

}

