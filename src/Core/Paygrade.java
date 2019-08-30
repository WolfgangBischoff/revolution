package Core;

public enum Paygrade
{
    A,B,C,D,E,F;

    public static Paygrade fromInt (Integer i)
    {
        switch (i)
        {
            case 0: return A;
            case 1: return B;
            case 2: return C;
            case 3: return D;
            case 4: return E;
            case 5: return F;
            default: return null;
        }
    }
}
