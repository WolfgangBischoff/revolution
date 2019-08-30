package Core.Exceptions;

public class InterpreterNoParametersAlloweException extends IllegalArgumentException
{
    public InterpreterNoParametersAlloweException(String methodName, String option)
    {
        super("In " + methodName + "\n\t" + option +" must not have arguments, did your forget \"-\"?");
    }
}
