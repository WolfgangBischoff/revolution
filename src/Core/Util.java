package Core;

import Core.EducationalLayer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Random;

public class Util
{
    //Society
    public static final Integer RATION_BASIC_EDU = 20;
    public static final Integer RATION_APP_EDU = 35;
    public static final Integer RATION_HIGHER_EDU = 30;
    public static final Integer RATION_UNIVERSITY_EDU = 15;

    //Person
    public static final Integer DEFAULT_AGE = 0;
    public static final String DEFAULT_FIRSTNAME = "FIRSTNAME";
    public static final String DEFAULT_LASTNAME = "LASTNAME";
    public static final EducationalLayer DEFAULT_EDU = EducationalLayer.EDU_UNKNOWN;
    public static final Integer PERSON_DEFAULT_DEPOSIT = 0;
    public static final int INIT_BASE_HAPPINESS = 100;
    public static final int THRESHOLD_VERY_POOR = 1000;
    public static final int THRESHOLD_POOR = 1400;
    public static final int THRESHOLD_MEDIUM = 2000;
    public static final int THRESHOLD_RICH = 3500;

    //Company
    public static final Integer NUM_BASE_EDU_WORK = 3;
    public static final Integer NUM_APPR_EDU_WORK = 4;
    public static final Integer NUM_HIGH_EDU_WORK = 2;
    public static final Integer NUM_UNIV_EDU_WORK = 1;
    public static final Integer COMP_DEFAULT_DEPOSIT = 100000;

    //Economy


    //Government

    //Interpreter
    public static final Integer INTER_DEF_NUM_EDU_BASE = 3;
    public static final Integer INTER_DEF_NUM_EDU_APPR = 4;
    public static final Integer INTER_DEF_NUM_EDU_HIGH = 2;
    public static final Integer INTER_DEF_NUM_EDU_UNIV = 1;
    public static final Integer INTER_DEF_NUM_COMPANIES = 4;


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
