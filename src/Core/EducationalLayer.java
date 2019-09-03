package Core;

public enum EducationalLayer
{
    EDU_CHILD(0), EDU_BASE(1),EDU_APPRENTICESHIP(2), EDU_HIGHER(3), EDU_UNIVERSITY(4),  ;

    private int IntNr;

    EducationalLayer(int nr)
    {
        this.IntNr = nr;
    }

    public int getInt()
    {
        return IntNr;
    }

    public static EducationalLayer fromInt(int n)
    {
        switch(n) {
            case 0:
                return EDU_CHILD;
            case 1:
                return EDU_BASE;
            case 2:
                return EDU_APPRENTICESHIP;
            case 3:
                return EDU_HIGHER;
            case 4:
                return EDU_UNIVERSITY;
            default:
                return null;
        }
    }
}
