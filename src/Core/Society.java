package Core;

import Core.Enums.EducationalLayer;
import Core.Enums.IndustryType;
import Core.Enums.InterpreterKeyword;

import java.util.*;

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
    public void calcSocietyDaily()
    {
        for (Person person : people)
            person.calcStateDaily();
        societyStatistics.calcAll();
    }

    public void calcSocietyMonthly()
    {
        for (Person person : people)
            person.calcStateMonthly();
    }


    public void shop()
    {
        //FOR ALL Industries
        IndustryType type = IndustryType.FOOD;

        for(Person p : people)
        {
            p.shop();
        }
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
            ret.append(person.dataBasis() + "\n");
            if (special == null)
                ret.append(
                                person.dateHappiness() + "\n\t" +
                                person.dataLayer() + "\n\t" +
                                person.dataEconomical());
            else if (special == InterpreterKeyword.BUDGET)
            {
                ret.append(person.dataBudget());
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
