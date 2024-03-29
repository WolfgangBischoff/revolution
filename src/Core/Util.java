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
    //General
    public static final Integer RESOLUTION_WIDTH = 1280;
    public static final Integer RESOLUTION_HEIGTH = 720;
    public static final Integer CALENDER_START_DAY = 22;
    public static final Integer CALENDER_START_MONTH = 12;
    public static final Integer CALENDER_START_YEAR = 2019;

    //Society
    public static final Integer NUM_BASIC_EDU = 2;
    public static final Integer NUM_APP_EDU = 2;
    public static final Integer NUM_HIGHER_EDU = 2;
    public static final Integer NUM_UNIVERSITY_EDU = 1;
    public static final Integer NUM_PERS_DEFAULT = 7; //Random gen
    //Society Education
    public static final Integer RATION_BASIC_EDU = 20;
    public static final Integer RATION_APP_EDU = 30;
    public static final Integer RATION_HIGHER_EDU = 30;
    public static final Integer RATION_UNIVERSITY_EDU = 20;

    //Person
    public static final Integer DEFAULT_AGE = 0;
    public static final String DEFAULT_FIRSTNAME = "FIRSTNAME";
    public static final String DEFAULT_LASTNAME = "LASTNAME";
    public static final EducationalLayer DEFAULT_EDU = EducationalLayer.EDU_BASE;
    public static final Integer PERSON_DEFAULT_DEPOSIT = 1500;
    public static final int INIT_BASE_HAPPINESS = 100;
    public static final int THRESHOLD_VERY_POOR = 1000;
    public static final int THRESHOLD_POOR = 1400;
    public static final int THRESHOLD_MEDIUM = 2000;
    public static final int THRESHOLD_RICH = 3500;

    //Who much they want to spend
    public static final Integer BUDGET_DEFAULT_WEIGHT_FOOD = 60;
    public static final Integer BUDGET_DEFAULT_WEIGHT_CLOTHS = 10;
    public static final Integer BUDGET_DEFAULT_WEIGHT_HOUSING = 20;
    public static final Integer BUDGET_DEFAULT_WEIGHT_ENERGY = 13;
    public static final Integer BUDGET_DEFAULT_WEIGHT_ELECTRONICS = 10;
    public static final Integer BUDGET_DEFAULT_WEIGHT_HEALTH = 10;
    public static final Integer BUDGET_DEFAULT_WEIGHT_TRAFFIC = 10;
    public static final Integer BUDGET_DEFAULT_WEIGHT_EDUCATION = 10;
    public static final Integer BUDGET_DEFAULT_WEIGHT_SPARETIME = 10;
    public static final Integer BUDGET_DEFAULT_WEIGHT_SAVING = 6;
    public static final Integer BUDGET_DEFAULT_WEIGHT_OTHER_AND_SERVICES = 3;

    //Company
    public static final Integer NUM_BASE_EDU_WORKPLACES = 3;
    public static final Integer NUM_APPR_EDU_WORKPLACES = 5;
    public static final Integer NUM_HIGH_EDU_WORKPLACES = 3;
    public static final Integer NUM_UNIV_EDU_WORKPLACES = 2;
    public static final Integer COMP_DEFAULT_DEPOSIT = 100000;
    public static final IndustryType COMP_DEFAULT_INDUSTRY = IndustryType.FOOD;
    public static final Integer PAYGRADE_GROSS_INCOME_A = 1500;
    public static final Integer PAYGRADE_GROSS_INCOME_B = 4500;
    public static final Integer PAYGRADE_GROSS_INCOME_C = 8000;
    public static final Integer PAYGRADE_GROSS_INCOME_D = 24000;
    public static final Integer PAYGRADE_GROSS_INCOME_E = 3000;
    public static final Integer PAYGRADE_GROSS_INCOME_F = 6000;
    public static final Integer MAX_CAPACITY_DEFAULT = 6000;

    //Economy
    public static final Integer DEFAULT_NUM_COMPANIES = 4;//IndustryType.getEnumSize();
    public static final Integer MARKET_ANAL_PERIODS_REMEMBERED = 5;

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
        String[] rawdata = null;
        try
        {
            BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
            row = csvReader.readLine();
            rawdata = row.split(",");
            csvReader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        String[] trimmed = new String[rawdata.length];
        for(int i=0; i<rawdata.length; i++)
            trimmed[i] = rawdata[i].trim();

        return trimmed;
    }

    public static List<String[]> readAllLineFromTxt(String pathToCsv, boolean ignoreFirstLine)
    {//Reads all line
        String row = null;
        Integer linecounter = 0;
        List<String[]> data = new ArrayList<>();
        try
        {
            BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
            while((row = csvReader.readLine()) != null)
            {
                if(ignoreFirstLine && linecounter == 0)
                {
                    linecounter++;
                    continue;
                }
                data.add(row.split(","));
                linecounter++;
            }
            csvReader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return data;
    }

}
