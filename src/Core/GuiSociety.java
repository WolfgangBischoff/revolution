package Core;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GuiSociety extends GridPane
{
    Simulation simulation;
    Society society;

    Text numberPersonsLabel = new Text("Population: ");
    Text numberPersons;
    Text avgGrossIncomeLabel = new Text("Average Gross Income: ");
    Text avgGrossIncome;
    Button refreshBttn;

    public GuiSociety()
    {
        simulation = Simulation.getSingleton();
        society = simulation.society;

        Text headline = new Text("Society Overview");
        headline.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        add(headline, 0,0);

        add(numberPersonsLabel, 0, 1);
        numberPersons = new Text("" + society.getSocietyStatistics().persons.size());
        add(numberPersons, 1,1);

        add(avgGrossIncomeLabel, 0,2);
        avgGrossIncome = new Text(society.getSocietyStatistics().avgGrossIncome.toString());
        add(avgGrossIncome, 1,2);

        refreshBttn = new Button("Refresh");
        refreshBttn.setOnAction(
                (none) -> calc()
        );
        add(refreshBttn, 1,3);
    }

    public void calc()
    {
        numberPersons.setText("" + society.getSocietyStatistics().persons.size());
        avgGrossIncome.setText(society.getSocietyStatistics().avgGrossIncome.toString());
    }
}
