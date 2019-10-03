package Core.GuiController;

import Core.Company;
import Core.Economy;
import Core.Enums.IndustryType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;


import java.io.IOException;
import java.util.List;

public class IndustryOverviewTabPageController
{
    IndustryType type;
    FXMLLoader loader;
    List<Company> companyList;
    Controller parentController;

    @FXML
    private ListView companyListView;
    private ObservableList observableListPerson = FXCollections.observableArrayList();


    public IndustryOverviewTabPageController(IndustryType type)
    {
        this.type = type;
        companyList = Economy.getEconomy().getCompanies(type);
        loader = new FXMLLoader(getClass().getResource("../../fxml/industryOverwiewTabPage.fxml"));
        loader.setController(this);
    }

    public IndustryOverviewTabPageController(IndustryType type, Controller parentController)
    {
        this(type);
        this.parentController = parentController;
    }


    public void setListView()
    {
        observableListPerson.setAll(companyList);
        companyListView.setItems(observableListPerson);

        companyListView.setCellFactory(new Callback<ListView<Company>, ListCell<Company>>() {
            @Override
            public ListCell<Company> call(ListView<Company> param)
            {
                return new ListCellCompany();
            }
        });

        companyListView.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if(parentController != null)
                    parentController.getMessage(companyListView.getSelectionModel().getSelectedItem());
            }
        });
    }

    @FXML
    private void initialize()
    {
        //List Items on left site
        setListView();

        //comp detail on right side
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
