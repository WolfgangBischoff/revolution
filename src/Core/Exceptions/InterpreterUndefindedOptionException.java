package Core.Exceptions;

public class InterpreterUndefindedOptionException extends IllegalArgumentException
{
    public InterpreterUndefindedOptionException(String methodName, String option)
    {
        super("In " + methodName + "\n\t" + "\t\"" + option + "\" option undefined");
    }
}

