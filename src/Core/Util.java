package Core;

import Core.Enums.EducationalLayer;
import Core.Enums.IndustryType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Util
{
    //Society
    public static final Integer NUM_PERS_DEFAULT = 40;
    public static final Integer RATION_BASIC_EDU = 20;
    public static final Integer RATION_APP_EDU = 35;
    public static final Integer RATION_HIGHER_EDU = 30;
    public static final Integer RATION_UNIVERSITY_EDU = 15;

    //Person
    public static final Integer DEFAULT_AGE = 0;
    public static final String DEFAULT_FIRSTNAME = "FIRSTNAME";
    public static final String DEFAULT_LASTNAME = "LASTNAME";
    public static final EducationalLayer DEFAULT_EDU = EducationalLayer.EDU_BASE;
    public static final Integer PERSON_DEFAULT_DEPOSIT = 1000;
    public static final int INIT_BASE_HAPPINESS = 100;
    public static final int THRESHOLD_VERY_POOR = 1000;
    public static final int THRESHOLD_POOR = 1400;
    public static final int THRESHOLD_MEDIUM = 2000;
    public static final int THRESHOLD_RICH = 3500;

    //Basis needs/wishes per Month; if not available => unhappy, extras make happy
    public static final Integer BASIC_NEED_FOOD = 30;
    public static final Integer BASIC_NEED_CLOTHS = 2;
    public static final Integer BASIC_NEED_HOUSING = 1;
    public static final Integer BASIC_NEED_ENERGY = 30;
    public static final Integer BASIC_NEED_ELECTRONICS = 1;
    public static final Integer BASIC_NEED_HEALTH = 1;
    public static final Integer BASIC_NEED_TRAFFIC = 15;
    public static final Integer BASIC_NEED_EDUCATION = 1;
    public static final Integer BASIC_NEED_SPARETIME = 10;

    //Who much they want to spend
    public static final Integer BUDGET_DEFAULT_WEIGHT_FOOD = 13;
    public static final Integer BUDGET_DEFAULT_WEIGHT_CLOTHS = 4;
    public static final Integer BUDGET_DEFAULT_WEIGHT_HOUSING = 20;
    public static final Integer BUDGET_DEFAULT_WEIGHT_ENERGY = 13;
    public static final Integer BUDGET_DEFAULT_WEIGHT_ELECTRONICS = 5;
    public static final Integer BUDGET_DEFAULT_WEIGHT_HEALTH = 5;
    public static final Integer BUDGET_DEFAULT_WEIGHT_TRAFFIC = 14;
    public static final Integer BUDGET_DEFAULT_WEIGHT_EDUCATION = 1;
    public static final Integer BUDGET_DEFAULT_WEIGHT_SPARETIME = 16;
    public static final Integer BUDGET_DEFAULT_WEIGHT_SAVING = 6;
    public static final Integer BUDGET_DEFAULT_WEIGHT_OTHER_AND_SERVICES = 3;

    //Company
    public static final Integer NUM_BASE_EDU_WORKPLACES = 2;
    public static final Integer NUM_APPR_EDU_WORKPLACES = 0;
    public static final Integer NUM_HIGH_EDU_WORKPLACES = 1;
    public static final Integer NUM_UNIV_EDU_WORKPLACES = 0;
    public static final Integer COMP_DEFAULT_DEPOSIT = 100000;
    public static final IndustryType COMP_DEFAULT_INDUSTRY = IndustryType.FOOD;
    public static final Integer PAYGRADE_GROSS_INCOME_A = 1000;
    public static final Integer PAYGRADE_GROSS_INCOME_B = 1300;
    public static final Integer PAYGRADE_GROSS_INCOME_C = 1600;
    public static final Integer PAYGRADE_GROSS_INCOME_D = 2000;
    public static final Integer PAYGRADE_GROSS_INCOME_E = 3000;
    public static final Integer PAYGRADE_GROSS_INCOME_F = 6000;
    public static final Integer COMPANY_UTIL_CAPACITY_DEFAULT = 45;
    public static final Integer UTILITY_SMALL = 1;
    public static final Integer UTILITY_MEDIUM = 3;
    public static final Integer UTILITY_LARGE = 5;


    //Economy
    //public static final Integer DEFAULT_NUM_COMPANIES = 3;//IndustryType.getEnumSize();
    public static final Integer DEFAULT_NUM_COMPANIES = IndustryType.getEnumSize();

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

    public static boolean tryParseInt(String value)
    {
        try
        {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e)
        {
            return false;
        }
    }

    public static String[] readFirstLineFromTxt(String pathToCsv)
    {//Reads first line
        String row = null;
        String[] data = null;
        try
        {
            BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
            row = csvReader.readLine();
            data = row.split(",");
            csvReader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return data;
    }

    public static List<String[]> readAllLineFromTxt(String pathToCsv)
    {//Reads all line
        String row = null;
        List<String[]> data = new ArrayList<>();
        try
        {
            BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
            while((row = csvReader.readLine()) != null)
            {
                data.add(row.split(","));
            }
            csvReader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return data;
    }

}
