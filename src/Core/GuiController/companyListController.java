package Core.GuiController;

import Core.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.util.ArrayList;

public class companyListController
{
    @FXML
    ListView listView;
    private Economy economy;
    private ArrayList<Company> companies;
    private ObservableList<Company> observableListCompany = FXCollections.observableArrayList();

    @FXML
    private void initialize()
    {
        economy = Simulation.getSingleton().getEconomy();
        companies = economy.getCompanies();
        listView.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                showCompanyDetail();
            }
        });
        setListView();
    }

    public void setListView() {

        observableListCompany.setAll(companies);
        listView.setItems(observableListCompany);

        listView.setCellFactory(new Callback<ListView<Company>, ListCell<Company>>() {
            @Override
            public ListCell<Company> call(ListView<Company> param)
            {
                return new ListCellCompany();
            }
        });
    }


    @FXML
    protected void backToOverview(ActionEvent event)
    {
        GameWindow.getSingleton().createNextScene("../fxml/economyOverview.fxml");
    }

    @FXML
    private void showCompanyDetail()
    {
        CompanyDetailController companyDetailController = new CompanyDetailController((Company)listView.getSelectionModel().getSelectedItem());
        GameWindow.getSingleton().createNextScene(companyDetailController.load());
    }
}
