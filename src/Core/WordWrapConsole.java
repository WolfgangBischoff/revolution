package Core;

public class WordWrapConsole extends Console
        {
    public WordWrapConsole(Simulation sim)
        {
        super(sim);
        textArea.setWrapText(true);
        }
}
