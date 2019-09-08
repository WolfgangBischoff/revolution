package Core.Exceptions;

public class InterpreterInvalidArgumentException extends IllegalArgumentException
{
    public InterpreterInvalidArgumentException(String methodName, String option, String possibleOptions)
    {
        super("In " + methodName + "\n\t" + "\t\"" + option + "\" option undefined.\nPossible: " + possibleOptions);
    }
}

