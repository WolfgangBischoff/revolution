package Core;

import Core.Enums.Season;
import java.time.DayOfWeek;

public class GameDate implements Comparable<GameDate>
{
    static public final int NUMBER_WEEKS_PER_SEASON = 4;

    Integer year;
    Season seasonOfYear;
    Integer weekOfSeason;
    DayOfWeek dayOfWeek;

    public GameDate()
    {
        year = 2019;
        seasonOfYear = Season.WINTER;
        weekOfSeason = 3;
        dayOfWeek = DayOfWeek.THURSDAY;
    }

    public GameDate(int DayOfWeek, int weekOfSeason, int seasonOfYear, int year)
    {
        dayOfWeek = java.time.DayOfWeek.of(DayOfWeek);
        this.weekOfSeason = weekOfSeason;
        this.seasonOfYear = Season.fromInt(seasonOfYear);
        this.year = year;
    }

    public GameDate clone()
    {
        return new GameDate(dayOfWeek.getValue(), weekOfSeason, seasonOfYear.getValue(), year);
    }

    public void nextDay(int number)
    {
        for (int i = 0; i < number; i++)
            nextDay();
    }

    public void nextDay()
    {
        boolean weekOfSeasonChanged = false;
        boolean seasonOfYearChanged = false;
        boolean yearChanged = false;

        int pastDayofWeek = dayOfWeek.getValue();
        if (pastDayofWeek >= 7)
        {
            weekOfSeasonChanged = true;
        }
        dayOfWeek = DayOfWeek.of(pastDayofWeek % 7 + 1);

        if (weekOfSeasonChanged)
            weekOfSeason++;
        if (weekOfSeason > NUMBER_WEEKS_PER_SEASON)
        {
            seasonOfYearChanged = true;
            weekOfSeason = weekOfSeason % NUMBER_WEEKS_PER_SEASON;
        }

        int pastSeason = seasonOfYear.getValue();
        if (seasonOfYearChanged)
        {
            pastSeason++;
            seasonOfYear = Season.fromInt(pastSeason % 4);
        }
        if (pastSeason > 3)
        {
            yearChanged = true;
        }

        if (yearChanged)
            year++;
    }

    public String dataToday()
    {
        return dayOfWeek + " Week: " + weekOfSeason + " " + seasonOfYear + " " + year;
    }

    @Override
    public int compareTo(GameDate o)
    {

        return 0;
    }
}
