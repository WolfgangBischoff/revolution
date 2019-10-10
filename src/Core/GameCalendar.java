package Core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class GameCalendar
{


    LocalDate date;


    public GameCalendar()
    {
       date =  LocalDate.of(2019,9,25);
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
