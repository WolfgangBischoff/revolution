package Core.GuiController;

import Core.Enums.IndustryType;
import Core.Person;
import Core.Product;
import Core.ProductStorage;
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

public class StorageOverviewTabPageController
{
    ProductStorage storage;
    IndustryType type;
    FXMLLoader loader;

    @FXML
    private ListView productsList;
    List<List<Product>> products;
    private ObservableList observableListPerson = FXCollections.observableArrayList();

    @FXML
    Text headline;

    public StorageOverviewTabPageController(ProductStorage storage, IndustryType type)
    {
        this.storage = storage;
        this.type = type;
        products = storage.getAllProducts(type);
        loader = new FXMLLoader(getClass().getResource("../../fxml/ProductDetailTabPage.fxml"));
        loader.setController(this);
    }

    public void setListView()
    {

        observableListPerson.setAll(products);
        productsList.setItems(observableListPerson);

        productsList.setCellFactory(new Callback<ListView<Product>, ListCell<Product>>()
        {
            @Override
            public ListCell<Product> call(ListView<Product> param)
            {
                return new ProductListCellController();
            }
        });
    }

    @FXML
    private void initialize()
    {
        headline.setText(type.toString());
        //List Items on left site
        setListView();

        //detail on right side
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
