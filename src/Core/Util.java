package Core;

import Core.Enums.EducationalLayer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Random;

public class Util
{
    //Society
    public static final Integer NUM_PERS_DEFAULT = 12;
    public static final Integer RATION_BASIC_EDU = 20;
    public static final Integer RATION_APP_EDU = 35;
    public static final Integer RATION_HIGHER_EDU = 30;
    public static final Integer RATION_UNIVERSITY_EDU = 15;

    //Person
    public static final Integer DEFAULT_AGE = 0;
    public static final String DEFAULT_FIRSTNAME = "FIRSTNAME";
    public static final String DEFAULT_LASTNAME = "LASTNAME";
    public static final EducationalLayer DEFAULT_EDU = EducationalLayer.EDU_BASE;
    public static final Integer PERSON_DEFAULT_DEPOSIT = 100;
    public static final int INIT_BASE_HAPPINESS = 100;
    public static final int THRESHOLD_VERY_POOR = 1000;
    public static final int THRESHOLD_POOR = 1400;
    public static final int THRESHOLD_MEDIUM = 2000;
    public static final int THRESHOLD_RICH = 3500;

    public static final Integer BUDGET_DEFAULT_WEIGHT_FOOD = 13;
    public static final Integer BUDGET_DEFAULT_WEIGHT_CLOTHS = 4;
    public static final Integer BUDGET_DEFAULT_WEIGHT_HOUSING = 20;
    public static final Integer BUDGET_DEFAULT_WEIGHT_ENERGY = 13;
    public static final Integer BUDGET_DEFAULT_WEIGHT_ELECTRONICS = 5;
    public static final Integer BUDGET_DEFAULT_WEIGHT_HEALTH = 5;
    public static final Integer BUDGET_DEFAULT_WEIGHT_TRAFFIC = 14;
    public static final Integer BUDGET_DEFAULT_WEIGHT_EDUCATION = 1;
    public static final Integer BUDGET_DEFAULT_WEIGHT_NIGHTLIFE = 5;
    public static final Integer BUDGET_DEFAULT_WEIGHT_SPARETIME = 11;
    public static final Integer BUDGET_DEFAULT_WEIGHT_SAVING = 6;
    public static final Integer BUDGET_DEFAULT_WEIGHT_OTHER_AND_SERVICES = 3;

    //Company
    public static final Integer NUM_BASE_EDU_WORK = 3;
    public static final Integer NUM_APPR_EDU_WORK = 4;
    public static final Integer NUM_HIGH_EDU_WORK = 2;
    public static final Integer NUM_UNIV_EDU_WORK = 1;
    public static final Integer COMP_DEFAULT_DEPOSIT = 100000;

    //Economy
    public static final Integer DEFAULT_NUM_EDU_BASE = 3;
    public static final Integer DEFAULT_NUM_EDU_APPR = 4;
    public static final Integer DEFAULT_NUM_EDU_HIGH = 2;
    public static final Integer DEFAULT_NUM_EDU_UNIV = 1;
    public static final Integer DEFAULT_NUM_COMPANIES = 4;

    //Government

    //Interpreter



    //Random
    static Random rand = new Random();
    public static Random getRandom()
    {
        return rand;
    }

    //Round
    public static Double roundTwoDigits(Double input)
    {
        DecimalFormatSymbols point = new DecimalFormatSymbols();
        point.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.##", point);
        return Double.valueOf(df.format(input));
    }

    public static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
