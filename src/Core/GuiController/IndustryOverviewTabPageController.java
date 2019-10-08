package Core.GuiController;

import Core.Company;
import Core.Economy;
import Core.Enums.IndustryType;
import Core.Enums.ViewPerspective;
import Core.GuiController.Civilian.civCompanyListCellConsumer;
import Core.GuiController.Civilian.civCompanyListCellWork;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class IndustryOverviewTabPageController
{
    IndustryType type;
    FXMLLoader loader;
    List<Company> companyList;
    Controller parentController;
    ViewPerspective perspective;

    @FXML
    private ListView companyListView;
    private ObservableList observableListPerson = FXCollections.observableArrayList();


    public IndustryOverviewTabPageController(IndustryType type, ViewPerspective perspective)
    {
        this.type = type;
        companyList = Economy.getEconomy().getCompanies(type);
        loader = new FXMLLoader(getClass().getResource("../../fxml/industryOverwiewTabPage.fxml"));
        loader.setController(this);
        this.perspective = perspective;
    }

    public IndustryOverviewTabPageController(IndustryType type, Controller parentController, ViewPerspective perspective)
    {
        this(type, perspective);
        this.parentController = parentController;
    }


    public void setListView()
    {
        observableListPerson.setAll(companyList);
        companyListView.setItems(observableListPerson);

        companyListView.setCellFactory(new Callback<ListView<Company>, ListCell<Company>>()
        {
            @Override
            public ListCell<Company> call(ListView<Company> param)
            {
                switch (perspective)
                {
                    case CONSUMER:
                        return new civCompanyListCellConsumer();
                    case JOBSEEKING:
                        return new civCompanyListCellWork();
                    default:
                        GENERAL:
                        return new civCompanyListCellConsumer();
                }
            }

        });

        companyListView.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if (parentController != null)
                    parentController.getMessage(companyListView.getSelectionModel().getSelectedItem());
            }
        });
    }

    @FXML
    private void initialize()
    {
        setListView();
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
