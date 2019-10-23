package Core.GuiController.Company;

import Core.Company;
import Core.CompanyMarketData;
import Core.GuiController.Civilian.civCompanyListCellConsumer;
import Core.Simulation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class CompMarketAnalysisC
{
    FXMLLoader loader;
    Company company;

    @FXML
    ListView marketData;
    private ObservableList observableListData = FXCollections.observableArrayList();
    private List<CompanyMarketData> companyMarketData;

    //For Player
    CompMarketAnalysisC()
    {
        loader = new FXMLLoader(getClass().getResource("/fxml/company/compMarketAnalysis.fxml"));
        loader.setController(this);
        company = Simulation.getSingleton().getPlayerCompany();
    }

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
        companyMarketData = company.getCompanyMarketDataStorage().getAnalysisData();
        setListView();
    }

    public void setListView()
    {
        observableListData.setAll(companyMarketData);
        marketData.setItems(observableListData);
        //TODO create analysis object
        marketData.setCellFactory(new Callback<ListView<Company>, ListCell<Company>>()
        {
            @Override
            public ListCell<Company> call(ListView<Company> param)
            {
                return new TextFieldListCell<>();
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
