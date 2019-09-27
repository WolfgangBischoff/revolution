package Core;

import Core.Enums.Season;

import java.time.DayOfWeek;
import java.util.Objects;

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

    public String dataDate()
    {
        return dayOfWeek + " Week: " + weekOfSeason + " " + seasonOfYear + " " + year;
    }

    @Override
    public int compareTo(GameDate o)
    {
        int otherIsLater = 1;
        int otherIsEarlier = -1;

        if (year > o.year)
            return otherIsEarlier;
        if (seasonOfYear.getValue() > o.seasonOfYear.getValue())
            return otherIsEarlier;
        if (weekOfSeason > o.weekOfSeason)
            return otherIsEarlier;
        if (dayOfWeek.getValue() > o.dayOfWeek.getValue())
            return otherIsEarlier;

        if (year == o.year &&
                seasonOfYear.getValue() == o.seasonOfYear.getValue()
                && weekOfSeason == o.weekOfSeason
                && dayOfWeek.getValue() == o.dayOfWeek.getValue())
            return 0;

        return otherIsLater;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameDate gameDate = (GameDate) o;
        return Objects.equals(year, gameDate.year) &&
                seasonOfYear == gameDate.seasonOfYear &&
                Objects.equals(weekOfSeason, gameDate.weekOfSeason) &&
                dayOfWeek == gameDate.dayOfWeek;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(year, seasonOfYear, weekOfSeason, dayOfWeek);
    }
}
