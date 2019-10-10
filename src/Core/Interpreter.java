package Core;

import Core.Enums.IndustryType;
import Core.Enums.InterpreterKeyword;
import Core.Enums.PoliticalOpinion;
import Core.Exceptions.InterpreterInvalidArgumentException;
import Core.Exceptions.InterpreterInvalidOptionCombination;
import java.util.*;
import static Core.Util.DEFAULT_NUM_COMPANIES;
import static Core.Util.tryParseInt;
import static javafx.application.Platform.exit;

public class Interpreter
{
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
    private Set<String> keywordsCalculate = new HashSet<>(Arrays.asList("calculate", "initPeriod"));
    private Set<String> keywordsProduce = new HashSet<>(Arrays.asList("produce", "prod"));
    private Set<String> keywordsIncome = new HashSet<>(Arrays.asList("income", "inc"));
    private Set<String> keywordsEducation = new HashSet<>(Arrays.asList("education", "edu"));
    private Set<String> keywordsShop = new HashSet<>(Arrays.asList("shop"));
    private Set<String> keywordsSet = new HashSet<>(Arrays.asList("set"));
    private Set<String> keywordsBudget = new HashSet<>(Arrays.asList("budget", "bud"));
    private Set<String> keywordsFood = new HashSet<>(Arrays.asList("food"));
    private Set<String> keywordsConsume = new HashSet<>(Arrays.asList("consume", "con"));
    private Set<String> keywordsMarket = new HashSet<>(Arrays.asList("market", "mar"));
    private Set<String> keywordsTime = new HashSet<>(Arrays.asList("time"));
    private Set<String> keywordsNext = new HashSet<>(Arrays.asList("next"));

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
        keywords.put(InterpreterKeyword.SHOP, keywordsShop);
        keywords.put(InterpreterKeyword.SET, keywordsSet);
        keywords.put(InterpreterKeyword.BUDGET, keywordsBudget);
        keywords.put(InterpreterKeyword.FOOD, keywordsFood);
        keywords.put(InterpreterKeyword.CONSUME, keywordsConsume);
        keywords.put(InterpreterKeyword.MARKET, keywordsMarket);
        keywords.put(InterpreterKeyword.TIME, keywordsTime);
        keywords.put(InterpreterKeyword.NEXT, keywordsNext);
    }

    public void setConsole(Console console)
    {
        this.console = console;
    }

    //Helptext
    private void printGeneralHelp(String possibleArg)
    {

        print(
                "Instructions:\n" +
                        possibleArg
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
        } catch (IllegalArgumentException e)
        {
            print(methodName + "\n\t" + e.getMessage());
        }
    }

    //FIRST INSTRUCTION
    private void processFirstParam(String[] inputParameters)
    {
        String methodName = "processFirstParam()";
        String possibleArguments = "[person, society, company, government, economy, market, test, time, help, exit]";
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
                case MARKET:
                    processSecondParamAfterMarket(newParam);
                    return;
                case TEST:
                    processSecondParamAfterTest(newParam);
                    return;
                case TIME:
                    processSecondParamAfterTime(newParam);
                    return;
                case HELP:
                    printGeneralHelp(possibleArguments);
                    return;
                case EXIT:
                    exit();
                    return;
            }
        throw new InterpreterInvalidArgumentException(methodName, inputParameters[0], possibleArguments);
    }

    //SECOND INSTRUCTION
    private void processSecondParamAfterPerson(String[] inputArguments)
    {
        String methodName = "processSecondParamAfterPerson()";
        String possibleArguments = "[print, shop]";
        String[] newParam = cutFirstIndexPositions(inputArguments, 1);

        //just "person"
        if (inputArguments.length == 0)
        {
            print(methodName + "\n" + POSSIBLE_ARGUMENTS + possibleArguments);
            return;
        }

        if (isKeyword(inputArguments[0]))
            switch (getKeyword(inputArguments[0]))
            {
                case PRINT:
                    personPrint(newParam);
                    return;
                case SHOP:
                    personBuy(newParam);
                    return;
            }
        throw new InterpreterInvalidArgumentException(methodName, inputArguments[0], possibleArguments);
    }


    private void processSecondParamAfterSociety(String[] inputArguments)
    {
        String methodName = "processSecondParamAfterSociety()";
        String possibleArguments = "[print, add, calculate, populate, shop]";
        String[] newParam = cutFirstIndexPositions(inputArguments, 1);

        //Just: society
        if (inputArguments.length == 0)
        {
            print(methodName + "\n" + POSSIBLE_ARGUMENTS + possibleArguments);
            return;
        }

        if (isKeyword(inputArguments[0]))
            switch (getKeyword(inputArguments[0]))
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
                case SHOP:
                    societyBuy();
                    return;
            }
        throw new InterpreterInvalidArgumentException(methodName, inputArguments[0], possibleArguments);
    }

    private void processSecondParamAfterCompany(String[] inputArguments)
    {
        String methodName = "processSecondParamAfterCompany()";
        String possibleArguments = "[print, pay]";
        String[] optionPara = cutFirstIndexPositions(inputArguments, 1);
        //Just: company
        if (inputArguments.length == 0)
        {
            print(methodName + "\n" + POSSIBLE_ARGUMENTS + possibleArguments);
            return;
        }

        if (isKeyword(inputArguments[0]))
            switch (getKeyword(inputArguments[0]))
            {
                case PRINT:
                    companyPrint(optionPara);
                    return;
                case PAY:
                    companyPay(optionPara);
                    return;
            }
        throw new InterpreterInvalidArgumentException(methodName, inputArguments[0], possibleArguments);

    }

    private void processSecondParamAfterGovernment(String[] inputArguments)
    {
        String methodName = "processSecondParamAfterGovernment()";
        String possibleArguments = "[print, set]";
        String[] cutParam = cutFirstIndexPositions(inputArguments, 1);
        if (inputArguments.length == 0)
        {
            print(methodName + "\n" + POSSIBLE_ARGUMENTS + "[print]");
            return;
        }

        if (isKeyword(inputArguments[0]))
            switch (getKeyword(inputArguments[0]))
            {
                case PRINT:
                    print(government);
                    return;
                case SET:
                    govermentSet(cutParam);
                    return;
            }
        throw new InterpreterInvalidArgumentException(methodName, inputArguments[0], possibleArguments);
    }

    private void processSecondParamAfterEconomy(String[] inputArguments)
    {
        String methodName = "processSecondParamAfterEconomy()";
        String possibleArguments = "[print, add, populate, hire, pay, calculate]";
        String[] optionPara = cutFirstIndexPositions(inputArguments, 1);
        //Just: Economy
        if (inputArguments.length == 0)
        {
            print(methodName + "\n" + POSSIBLE_ARGUMENTS + possibleArguments);
            return;
        }
        if (isKeyword(inputArguments[0]))
            switch (getKeyword(inputArguments[0]))
            {
                case ADD:
                    economyAdd(optionPara);
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
                case CALCULATE:
                    economy.initPeriod();
                    return;
            }
        throw new InterpreterInvalidArgumentException(methodName, inputArguments[0], possibleArguments);
    }

    private void processSecondParamAfterTest(String[] inputArguments)
    {
        String methodName = "processSecondParamAfterTest()";
        String possibleArguments = "[cash]";
        String[] residualArguments = cutFirstIndexPositions(inputArguments, 1);

        //Just: test
        if (inputArguments.length == 0)
        {
            print(methodName + "\n" + POSSIBLE_ARGUMENTS + possibleArguments);
            return;
        }
        if (isKeyword(inputArguments[0]))
            switch (getKeyword(inputArguments[0]))
            {
                case CASH:
                    testCash();
                    return;
            }
        throw new InterpreterInvalidArgumentException(methodName, inputArguments[0].toLowerCase(), possibleArguments);
    }

    private void processSecondParamAfterMarket(String[] inputArguments)
    {
        String methodName = "processSecondParamAfterMarket()";
        String possibleArguments = "[print]";
        String[] optionPara = cutFirstIndexPositions(inputArguments, 1);
        //Just: company
        if (inputArguments.length == 0)
        {
            print(methodName + "\n" + POSSIBLE_ARGUMENTS + possibleArguments);
            return;
        }

        if (isKeyword(inputArguments[0]))
            switch (getKeyword(inputArguments[0]))
            {
                case PRINT:
                    marketPrint(optionPara);
                    return;
            }
        throw new InterpreterInvalidArgumentException(methodName, inputArguments[0], possibleArguments);
    }

    private void processSecondParamAfterTime(String[] inputArguments)
    {
        String methodName = "processSecondParamAfterTime()";
        String possibleArguments = "[print, next]";
        String[] residualInputArguments = cutFirstIndexPositions(inputArguments, 1);
        if (inputArguments.length == 0)
        {
            print(methodName + "\n" + POSSIBLE_ARGUMENTS + possibleArguments);
            return;
        }

        if (isKeyword(inputArguments[0]))
            switch (getKeyword(inputArguments[0]))
            {
                case PRINT:
                    print(Simulation.getSingleton().getDate());
                    return;
                case NEXT:
                    Simulation.getSingleton().nextPeriod();
                    return;
            }
        throw new InterpreterInvalidArgumentException(methodName, inputArguments[0], possibleArguments);
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
                            print(person.dataBudget());
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
                    print(person.dataEconomical());
                    foundPerson = true;
                }
            }
            if (!foundPerson)
                print("No person found with name: " + inputOptions[0] + " " + inputOptions[1]);
            return;
        }

        throw new InterpreterInvalidOptionCombination(methodname, inputOptions);
    }

    private void personBuy(String[] inputArguments)
    {
        String methodname = "personBuy()";

        if (inputArguments.length == 0)
        {
            print("No Person specified. Example: person buy Hans Hubertus");
            return;
        }
        else if (inputArguments.length >= 2)
        {
            boolean foundPerson = false;
            for (Person person : society.getPeople())
            {
                if (person.name.equals(new PersonName(inputArguments[0], inputArguments[1])))
                {
                    person.shop();
                    print(person.name + " shopped");
                    foundPerson = true;
                }
            }
            if (!foundPerson)
                print("No person found with name: " + inputArguments[0] + " " + inputArguments[1]);
            return;
        }

        throw new InterpreterInvalidOptionCombination(methodname, inputArguments);
    }

    private void societyPrint(String[] inputArguments)
    {
        String methodname = "societyPrint()";
        String possibleArguments = "[income, person, education, budget, consume]";
        if (inputArguments.length == 0)
        {
            print(society.getSocietyStatistics().printBase());
            print(POSSIBLE_ARGUMENTS + possibleArguments);
            return;
        }
        if (isKeyword(inputArguments[0]))
            switch (getKeyword(inputArguments[0]))
            {
                case INCOME:
                    print(society.getSocietyStatistics().printIncomeStat());
                    return;
                case PERSON:
                    print(society.printSocPeople(null));
                    return;
                case EDUCATION:
                    print(society.getSocietyStatistics().printEduStat());
                    return;
                case BUDGET:
                    print(society.printSocPeople(InterpreterKeyword.BUDGET));
                    return;
                case CONSUME:
                    print(society.printSocPeople(InterpreterKeyword.CONSUME));
                    return;
            }
        throw new InterpreterInvalidArgumentException(methodname, inputArguments[0], possibleArguments);
    }


    private void societyCalc()
    {
        society.calcSocietyDaily();
        print("Calculated Societey");
    }

    private void societyPopulate()
    {
        Society.getSociety().populateSociety(Util.NUM_PERS_DEFAULT);
        society.calcSocietyDaily();
        print("Populated Societey");
    }

    private void societyBuy()
    {
        society.shop();
        print("Society shopped");
    }

    private void economyPrint(String[] inputArguments)
    {
        String methodname = "economyPrint()";
        String possibleArguments = "[company, market]";
        //Case no options
        if (inputArguments.length == 0)
        {
            print(economy.economyBaseData());
            print(POSSIBLE_ARGUMENTS + possibleArguments);
            return;
        }
        if (isKeyword(inputArguments[0]))
            switch (getKeyword(inputArguments[0]))
            {
                case COMPANY:
                    print(economy.economyBaseCompanyData());
                    return;
                case MARKET:
                    print(economy.dataMarketAnalysis());
                    return;
            }
        throw new InterpreterInvalidArgumentException(methodname, inputArguments[0], possibleArguments);

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
        society.calcSocietyDaily();
        economy.initPeriod();
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
            society.calcSocietyDaily();
            economy.initPeriod();
        }
        else
            print("Company not found");
    }

    private void companyPrint(String[] inputOptions)
    {
        String methodname = "economyPrint()";
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

    private void economyAdd(String[] inputOptions)
    {
        String methodname = "economyAdd()";
        String possibleArguments = "[food, test]";
        //Case no options
        if (inputOptions.length == 0)
        {
            print("Please specify name");
            print(POSSIBLE_ARGUMENTS + possibleArguments);
            return;
        }

        if (isKeyword(inputOptions[0]))
        {
            String[] residualArguments = cutFirstIndexPositions(inputOptions, 1);
            switch (getKeyword(inputOptions[0]))
            {
                case FOOD:
                    economyAddIndustry(IndustryType.FOOD, residualArguments);
                    return;
                case TEST:
                    economy.createTest(residualArguments[0]);
                    return;

            }
        }

        //Case name given
        if (inputOptions.length >= 1)
        {
            print(economy.addCompany(inputOptions[0]));
            return;
        }

        throw new InterpreterInvalidOptionCombination(methodname, inputOptions);

    }

    private void economyAddIndustry(IndustryType type, String[] arguments)
    {
        if (arguments.length > 0)
        {
            Integer number = 0;
            if (Util.tryParseInt(arguments[0]))
            {
                number = Integer.parseInt(arguments[0]);
                print(economy.addCompany(type, number).toString());
                return;
            }
            else
            {
                print("Not a number");
                return;
            }
        }
        economy.addCompany(type, 1);

    }

    private void govermentSet(String[] inputParam)
    {
        PoliticalOpinion newRulingParty = PoliticalOpinion.Unpolitical;
        if (tryParseInt(inputParam[0]))
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

    private void marketPrint(String[] inputArgs)
    {
        // print(Market.getMarket().dataMarketAnalysis());
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
