package Core.GuiController;

import Core.Enums.IndustryType;
import Core.GameWindow;
import Core.Person;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.IOException;

public class StorageOverviewController
{
    //ProductOwner storageOwner;

    FXMLLoader loader;

    @FXML
    Button backButton;
    @FXML
    Text name;
    @FXML
    Tab foodTab, clothsTabPage, housingTabPage, educationTabPage;

    public StorageOverviewController()
    {

        //storageOwner = storage.getOwner();
        loader = new FXMLLoader(getClass().getResource("../../fxml/storageOverview.fxml"));
        loader.setController(this);
    }

    @FXML
    private void initialize()
    {//Is used by fxml after this is set as Controller

        name.setText("TODO");

        //Init Tabs
        //foodTab.setGraphic(new Circle(0, 0, 10));
        foodTab.setGraphic(new ImageView(new Image("/img/food.png", 20,20,false,false)));

        /*
        foodTab.setContent(new StorageOverviewTabPageController(storage, IndustryType.FOOD).load());
        clothsTabPage.setContent(new StorageOverviewTabPageController(storage, IndustryType.CLOTHS).load());
        housingTabPage.setContent(new StorageOverviewTabPageController(storage, IndustryType.HOUSING).load());
        educationTabPage.setContent(new StorageOverviewTabPageController(storage, IndustryType.EDUCATION).load());
*/
        backButton.setOnAction((event) -> {
            //Create PersonDetailViewController which can pass a parameter
            /*if (storageOwner instanceof Person)
            {
                PersonDetailController personDetailController = new PersonDetailController((Person) storageOwner);
                GameWindow.getSingleton().createNextScene(personDetailController.load());
            }
            else
                System.out.println("Not person TODO");*/
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
