package Core.GuiController;

import Core.*;
import Core.Enums.EducationalLayer;
import Core.GuiController.Civilian.CivilianLocationController;
import com.sun.glass.ui.Window;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.IOException;

public class CivilianController
{


    @FXML
    private void initialize() throws IOException
    {

    }

    @FXML
    public void backToMainMenu(javafx.event.ActionEvent event)
    {
        GameWindow.getSingleton().createNextScene("../fxml/mainMenu.fxml");
    }

    @FXML
    private void shopView()
    {
        System.out.println(Market.getMarket().dataMarketCompanies());
    }

    public void job(javafx.event.ActionEvent event)
    {
        System.out.println("Job Market");
    }

    public void consume(ActionEvent event) throws IOException
    {
        System.out.println("Consume");
    }

}
