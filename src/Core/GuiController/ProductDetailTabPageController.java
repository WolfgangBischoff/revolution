package Core.GuiController;

import Core.Enums.IndustryType;
import Core.Product;
import Core.ProductStorage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


import java.io.IOException;
import java.util.List;

public class ProductDetailTabPageController
{
    ProductStorage storage;
    List<Product> products;
    IndustryType type;
    FXMLLoader loader;

    @FXML
    Text headline;

    public ProductDetailTabPageController(ProductStorage storage, IndustryType type)
    {
        this.storage = storage;
        this.type = type;
        products = storage.getAllProducts(type);
        loader = new FXMLLoader(getClass().getResource("../../fxml/ProductDetailTabPage.fxml"));
        loader.setController(this);
    }

    @FXML
    private void initialize()
    {
        headline.setText(type.toString());
        //List Items TODO
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
