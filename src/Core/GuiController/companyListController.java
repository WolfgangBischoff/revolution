package Core.GuiController;

import Core.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.ArrayList;

public class companyListController
{
    @FXML
    ListView listView;
    Economy economy;
    private ArrayList<Company> companies;
    private ObservableList<Company> observableListCompany = FXCollections.observableArrayList();

    @FXML
    private void initialize()
    {
        economy = Simulation.getSingleton().getEconomy();
        companies = economy.getCompanies();
        //ADD TO Listener
        //Click Action
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
        //Society.getSociety().getSocietyStatistics().removePropertyChangeListener(this);
    }
}
