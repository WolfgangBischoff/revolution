package Core.GuiController;

import Core.GameWindow;
import Core.Person;
import Core.ProductOwner;
import Core.ProductStorage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

public class StorageOverviewController
{
    ProductOwner storageOwner;
    ProductStorage storage;
    FXMLLoader loader;

    @FXML
    Button backButton;
    @FXML
    Text name;

    public StorageOverviewController(ProductStorage storage)
    {
        this.storage = storage;
        storageOwner = storage.getOwner();
        System.out.println("HHH" + storageOwner.getName());
        loader = new FXMLLoader(getClass().getResource("../../fxml/storageOverview.fxml"));
        loader.setController(this);
    }

    @FXML
    private void initialize()
    {//Is used by fxml after this is set as Controller

        name.setText(storageOwner.getName());

        backButton.setOnAction((event) -> {
            //Create PersonDetailViewController which can pass a parameter
            if (storageOwner instanceof Person)
            {
                PersonDetailController personDetailController = new PersonDetailController((Person) storageOwner);
                GameWindow.getSingleton().createNextScene(personDetailController.load());
            }
            else
                System.out.println("Not person TODO");
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
