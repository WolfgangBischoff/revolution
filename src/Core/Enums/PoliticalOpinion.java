package Core.Enums;

public enum PoliticalOpinion
{
    Unpolitical(0), SocialDemocratic(1), Conservativ(2), Enviromental(3);
    int value;

    PoliticalOpinion(int v)
    {
        value = v;
    }

    public static PoliticalOpinion fromInt(int value)
    {
        switch (value)
        {
            case 0: return Unpolitical;
            case 1: return SocialDemocratic;
            case 2: return Conservativ;
            case 3: return Enviromental;
            default: return Unpolitical;
        }
    }

}
