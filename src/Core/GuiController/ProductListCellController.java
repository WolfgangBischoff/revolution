package Core.GuiController;

import Core.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class ProductListCellController extends ListCell<Product>
{
    //Product product;
    @FXML
    private Text name, size, price;
    @FXML
    private GridPane gridpane;

    @Override
        public void updateItem(Product product, boolean empty)
        {
            super.updateItem(product, empty);
            if (product != null)
            {
                loadFXML();

                name.setText(product.getName());
                size.setText("Utility units: " + product.getUtilityBase());
                price.setText("Price bought: " + product.getPriceProductWasBought());
                setGraphic(gridpane);

            }
        }

        private void loadFXML()
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../fxml/ProductListCell.fxml"));
            fxmlLoader.setController(this);
            try
            {
                fxmlLoader.load();
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }

}
