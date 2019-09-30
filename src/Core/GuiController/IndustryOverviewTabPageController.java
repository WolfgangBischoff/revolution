package Core.GuiController;

import Core.Company;
import Core.Economy;
import Core.Enums.IndustryType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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

    @FXML
    private ListView companyListView;
    private ObservableList observableListPerson = FXCollections.observableArrayList();

    @FXML
    Text headline;


    public IndustryOverviewTabPageController(IndustryType type)
    {
        this.type = type;
        companyList = Economy.getEconomy().getCompanies(type);
        loader = new FXMLLoader(getClass().getResource("../../fxml/industryOverwiewTabPage.fxml"));
        loader.setController(this);
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
    }

    @FXML
    private void initialize()
    {
        headline.setText(type.toString());
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
