package Core;

import Core.Enums.Season;

import java.time.DayOfWeek;

public class Calender
{
    static public final int NUMER_WEEKS_PER_SEASON = 4;

    Integer year;
    Season seasonOfYear;
    Integer weekOfSeason;
    DayOfWeek dayOfWeek;

    public Calender()
    {
year = 2019;
seasonOfYear = Season.WINTER;
weekOfSeason = 3;
dayOfWeek = DayOfWeek.THURSDAY;
    }

    public void nextDay()
    {
        boolean weekOfSeasonChanged = false;
        boolean seasonOfYearChanged = false;
        boolean yearChanged = false;

        int pastDayofWeek = dayOfWeek.getValue();
        if(pastDayofWeek++ >= 8)
        {
         weekOfSeasonChanged = true;
        }
        dayOfWeek = DayOfWeek.of(pastDayofWeek%7+1);

        if(weekOfSeasonChanged)
            weekOfSeason++;
        if(weekOfSeason > NUMER_WEEKS_PER_SEASON)
        {
            seasonOfYearChanged = true;
            weekOfSeason = weekOfSeason%NUMER_WEEKS_PER_SEASON;
        }

        int pastSeason = seasonOfYear.getValue();
        if(seasonOfYearChanged)
            pastSeason++;
        if(pastSeason > 3)
        {
            yearChanged = true;
        }
        seasonOfYear = Season.fromInt(pastSeason % 3);

        if(yearChanged)
            year++;
    }

    public String dataToday()
    {
        return dayOfWeek + " Week: " + weekOfSeason + " " + seasonOfYear + " " + year;
    }

}
