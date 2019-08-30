package Core;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GuiPersonGrid extends GridPane
{
    public GuiPersonGrid(Person person)
    {
        Person test = person;
        test.calcState();

        setHgap(5);
        setVgap(5);
        setGridLinesVisible(true);

        GridPane dataPane = new GridPane();

        Text name = new Text(test.name.toString());
        name.setFont(Font.font("Verdana", 30));
        name.setFill(Color.RED);

        add(name, 0,0);

        Label ageLabel = new Label("Age");
        dataPane.add(ageLabel, 0,1);
        Text age = new Text(test.age.toString());
        dataPane.add(age, 1,1);

        Text edu = new Text(test.educationalLayer.toString());
        dataPane.add(edu, 0,2);

        Text pol = new Text(test.politicalOpinion.toString());
        dataPane.add(pol, 0,3);

        Text grIncome = new Text("" + test.getGrossIncome());
        Label grIncomeLabel = new Label("Gross Income: ");
        dataPane.add(grIncome, 1,4);
        dataPane.add(grIncomeLabel, 0,4);

        Text netIncome = new Text("" + test.getNettIncome());
        Label netIncomeLabel = new Label("Nett Income: ");
        dataPane.add(netIncome, 1,5);
        dataPane.add(netIncomeLabel, 0,5);

        Text depo = new Text("" + test.getDeposit());
        Label depoLabel = new Label("Deposit: ");
        dataPane.add(depo, 1,6);
        dataPane.add(depoLabel, 0,6);

        add(dataPane, 0, 1);
    }
}
