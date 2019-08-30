package Core;

import java.util.ArrayList;
import static Core.Util.*;

public class Society {

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
        for(Person person : people)
            person.calcState();
        societyStatistics.calcAll();
    }

    //Prints
    @Override
    public String toString()
    {
        return "Society: " +
                "people=" + people +
                '}';
    }

    public String printSocPeople()
    {
        if(people.size() == 0)
            return "Society has no members";

        StringBuilder ret = new StringBuilder();
        for(Person person : people)
            ret.append("\nPerson: " +
                    person.printBasicData() + "\n\t" +
                    person.printHappiness()  + "\n\t" +
                    person.printLayers() + "\n\t" +
                    person.printEconomical()
            );
        return ret.toString();
    }

    public String printSocStatistics()
    {
        return societyStatistics.toString();
    }

    /***
     * Creates a population randomly based on defined educational Ratios
     * @param numberPersons Number of people
     */
    public void populateSociety(Integer numberPersons)
    {
        people = new ArrayList<>();
        //Create People
        for(int i=0; i<numberPersons; i++)
        {
            //Random generation of EduLayer
            Integer[] ratios = {RATION_BASIC_EDU,RATION_APP_EDU,RATION_HIGHER_EDU,RATION_UNIVERSITY_EDU};
            people.add(
                    new Person(
                            EducationalLayer.fromInt(Statistics.randomWithRatio(ratios))));
        }
        societyStatistics =  new SocietyStatistics(this);
    }

    public void populateSociety(Integer baseEdu, Integer apprEdu, Integer higherEdu, Integer unicEdu)
    {
        people = new ArrayList<>();

        for(int i=0; i < baseEdu; i++)
        {
            people.add(new Person(EducationalLayer.EDU_BASE));
        }
        for(int i=0; i < apprEdu; i++)
        {
            people.add(new Person(EducationalLayer.EDU_APPRENTICESHIP));
        }
        for(int i=0; i < higherEdu; i++)
        {
            people.add(new Person(EducationalLayer.EDU_HIGHER));
        }
        for(int i=0; i < unicEdu; i++)
        {
            people.add(new Person(EducationalLayer.EDU_UNIVERSITY));
        }
        societyStatistics = new SocietyStatistics(this);
    }

    public void addPersonRnd(Integer numberPersons)
    {
        for(int i=0; i<numberPersons; i++)
        {
            //Random generation of EduLayer
            Integer[] ratios = {RATION_BASIC_EDU,RATION_APP_EDU,RATION_HIGHER_EDU,RATION_UNIVERSITY_EDU};
            people.add(
                    new Person(
                            EducationalLayer.fromInt(Statistics.randomWithRatio(ratios))));
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
        if(singleton == null)
        {
            singleton = new Society();
            return singleton;
        }

        else
            return singleton;
    }

}
