package Core.Enums;

public enum Paygrade
{
    A,B,C,D,E,F;

    public static Paygrade fromInt (Integer i)
    {
        switch (i)
        {
            case 1: return A;
            case 2: return B;
            case 3: return C;
            case 4: return D;
            case 5: return E;
            case 6: return F;
            default: return null;
        }
    }
}
