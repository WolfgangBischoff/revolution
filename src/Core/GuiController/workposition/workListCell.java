package Core.GuiController.workposition;

import Core.GuiController.Controller;
import Core.Workposition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class workListCell extends ListCell<Workposition>
{
    @Override
    public void updateItem(Workposition workposition, boolean empty)
    {
        super.updateItem(workposition, empty);
        if (workposition != null)
        {
            WorkpositionData data = new WorkpositionData(workposition);
            data.setInfo();
            setGraphic(data.getGridpane());
        }
    }


    class WorkpositionData
    {
        @FXML
        private GridPane gridpane;
        @FXML
        private Text positionName;
        @FXML
        private Label salary, industry;

        Workposition workposition;

        public WorkpositionData(Workposition workposition)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../fxml/workposition/workListCell.fxml"));
            fxmlLoader.setController(this);
            try
            {
                fxmlLoader.load();
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
            this.workposition = workposition;
        }

        public void setInfo()
        {
            positionName.setText(workposition.getNeededEducation().name());
            salary.setText(workposition.getGrossIncomeWork().toString());
            industry.setText(workposition.getCompany().getIndustry().name());
        }

        public GridPane getGridpane()
        {
            return gridpane;
        }
    }
}

