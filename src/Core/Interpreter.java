package Core;

import Core.Enums.InterpreterKeyword;
import Core.Enums.PoliticalOpinion;
import Core.Exceptions.InterpreterInvalidOptionCombination;
import Core.Exceptions.InterpreterInvalidArgumentException;

import java.util.*;

import static Core.Util.*;
import static javafx.application.Platform.exit;

public class Interpreter {
    private static Interpreter instance = null;
    private Society society;
    private Economy economy;
    private Government government;
    private Console console;
    private final boolean PRINTSYSOUT = true;

    private Set<String> keywordsRandom = new HashSet<>(Arrays.asList("random", "rnd"));
    private Set<String> keywordsPerson = new HashSet<>(Arrays.asList("person", "pers", "per"));
    private Set<String> keywordsSociety = new HashSet<>(Arrays.asList("society", "soc"));
    private Set<String> keywordsEconomy = new HashSet<>(Arrays.asList("economy", "eco"));
    private Set<String> keywordsCompany = new HashSet<>(Arrays.asList("company", "comp", "com"));
    private Set<String> keywordsGovernment = new HashSet<>(Arrays.asList("government", "gov"));
    private Set<String> keywordsAdd = new HashSet<>(Arrays.asList("add"));
    private Set<String> keywordsPrint = new HashSet<>(Arrays.asList("print"));
    private Set<String> keywordsTest = new HashSet<>(Arrays.asList("test"));
    private Set<String> keywordsHelp = new HashSet<>(Arrays.asList("help", "?"));
    private Set<String> keywordsExit = new HashSet<>(Arrays.asList("quit", "exit", "end"));
    private Set<String> keywordsPopulate = new HashSet<>(Arrays.asList("populate", "pop"));
    private Set<String> keywordsCash = new HashSet<>(Arrays.asList("cash"));
    private Set<String> keywordsPay = new HashSet<>(Arrays.asList("pay"));
    private Set<String> keywordsHire = new HashSet<>(Arrays.asList("hire"));
    private Set<String> keywordsCalculate = new HashSet<>(Arrays.asList("calculate", "calc"));
    private Set<String> keywordsProduce = new HashSet<>(Arrays.asList("produce", "prod"));
    private Set<String> keywordsIncome = new HashSet<>(Arrays.asList("income", "inc"));
    private Set<String> keywordsEducation = new HashSet<>(Arrays.asList("education", "edu"));
    private Set<String> keywordsBuy = new HashSet<>(Arrays.asList("buy"));
    private Set<String> keywordsSet = new HashSet<>(Arrays.asList("set"));
    private Set<String> keywordsBudget = new HashSet<>(Arrays.asList("budget", "bud"));

    private Map<InterpreterKeyword, Set<String>> keywords = new HashMap<>();

    private String POSSIBLE_ARGUMENTS = "Need more arguments. Possible arguments: ";

    //Constructors
    private Interpreter(Society soc, Economy eco, Government gov)
    {
        society = soc;
        economy = eco;
        government = gov;
        keywords.put(InterpreterKeyword.PERSON, keywordsPerson);
        keywords.put(InterpreterKeyword.RANDOM, keywordsRandom);
        keywords.put(InterpreterKeyword.SOCIETY, keywordsSociety);
        keywords.put(InterpreterKeyword.ECONOMY, keywordsEconomy);
        keywords.put(InterpreterKeyword.COMPANY, keywordsCompany);
        keywords.put(InterpreterKeyword.GOVERNMENT, keywordsGovernment);
        keywords.put(InterpreterKeyword.ADD, keywordsAdd);
        keywords.put(InterpreterKeyword.PRINT, keywordsPrint);
        keywords.put(InterpreterKeyword.TEST, keywordsTest);
        keywords.put(InterpreterKeyword.HELP, keywordsHelp);
        keywords.put(InterpreterKeyword.POPULATE, keywordsPopulate);
        keywords.put(InterpreterKeyword.CASH, keywordsCash);
        keywords.put(InterpreterKeyword.PAY, keywordsPay);
        keywords.put(InterpreterKeyword.HIRE, keywordsHire);
        keywords.put(InterpreterKeyword.CALCULATE, keywordsCalculate);
        keywords.put(InterpreterKeyword.PRODUCE, keywordsProduce);
        keywords.put(InterpreterKeyword.EXIT, keywordsExit);
        keywords.put(InterpreterKeyword.INCOME, keywordsIncome);
        keywords.put(InterpreterKeyword.EDUCATION, keywordsEducation);
        keywords.put(InterpreterKeyword.BUY, keywordsBuy);
        keywords.put(InterpreterKeyword.SET, keywordsSet);
        keywords.put(InterpreterKeyword.BUDGET, keywordsBudget);
    }

    public void setConsole(Console console)
    {
        this.console = console;
    }

    //Helptext
    private void printGeneralHelp()
    {

        print(
                "Instructions:\n" +
                        "person print\n" +
                        "society print [income, persons]\n" +
                        "society add\n" +
                        "society populate\n" +
                        "economy print [companies]\n" +
                        "economy add\n" +
                        "economy populate\n" +
                        "economy pay\n" +
                        "government print\n" +
                        "company print\n" +
                        "company pay\n" +
                        "company produce\n" +
                        "testCash cash\n"
        );
    }

    //PathFinding
    public void readInstruction(String input)
    {
        String methodName = "readInstruction()";
        String[] param = input.split("\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?");//split along whitespaces, but respects quotation marks "two strings"
        try
        {
            processFirstParam(param);
        }
        catch (IllegalArgumentException e)
        {
            print(methodName + "\n\t" + e.getMessage());
        }
    }

    //FIRST INSTRUCTION
    private void processFirstParam(String[] inputParameters)
    {
        String methodName = "processFirstParam()";
        String possibleArguments = "[person, society, company, government, economy, test, help, exit]";
        String[] newParam = cutFirstIndexPositions(inputParameters, 1);

        if (isKeyword(inputParameters[0]))
            switch (getKeyword(inputParameters[0]))
            {
                case PERSON:
                    processSecondParamAfterPerson(newParam);
                    return;
                case SOCIETY:
                    processSecondParamAfterSociety(newParam);
                    return;
                case COMPANY:
                    processSecondParamAfterCompany(newParam);
                    return;
                case GOVERNMENT:
                    processSecondParamAfterGovernment(newParam);
                    return;
                case ECONOMY:
                    processSecondParamAfterEconomy(newParam);
                    return;
                case TEST:
                    processSecondParamAfterTest(newParam);
                    return;
                case HELP:
                    printGeneralHelp();
                    return;
                case EXIT:
                    exit();
                    return;
            }
        throw new InterpreterInvalidArgumentException(methodName, inputParameters[0], possibleArguments);
    }

    //SECOND INSTRUCTION
    private void processSecondParamAfterPerson(String[] inputParameters)
    {
        String methodName = "processSecondParamAfterPerson()";
        String possibleArguments = "[print, buy]";
        String[] newParam = cutFirstIndexPositions(inputParameters, 1);

        //just "person"
        if (inputParameters.length == 0)
        {
            print(methodName + "\n" + POSSIBLE_ARGUMENTS + "[print]");
            return;
        }

        if (isKeyword(inputParameters[0]))
            switch (getKeyword(inputParameters[0]))
            {
                case PRINT:
                    personPrint(newParam);
                    return;
                case BUY:
                    personBuy(newParam);
                    return;
            }
        throw new InterpreterInvalidArgumentException(methodName, inputParameters[0], possibleArguments);
    }


    private void processSecondParamAfterSociety(String[] inputParameters)
    {
        String methodName = "processSecondParamAfterSociety";
        String possibleArguments = "[print, add, calculate, populate]";
        String[] newParam = cutFirstIndexPositions(inputParameters, 1);

        //Just: society
        if (inputParameters.length == 0)
        {
            print(methodName + "\n" + POSSIBLE_ARGUMENTS + "[print, add, calculate, populate]");
            return;
        }

        if (isKeyword(inputParameters[0]))
            switch (getKeyword(inputParameters[0]))
            {
                case PRINT:
                    societyPrint(newParam);
                    return;
                case ADD:
                    societyAdd(newParam);
                    return;
                case CALCULATE:
                    societyCalc();
                    return;
                case POPULATE:
                    societyPopulate();
                    return;
            }
        throw new InterpreterInvalidArgumentException(methodName, inputParameters[0], possibleArguments);
    }

    private void processSecondParamAfterCompany(String[] inputParameters)
    {
        String methodName = "processSecondParamAfterCompany";
        String possibleArguments = "[print, pay, produce]";
        String[] optionPara = cutFirstIndexPositions(inputParameters, 1);
        //Just: company
        if (inputParameters.length == 0)
        {
            print(methodName + "\n" + POSSIBLE_ARGUMENTS + "[print, pay, produce]");
            return;
        }

        if (isKeyword(inputParameters[0]))
            switch (getKeyword(inputParameters[0]))
            {
                case PRINT:
                    companyPrint(optionPara);
                    return;
                case PAY:
                    companyPay(optionPara);
                    return;
                case PRODUCE:
                    companyProduce(optionPara);
                    return;
            }
        throw new InterpreterInvalidArgumentException(methodName, inputParameters[0],possibleArguments);

    }

    private void processSecondParamAfterGovernment(String[] inputParameters)
    {
        String methodName = "processSecondParamAfterGovernment";
        String possibleArguments = "[print, pay, produce]";
        String[] cutParam = cutFirstIndexPositions(inputParameters,1);
        if (inputParameters.length == 0)
        {
            print(methodName + "\n" + POSSIBLE_ARGUMENTS + "[print]");
            return;
        }

        if (isKeyword(inputParameters[0]))
            switch (getKeyword(inputParameters[0]))
            {
                case PRINT:
                    print(government);
                    return;
                case SET:
                    govermentSet(cutParam);
                    return;
            }
        throw new InterpreterInvalidArgumentException(methodName, inputParameters[0], possibleArguments);
    }

    private void processSecondParamAfterEconomy(String[] inputParameters)
    {
        String methodName = "processSecondParamAfterEconomy";
        String possibleArguments = "[print, add, populate, hire, pay, produce]";
        String[] optionPara = cutFirstIndexPositions(inputParameters, 1);
        //Just: Economy
        if (inputParameters.length == 0)
        {
            print(methodName + "\n" + POSSIBLE_ARGUMENTS + possibleArguments);
            return;
        }
        if (isKeyword(inputParameters[0]))
            switch (getKeyword(inputParameters[0]))
            {
                case ADD:
                    companyAdd(optionPara);
                    return;
                case PRINT:
                    economyPrint(optionPara);
                    return;
                case POPULATE:
                    economyPopulate();
                    return;
                case HIRE:
                    economyHire(optionPara);
                    return;
                case PAY:
                    economyPaySalary(optionPara);
                    return;
                case PRODUCE:
                    economy.comaniesProduce(); return;
            }
        throw new InterpreterInvalidArgumentException(methodName, inputParameters[0], possibleArguments);
    }

    private void processSecondParamAfterTest(String[] inputParameters)
    {
        String methodName = "processSecondParamAfterTest";
        String possibleArguments = "[cash]";
        String[] residualArguments = cutFirstIndexPositions(inputParameters, 1);

        //Just: test
        if (inputParameters.length == 0)
        {
            print(methodName + "\n" + POSSIBLE_ARGUMENTS + possibleArguments);
            return;
        }
        if (isKeyword(inputParameters[0]))
            switch (getKeyword(inputParameters[0]))
            {
                case CASH:
                    testCash();
                    return;
            }
        throw new InterpreterInvalidArgumentException(methodName, inputParameters[0].toLowerCase(), possibleArguments);
    }

    //OPTIONS
    private void societyRandomAdd(String[] inputParams)
    {
        String methodname = "societyRandomAdd()";
        if (inputParams.length == 0)
            Society.getSociety().addPersonRnd(1);
        else if (tryParseInt(inputParams[0]))
            Society.getSociety().addPersonRnd(Integer.parseInt(inputParams[0]));
        else throw new IllegalArgumentException("Not an Integer: " + inputParams[0]);
    }

    private void societyAdd(String[] inputOptions)
    {
        String methodname = "societyAdd()";
        String possibleArguments = "[random]";
        //Case no options
        if (inputOptions.length == 0)
        {
            Person newPerson = new Person();
            society.addPerson(newPerson);
            print("Added Person: " + newPerson);
            return;
        }

        if (isKeyword(inputOptions[0]))
            switch (getKeyword(inputOptions[0]))
            {
                case RANDOM:
                    societyRandomAdd(cutFirstIndexPositions(inputOptions, 1));
                    return;
                default:
                    throw new InterpreterInvalidArgumentException(methodname, inputOptions[0], possibleArguments);
            }
        if (inputOptions.length >= 2)
        {
            Person newPerson = new Person(new PersonName(inputOptions[0], inputOptions[1]));
            society.addPerson(newPerson);
            print("Added Person: " + newPerson);
            return;
        }

        throw new InterpreterInvalidOptionCombination(methodname, inputOptions);
    }

    private void personPrint(String[] inputOptions)
    {
        String methodname = "personPrint()";
        String possibleArguments = "[budget]";
        if (inputOptions.length == 0)
        {
            print("No Person specified. Example: person print Hans Hubertus");
            return;
        }

        if (isKeyword(inputOptions[0]))
            switch (getKeyword(inputOptions[0]))
            {
                case BUDGET:
                    boolean foundPerson = false;
                    for (Person person : society.getPeople())
                    {
                        if (person.name.equals(new PersonName(inputOptions[1], inputOptions[2])))
                        {
                            print(person.budgetData());
                            foundPerson = true;
                        }
                    }
                    if (!foundPerson)
                        print("No person found with name: " + inputOptions[1] + " " + inputOptions[2]);
                    return;
                default:
                    throw new InterpreterInvalidArgumentException(methodname, inputOptions[0], possibleArguments);
            }
        else if (inputOptions.length >= 2)
        {
            boolean foundPerson = false;
            for (Person person : society.getPeople())
            {
                if (person.name.equals(new PersonName(inputOptions[0], inputOptions[1])))
                {
                    print(person);
                    print(person.printEconomical());
                    foundPerson = true;
                }
            }
            if (!foundPerson)
                print("No person found with name: " + inputOptions[0] + " " + inputOptions[1]);
            return;
        }

        throw new InterpreterInvalidOptionCombination(methodname, inputOptions);
    }

    private void personBuy(String[] inputOptions)
    {
        String methodname = "personBuy()";

        if (inputOptions.length == 0)
        {
            print("No Person specified. Example: person buy Hans Hubertus");
            return;
        }
        else if (inputOptions.length >= 2)
        {
            boolean foundPerson = false;
            for (Person person : society.getPeople())
            {
                if (person.name.equals(new PersonName(inputOptions[0], inputOptions[1])))
                {
                    person.shop();
                    print(person.name + " shopped");
                    foundPerson = true;
                }
            }
            if (!foundPerson)
                print("No person found with name: " + inputOptions[0] + " " + inputOptions[1]);
            return;
        }

        throw new InterpreterInvalidOptionCombination(methodname, inputOptions);
    }

    private void societyPrint(String[] inputOptions)
    {
        String methodname = "societyPrint()";
        String possibleArguments = "[income, person, education]";
        if (inputOptions.length == 0)
        {
            print(society.getSocietyStatistics().printBase());
            return;
        }
        if (isKeyword(inputOptions[0]))
            switch (getKeyword(inputOptions[0]))
            {
                case INCOME:
                    print(society.getSocietyStatistics().printIncomeStat());
                    return;
                case PERSON:
                    print(society.printSocPeople());
                    return;
                case EDUCATION:
                    print(society.getSocietyStatistics().printEduStat());
                    return;
            }
        throw new InterpreterInvalidArgumentException(methodname, inputOptions[0], possibleArguments);
    }

    private void societyCalc()
    {
        society.calcSociety();
        print("Calculated Societey");
    }

    private void societyPopulate()
    {
        Society.getSociety().populateSociety(Util.NUM_PERS_DEFAULT);
        //society.populateSociety(DEFAULT_NUM_EDU_BASE, DEFAULT_NUM_EDU_APPR, DEFAULT_NUM_EDU_HIGH, DEFAULT_NUM_EDU_UNIV);
        society.calcSociety();
        print("Populated Societey");
    }

    private void economyPrint(String[] inputOptions)
    {
        String methodname = "economyPrint()";
        String possibleArguments = "[company]";
        //Case no options
        if (inputOptions.length == 0)
        {
            print(economy.economyBaseData());
        }
        else if (isKeyword(inputOptions[0]))
            switch (getKeyword(inputOptions[0]))
            {
                case COMPANY:
                    print(economy.economyBaseCompanyData());
                    return;
            }
        throw new InterpreterInvalidArgumentException(methodname, inputOptions[0], possibleArguments);

    }

    private void economyPopulate()
    {
        economy.populateEconomy(DEFAULT_NUM_COMPANIES);
    }

    private void economyHire(String[] inputOptions)
    {
        String methodname = "economyHire()";
        economy.fillWorkplaces();

    }

    private void testCash()
    {
        String methodname = "testCash()";
        Integer depPeople = society.getSocietyStatistics().depositSumPeople;
        Integer depComp = economy.getEconomyStatistics().getSumCompanyDeposits();
        Integer depGov = government.getDeposit();
        print("Society: " + depPeople + "\nCompanies: " + depComp + "\nGovernment: " + depGov + "\nSum: " + (depPeople + depGov + depComp));
    }

    private void economyPaySalary(String[] inputOptions)
    {
        economy.companiesPaySalary();
        society.calcSociety();
        economy.calc();
        print("All Companies payed loans");
        return;
    }

    private void companyPay(String[] inputOptions)
    {
        String methodname = "companyPay()";

        //Case no options
        if (inputOptions.length == 0)
        {
            print("No Company specified. Please specify name");
            return;
        }

        Company pays = economy.getCompanyByName(inputOptions[0]);
        if (pays != null)
        {
            pays.paySalaries();
            print("Company paid");
            society.calcSociety();
            economy.calc();
        }
        else
            print("Company not found");
    }

    private void companyPrint(String[] inputOptions)
    {
        String methodname = "companyAdd()";
        //Case no options
        if (inputOptions.length == 0)
        {
            print("No Company specified. Did you forget to specify a name?");
            return;
        }
        //Case print company with name
        if (inputOptions.length >= 1)
        {
            Company company = economy.getCompanyByName(inputOptions[0]);
            if (company != null)
                print(company.baseData());
            else
                print("No Company found");
            return;
        }

        throw new InterpreterInvalidOptionCombination(methodname, inputOptions);
    }

    private void companyProduce(String[] optionPara)
    {
        if (optionPara.length == 0)
        {
            print("Specify company");
            return;
        }
        Company company = economy.getCompanyByName(optionPara[0]);
        if (company != null)
        {
            company.produce();
            print(company.getName() + " produced");
        }
        else
            print("No Company found");

    }

    private void companyAdd(String[] inputOptions)
    {
        String methodname = "companyAdd()";
        //Case no options
        if (inputOptions.length == 0)
        {
            print("Please specify name");
            return;
        }
        //Case name given
        if (inputOptions.length >= 1)
        {
            print(economy.addCompany(inputOptions[0]));
            return;
        }

        throw new InterpreterInvalidOptionCombination(methodname, inputOptions);

    }

    private void govermentSet(String[] inputParam)
    {
        PoliticalOpinion newRulingParty = PoliticalOpinion.Unpolitical;
        if(tryParseInt(inputParam[0]))
        {
            newRulingParty = PoliticalOpinion.fromInt(Integer.parseInt(inputParam[0]));
            government.setRulingParty(newRulingParty);
            print("Gov set to: " + newRulingParty);
        }
        else
        {
            print("cannot resolve political party");
        }

    }

    //Helper
    private String[] cutFirstIndexPositions(String[] input, Integer NumberCutPostions)
    {
        int lengthReturnArray = input.length - NumberCutPostions;
        if (lengthReturnArray < 0)
            lengthReturnArray = 0;

        String[] ret = new String[lengthReturnArray];
        for (int i = 0; i < ret.length; i++)
            ret[i] = input[i + NumberCutPostions];
        return ret;
    }

    private void print(String text)
    {
        if (PRINTSYSOUT)
            System.out.println(text);
        console.println(text);
    }

    private void print(Object object)
    {
        print(object.toString());
    }

    private InterpreterKeyword getKeyword(String input)
    {
        for (Map.Entry<InterpreterKeyword, Set<String>> keyword : keywords.entrySet())
            if (keyword.getValue().contains(input))
                return keyword.getKey();

        return null;
    }

    private boolean isKeyword(String input)
    {
        if (getKeyword(input) != null)
            return true;
        else
            return false;
    }

    //Getter
    public static Interpreter getInterpreter()
    {
        if (instance != null)
            return instance;
        else
        {
            throw new RuntimeException("Interpreter not initialized");
        }
    }

    public static Interpreter getInterpreter(Simulation simulation)
    {
        Society soc = simulation.getSociety();
        Economy eco = simulation.getEconomy();
        Government gov = simulation.getGovernment();

        if (instance != null)
            throw new RuntimeException("Interpreter already initialized");
        else
        {
            return new Interpreter(soc, eco, gov);
        }
    }


}
