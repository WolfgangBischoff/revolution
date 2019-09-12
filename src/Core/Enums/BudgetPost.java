package Core.Enums;

//https://www.musterhaushalt.de/durchschnitt/einkommen-und-ausgaben/singlehaushalt/
//Assumtion all products are cosumer goods
public enum BudgetPost
{
    FOOD, CLOTHS, HOUSING, ENERGY, ELECTRONICS, HEALTH, TRAFFIC, EDUCATION,
    SPARETIME,
    SAVING, OTHER_AND_SERVICES;

    public static BudgetPost fromInt(int val)
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
            case 9:
                return SAVING;
            case 10:
                return OTHER_AND_SERVICES;
            default:
                throw new RuntimeException("BudgetPost unknown");
        }
    }
    public static int getEnumSize()
    {
        return BudgetPost.values().length;
    }
}
