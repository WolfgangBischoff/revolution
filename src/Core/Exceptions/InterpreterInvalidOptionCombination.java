package Core.Exceptions;

public class InterpreterInvalidOptionCombination extends IllegalArgumentException
{
    public InterpreterInvalidOptionCombination(String methodName, String[] option)
    {
        super("In " + methodName + "\n\t" + " invalid option combination");
    }
}
