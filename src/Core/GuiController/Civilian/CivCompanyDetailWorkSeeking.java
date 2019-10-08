package Core.GuiController.Civilian;

import Core.Company;
import Core.GuiController.Controller;
import Core.GuiController.workposition.workListCell;
import Core.Player;
import Core.Simulation;
import Core.Workposition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CivCompanyDetailWorkSeeking implements Controller
{
    Company company;
    FXMLLoader loader;
    List<Workposition> freeWorkpositions = new ArrayList<>();
    Workposition chosenWorkposition = null;

    @FXML
    Label companyDescription;
    @FXML
    Button apply;
    @FXML
    ListView workplacesListView;
    private ObservableList observableListPerson = FXCollections.observableArrayList();

    public CivCompanyDetailWorkSeeking(Company company)
    {
        this.company = company;
        loader = new FXMLLoader(getClass().getResource("../../../fxml/civilian/civCompanyDetailWorkSeeking.fxml"));
        loader.setController(this);
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

    @FXML
    private void initialize()
    {
        freeWorkpositions = company.getFreeWorkpositions();
        companyDescription.setText(company.getName() + " is is a great employer");

        apply.setOnAction(
                new EventHandler<ActionEvent>()
                {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        Player player = Simulation.getSingleton().getPlayer();
                        if (chosenWorkposition != null)
                        {
                            System.out.println("Application send");
                            if (chosenWorkposition.isWorkerAppropriate(player))
                            {
                                chosenWorkposition.getCompany().employWorker(chosenWorkposition, player);
                                System.out.println("You got hired! :)");
                            }
                            else
                                System.out.println("Your qualifications dont fit or you already have a job! :(");
                        }

                    }
                }
        );

        setListView();
    }


    public void setListView()
    {
        observableListPerson.setAll(freeWorkpositions);
        workplacesListView.setItems(observableListPerson);

        workplacesListView.setCellFactory(new Callback<ListView<Workposition>, ListCell<Workposition>>()
        {
            @Override
            public ListCell<Workposition> call(ListView<Workposition> param)
            {
                return new workListCell();
            }

        });

        workplacesListView.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                chosenWorkposition = (Workposition) workplacesListView.getSelectionModel().getSelectedItem();
            }
        });
    }

    @Override
    public void getMessage(Object object)
    {
        if (object instanceof Workposition)
            chosenWorkposition = (Workposition) object;
    }
}

