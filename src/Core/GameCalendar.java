package Core;

import Core.Enums.SpecialDayOfYear;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static Core.Util.CALENDER_START_DAY;
import static Core.Util.CALENDER_START_MONTH;
import static Core.Util.CALENDER_START_YEAR;


public class GameCalendar
{


    LocalDate date;


    public GameCalendar()
    {
       date =  LocalDate.of(CALENDER_START_YEAR,CALENDER_START_MONTH, CALENDER_START_DAY);
    }

    public LocalDate getDate()
    {
        return date;
    }


    public void nextDay(int number)
    {
        for (int i = 0; i < number; i++)
            nextDay();
    }

    public void nextDay()
    {
        date = date.plusDays(1);
    }

    public int calcWorkdaysCurrentMonth()
    {
        LocalDate day = date.withDayOfMonth(1);
        int numberWorkdays = 0;
        for(int i=0; i<day.getMonth().length(day.isLeapYear()); i++)
        {
            if(checkYearlySpecialDay(day) == SpecialDayOfYear.WORKDAY)
                numberWorkdays++;
            day = day.plusDays(1);
        }
        return numberWorkdays;
    }

    public SpecialDayOfYear checkYearlySpecialDay(LocalDate date)
    {
        if(date.getMonth() == Month.JANUARY)
        {
            if(date.getDayOfMonth() == 1)
                return SpecialDayOfYear.NEWYEAR;
            if(date.getDayOfMonth() == 2)
                return SpecialDayOfYear.DAYOFLEADER;
        }

        if(date.getMonth() == Month.DECEMBER)
        {
            if(date.getDayOfMonth() == 24)
                return SpecialDayOfYear.CHRISTMAS;
            if(date.getDayOfMonth() == 31)
                return SpecialDayOfYear.SYLVESTER;
        }
        return SpecialDayOfYear.WORKDAY;
    }


    @Override
    public String toString()
    {
        return dataDate();
    }

    public String dataDate()
    {
        return date.toString();
    }

    public String dataDateWeekday()
    {
        Locale locale = new Locale("en", "US");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd. LLLL YYYY", locale);
        return formatter.format(date);
    }


}
