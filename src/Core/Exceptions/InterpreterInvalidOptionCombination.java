package Core.Exceptions;

public class InterpreterInvalidOptionCombination extends IllegalArgumentException
{
    public InterpreterInvalidOptionCombination(String methodName, String[] option)
    {
        /*StringBuilder sb = new StringBuilder()
                for(String args : option)
                    sb.append(args + " ");*/
        super("In " + methodName + "\n\t" + " invalid option combination");
    }
}
