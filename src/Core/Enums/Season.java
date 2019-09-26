package Core.Enums;

public enum Season
{
    SPRING(0), SUMMER(1), FALL(2), WINTER(3);
    int value;
    Season(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public static Season fromInt(int value)
    {
        switch (value)
        {
            case 0: return SPRING;
            case 1: return SUMMER;
            case 2: return FALL;
            case 3: return WINTER;
            default: throw new RuntimeException("ENUM value unused: " + value);
        }
    }

}
