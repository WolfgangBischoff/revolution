package Core.Enums;

public enum IndustryType
{
    FOOD, CLOTHS, HOUSING, ENERGY, ELECTRONICS, HEALTH, TRAFFIC, EDUCATION,
    SPARETIME;

    public static IndustryType fromInt(int val)
    {
        switch (val)
        {
            case 0:
                return FOOD;
            case 1:
                return CLOTHS;
            case 2:
                return HOUSING;
            case 3:
                return ENERGY;
            case 4:
                return ELECTRONICS;
            case 5:
                return HEALTH;
            case 6:
                return TRAFFIC;
            case 7:
                return EDUCATION;
            case 8:
                return SPARETIME;
            default:
                throw new RuntimeException("IndustryType unknown");
        }
    }




    public static int getEnumSize()
    {
        return IndustryType.values().length;
    }
}
