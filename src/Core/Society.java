package Core;

import Core.Enums.BudgetPost;
import Core.Enums.EducationalLayer;
import Core.Enums.IndustryType;
import Core.Enums.InterpreterKeyword;

import java.util.ArrayList;

import static Core.Util.*;

public class Society
{

    private static Society singleton = null;
    private ArrayList<Person> people = new ArrayList<>();
    private SocietyStatistics societyStatistics;

    //Constructors
    private Society()
    {
        societyStatistics = new SocietyStatistics(this);
    }

    //Calculations
    public void calcSociety()
    {
        for (Person person : people)
            person.calcState();
        societyStatistics.calcAll();
    }

    public void shop()
    {
       // Market.getMarket().initNewDay();
        for(Person p : people)
            p.shop();
        societyStatistics.calcAll();
        Simulation.getSingleton().getEconomy().calc();
    }

    //Prints
    @Override
    public String toString()
    {
        return "Society: " +
                "people=" + people +
                '}';
    }

    public String printSocPeople(InterpreterKeyword special)
    {
        if (people.size() == 0)
            return "Society has no members";

        StringBuilder ret = new StringBuilder();
        for (Person person : people)
        {
            ret.append(person.printBasicData() + "\n");
            if (special == null)
                ret.append(
                                person.printHappiness() + "\n\t" +
                                person.printLayers() + "\n\t" +
                                person.printEconomical());
            else if (special == InterpreterKeyword.BUDGET)
            {

                ret.append(person.budgetData());
                ret.append(person.dataBestMarketOffer(BudgetPost.FOOD));
            }
            else if (special == InterpreterKeyword.CONSUME)
            {
                ret.append(person.getConsumeDataStorage().dataConsume());
            }
        }
        return ret.toString();
    }


    public void clear()
    {
        for (Person person : people)
        {
            person.quitWorkposition();
        }
        people.clear();
        societyStatistics.calcAll();
    }

    /***
     * Creates a population randomly based on defined educational Ratios
     * @param numberPersons Number of people
     */
    public void populateSociety(Integer numberPersons)
    {
        clear();
        addPersonRnd(numberPersons);
    }

    public void addPersonRnd(Integer numberPersons)
    {
        for (int i = 0; i < numberPersons; i++)
        {
            //Random generation of EduLayer
            Integer[] ratios = {RATION_BASIC_EDU, RATION_APP_EDU, RATION_HIGHER_EDU, RATION_UNIVERSITY_EDU};
            people.add(
                    new Person(
                            EducationalLayer.fromInt(1 + Statistics.randomWithRatio(ratios)))); //+1 To avoid children
        }
        societyStatistics.calcAll();
    }


    public void populateSociety(Integer baseEdu, Integer apprEdu, Integer higherEdu, Integer unicEdu)
    {
        clear();

        for (int i = 0; i < baseEdu; i++)
        {
            people.add(new Person(EducationalLayer.EDU_BASE));
        }
        for (int i = 0; i < apprEdu; i++)
        {
            people.add(new Person(EducationalLayer.EDU_APPRENTICESHIP));
        }
        for (int i = 0; i < higherEdu; i++)
        {
            people.add(new Person(EducationalLayer.EDU_HIGHER));
        }
        for (int i = 0; i < unicEdu; i++)
        {
            people.add(new Person(EducationalLayer.EDU_UNIVERSITY));
        }
        societyStatistics.calcAll();
    }


    public void addPerson(Person person)
    {
        people.add(person);
        societyStatistics.calcAll();
    }

    //Getter and Setter
    public ArrayList<Person> getPeople()
    {
        return people;
    }

    public SocietyStatistics getSocietyStatistics()
    {
        return societyStatistics;
    }


    public static Society getSociety()
    {
        if (singleton == null)
        {
            singleton = new Society();
            return singleton;
        }

        else
            return singleton;
    }

}
