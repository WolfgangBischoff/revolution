package Core;

import Core.Exceptions.InterpreterInvalidOptionCombination;
import Core.Exceptions.InterpreterNoParametersAlloweException;
import Core.Exceptions.InterpreterUndefindedOptionException;

import java.util.HashMap;
import java.util.Map;

import static Core.Util.*;
import static javafx.application.Platform.exit;

public class Interpreter
{
    private static Interpreter instance = null;
    private Society society;
    private Economy economy;
    private Government government;
    private Console console;
    private final boolean PRINTSYSOUT = true;

    //Constructors
    private Interpreter(Society soc, Economy eco, Government gov)
    {
        society = soc;
        economy = eco;
        government = gov;
    }

    public void setConsole(Console console) {
        this.console = console;
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


    private String normalizeFirstParameter(String rawInput)
    {
        String methodName = "normalizeFirstParameter()";
        switch (rawInput.toLowerCase())
        {
            case "person":
            case "per":
                return "person";
            case "society":
            case "soc":
                return "society";
            case "company":
            case "comp":
            case "com":
                return "company";
            case "government":
            case "gov":
                return "government";
            case "economy":
            case "eco":
                return "economy";
            case "test":
                return "test";
            case "help":
            case "?":
                return "help";
            case "quit":
            case "end":
                return "quit";
            default:
                throw new InterpreterUndefindedOptionException(methodName, rawInput);
        }
    }

    private String normalizeSecondParameter(String rawInput)
    {
        String methodName = "normalizeSecondParameter()";
        switch (rawInput.toLowerCase())
        {
            case "print":
            case "p":
                return "print";
            case "populate":
            case "pop":
                return "populate";
            case "add":
            case "a":
                return "add";
            case "cash":
                return "cash"; //test()
            case "pay":
                return "pay"; //company
            case "hire":
                return "hire"; //economy
            case "calc":
                return "calc"; //society
            default:
                throw new InterpreterUndefindedOptionException(methodName, rawInput);
        }
    }

    private String normalizeOption(String inputString)
    {
        String methodName = "normalizeOption";

        switch (inputString.toLowerCase())
        {
            case "-name":
            case "-n":
                return "-name";
            case "-firstname":
            case "-fn":
                return "-firstname";
            case "-lastname":
            case "-ln":
                return "-lastname";
            case "-age":
                return "-age";
            case "-all":
            case "-a":
                return "-all";
            case "-income":
            case "-inc":
                return "-income";
            case "-companies":
            case "-comp":
            case "-c":
                return "-companies";
            default:
                throw new InterpreterUndefindedOptionException(methodName, inputString);
        }
    }

    //FIRST INSTRUCTION
    private void processFirstParam(String[] inputParameter)
    {
        String methodName = "processFirstParam";
        String parameter = normalizeFirstParameter(inputParameter[0]);
        String[] newParam = cutFirstIndexPositions(inputParameter, 1);

        switch (parameter)
        {
            case "person":
                processSecondParamAfterPerson(newParam);
                break;
            case "society":
                processSecondParamAfterSociety(newParam);
                break;
            case "company":
                processSecondParamAfterCompany(newParam);
                break;
            case "government":
                processSecondParamAfterGovernment(newParam);
                break;
            case "economy":
                processSecondParamAfterEconomy(newParam);
                break;
            case "test":
                processSecondParamAfterTest(newParam);
                break;
            case "help":
                printGeneralHelp();
                break;
            case "quit":
                exit();//run = false;
                break;
            default:
                throw new InterpreterUndefindedOptionException(methodName, parameter);
        }

    }

    //SECOND INSTRUCTION
    private void processSecondParamAfterPerson(String[] inputParameters)
    {
        String methodName = "processSecondParamAfterPerson()";
        String[] newParam = cutFirstIndexPositions(inputParameters, 1);

        //just "person"
        if (inputParameters.length == 0)
        {
            print(methodName + " Further arguments needed");
            return;
        }

        String parameter = normalizeSecondParameter(inputParameters[0].toLowerCase());
        //Switch to second param
        switch (parameter)
        {
            case "add":
                personAdd(newParam);
                break;
            case "print":
                personPrint(newParam);
                break;
            default:
                throw new InterpreterUndefindedOptionException(methodName, inputParameters[0].toLowerCase());
        }
    }

    private void processSecondParamAfterSociety(String[] inputParameters)
    {
        String methodName = "processSecondParamAfterSociety";
        String[] newParam = cutFirstIndexPositions(inputParameters, 1);

        //Just: society
        if (inputParameters.length == 0)
        {
            print(methodName + " Further arguments needed");
            return;
        }

        String parameter = normalizeSecondParameter(inputParameters[0].toLowerCase());
        switch (parameter)
        {
            case "print":
                societyPrint(newParam);
                break;
            case "calc":
                societyCalc();
                break;
            case "populate":
                societyPopulate();
                break;
            default:
                throw new InterpreterUndefindedOptionException(methodName, inputParameters[0]);
        }
    }

    private void processSecondParamAfterCompany(String[] inputParameters)
    {
        String methodName = "processSecondParamAfterCompany";
        String[] optionPara = cutFirstIndexPositions(inputParameters, 1);
        //Just: company
        if (inputParameters.length == 0)
        {
            print(methodName + " Further arguments needed");//System.out.println("Further arguments needed");
            return;
        }

        String parameter = normalizeSecondParameter(inputParameters[0].toLowerCase());
        switch (parameter)
        {
            case "print":
                companyPrint(optionPara);
                break;
            case "pay":
                companyPay(optionPara);
                break;
            case "add":
                companyAdd(optionPara);
                break;

            default:
                throw new InterpreterUndefindedOptionException(methodName, inputParameters[0].toLowerCase());
        }
    }

    private void processSecondParamAfterGovernment(String[] inputParameters)
    {
        String methodName = "processSecondParamAfterGovernment";
        String[] optionPara = cutFirstIndexPositions(inputParameters, 1);
        if (inputParameters.length == 0)
        {
            print(methodName + " Further arguments needed");//System.out.println("Further arguments needed");
            return;
        }

        String parameter = normalizeSecondParameter(inputParameters[0].toLowerCase());
        switch (parameter)
        {
            case "print":
                print(government);
                break;
            default:
                throw new InterpreterUndefindedOptionException(methodName, inputParameters[0]);
        }
    }

    private void processSecondParamAfterEconomy(String[] inputParameters)
    {
        String methodName = "processSecondParamAfterEconomy";
        String[] optionPara = cutFirstIndexPositions(inputParameters, 1);
        //Just: Economy
        if (inputParameters.length == 0)
        {
            print(methodName + " Further arguments needed");//System.out.println("Further arguments needed");
            return;
        }
        String parameter = normalizeSecondParameter(inputParameters[0].toLowerCase());
        switch (parameter)
        {
            case "print":
                enconomyPrint(optionPara);
                break;
            case "populate":
                economyPopulate();
                break;
            case "hire":
                economyHire(optionPara);
                break;
            default:
                throw new InterpreterUndefindedOptionException(methodName, inputParameters[0]);
        }

    }

    private void processSecondParamAfterTest(String[] inputParameters)
    {
        String methodName = "processSecondParamAfterTest";
        String[] optionPara = cutFirstIndexPositions(inputParameters, 1);

        //Just: test
        if (inputParameters.length == 0)
        {
            print(methodName + " Further arguments needed");//System.out.println("Further arguments needed");
            return;
        }

        String parameter = normalizeSecondParameter(inputParameters[0].toLowerCase());
        switch (parameter)
        {
            case "cash":
                testCash();
                break;
            default:
                throw new InterpreterUndefindedOptionException(methodName, inputParameters[0].toLowerCase());
        }
    }


    //OPTIONS
    private void personAdd(String[] inputOptions)
    {
        String methodname = "personAdd()";
        Map<String, String> options = readOptionParameter(inputOptions);
        //Case no options
        if (options.size() == 0)
        {
            print("No Person specified. Did you forget -name or -all?");//System.out.println("No Person specified. Did you forget -name or -all?");
            return;
        }

        //case name
        if (options.containsKey("-name"))
        {
            Person newPerson = new Person(new PersonName(options.get("-name")));
            society.addPerson(newPerson);
            print("Added Person: " + newPerson);//System.out.println("Added Person: " + newPerson);
            return;
        }

        //case first and lastname
        if (options.containsKey("-firstname") && options.containsKey("-lastname"))
        {
            PersonName name = new PersonName(options.get("-firstname"), options.get("-lastname"));
            Person newPerson = new Person(name);
            society.addPerson(newPerson);
            print("Added Person: " + newPerson);//System.out.println("Added Person: " + newPerson);
            return;
        }

        throw new InterpreterInvalidOptionCombination(methodname, inputOptions);
    }

    private void personPrint(String[] inputOptions)
    {
        String methodname = "personPrint()";
        Map<String, String> options = readOptionParameter(inputOptions);

        String forbiddenParam = notContainsForbiddenOptionParameter(options, new String[]{"-all"});
        if (forbiddenParam != null)
            throw new InterpreterNoParametersAlloweException(methodname, forbiddenParam);

        //Case no options
        if (options.size() == 0)
        {
            print("No Person specified. Did you forget -name?");//System.out.println("No Person specified. Did you forget -name?");
            return;
        }
        //case all persons
        if (options.containsKey("-all"))
        {
            print(society.printSocPeople());
            return;
        }
        //case some persons
        //TODO implement search function in Society and then use all options
        if (options.containsKey("-name"))
        {
            for (Person person : society.getPeople())
            {
                if (person.name.equals(new PersonName(options.get("-name"))))
                    print(person);//System.out.println(person);
            }
            return;
        }

        throw new InterpreterInvalidOptionCombination(methodname, inputOptions);
    }

    private void societyPrint(String[] inputOptions)
    {
        String methodname = "personPrint()";
        Map<String, String> options = readOptionParameter(inputOptions);
        //Case no options
        if (options.size() == 0)
            print(society.getSocietyStatistics().printBase());//System.out.println(society.getSocietyStatistics().printBase());

        if (options.containsKey("-income"))
            print(society.getSocietyStatistics().printIncomeStat());//System.out.println(society.getSocietyStatistics().printIncomeStat());
    }

    private void societyCalc()
    {
        society.calcSociety();
        print("Calculated Societey");
    }

    private void societyPopulate()
    {
        society.populateSociety(INTER_DEF_NUM_EDU_BASE, INTER_DEF_NUM_EDU_APPR, INTER_DEF_NUM_EDU_HIGH, INTER_DEF_NUM_EDU_UNIV);
        society.calcSociety();
    }

    private void enconomyPrint(String[] inputOptions)
    {
        String methodname = "economyPrint()";
        Map<String, String> options = readOptionParameter(inputOptions);

        //Case no options
        if (options.size() == 0)
        {
            print(economy.economyBaseData());//System.out.println(economy.economyBaseData());
        }
        if (options.containsKey("-companies"))
            print(economy.getCompanies());//System.out.println(economy.getCompanies());

    }

    private void economyPopulate()
    {
        economy.populateEconomy(INTER_DEF_NUM_COMPANIES);
    }

    private void economyHire(String[] inputOptions)
    {
        String methodname = "economyHire()";
        Map<String, String> options = readOptionParameter(inputOptions);

        if (options.containsKey("-name"))
        {
            if (economy.getCompanyByName(options.get("-name")) == null)
            {
                print("Company with name " + options.get("-name") + " does not exist");//System.out.println("Company with name " + options.get("-name") + " does not exist");
                return;
            }
            Company hires = economy.getCompanyByName(options.get("-name"));
            economy.fillWorkplaces(hires);
            return;
        }

        economy.fillWorkplaces();

    }

    private void testCash()
    {
        String methodname = "testCash()";
        //Map<String, String> options = readOptionParameter(inputOptions);

        Integer depPeople = society.getSocietyStatistics().depositSumPeople;
        Integer depComp = economy.getEconomyStatistics().calcSumCompanyDeposits();
        Integer depGov = government.getDeposit();
        print("Society: " + depPeople + "\nCompanies: " + depComp + "\nGovernment: " + depGov + "\nSum: " + (depPeople + depGov + depComp));//System.out.println("Society: " + depPeople + "\nCompanies: " + depComp + "\nGovernment: " + depGov + "\nSum: " + (depPeople + depGov + depComp));
    }

    private void companyPay(String[] inputOptions)
    {
        String methodname = "companyPay()";
        Map<String, String> options = readOptionParameter(inputOptions);

        //Case no options
        if (options.size() == 0)
        {
            print("No Company specified. Did you forget -name?");//System.out.println("No Company specified. Did you forget -name?");
            return;
        }

        //Case all companies
        if (options.containsKey("-all"))
        {
            for (Company company : economy.getCompanies())
                company.paySalaries();
            print("All Companies payed loans");//System.out.println("All Companies payed loans");
            society.calcSociety();
            return;
        }

        //Case name given
        if (options.containsKey("-name"))
        {
            economy.getCompanyByName(options.get("-name")).paySalaries();
            return;
        }

        throw new InterpreterInvalidOptionCombination(methodname, inputOptions);
    }

    private void companyPrint(String[] inputOptions)
    {
        String methodname = "companyAdd()";
        Map<String, String> options = readOptionParameter(inputOptions);

        String forbiddenParam = notContainsForbiddenOptionParameter(options, new String[]{"-all"});
        if (forbiddenParam != null)
            throw new InterpreterNoParametersAlloweException(methodname, forbiddenParam);

        //Case no options
        if (options.size() == 0)
        {
            print("No Company specified. Did you forget -name?");//System.out.println("No Company specified. Did you forget -name?");
            return;
        }

        //Case print all companies
        if (options.containsKey("-all"))
        {
            print(economy.economyBaseCompanyData());//System.out.println(economy.economyBaseCompanyData());
            return;
        }

        //Case print company with name
        if (options.containsKey("-name"))
        {
            print(economy.getCompanyByName(options.get("-name")));
            return;
        }

        throw new InterpreterInvalidOptionCombination(methodname, inputOptions);
    }

    private void companyAdd(String[] inputOptions)
    {
        String methodname = "companyAdd()";
        Map<String, String> options = readOptionParameter(inputOptions);

        //Case no options
        if (options.size() == 0)
        {
            print("No Company specified. Did you forget -name?");
            return;
        }

        //Case name given
        if (options.containsKey("-name"))
        {
            print(economy.addCompanyByName(options.get("-name")));
            return;
        }

        throw new InterpreterInvalidOptionCombination(methodname, inputOptions);

    }

    //Util
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

    /**
     * Checks if options which should not have parameter really dont have parameter. Example: -all parmeter => forbidden
     *
     * @param options                   option/parameter pairs
     * @param parameterForbiddenOptions options which should not have parameter
     * @return forbidden parameter or null
     */
    private String notContainsForbiddenOptionParameter(Map<String, String> options, String[] parameterForbiddenOptions)
    {
        for (String forbiddenParameterOption : parameterForbiddenOptions)
            if (options.get(forbiddenParameterOption) != null)
                return options.get(forbiddenParameterOption);

        return null;
    }

    private Map<String, String> readOptionParameter(String[] params)
    {
        String methodname = "readOptionParameter()";

        //Iterate params and create Map
        Map<String, String> results = new HashMap<>();
        String[] residualParams = params;

        while (residualParams.length > 0)
        {
            //Next Token is parameter type
            if (residualParams[0].contains("-"))
            {
                //paramter type with parameter value (-n Wolfgang)
                if (residualParams.length >= 2 && !residualParams[1].contains("-"))
                {
                    results.put(normalizeOption(residualParams[0]), residualParams[1]);
                    residualParams = cutFirstIndexPositions(residualParams, 2);
                }
                else
                //just parameter type (-all)
                {
                    results.put(normalizeOption(residualParams[0]), null);
                    residualParams = cutFirstIndexPositions(residualParams, 1);
                }
            }
            else
            {
                throw new InterpreterUndefindedOptionException(methodname, residualParams[0]);
            }
        }
        return results;
    }

    //Helptext
    private void printGeneralHelp()
    {

        print(
                "Existing Instructions:\n" +
                        "Person print -name -all\n" +
                        "Person add -name -firstname -lastname\n" +
                        "economy print -companies\n" +
                        "society print -income\n" +
                        "society populate\n" +
                        "government print\n" +
                        "company print -name -all\n" +
                        "company pay -name -all\n" +
                        "testCash cash\n"
        );
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
        Society soc = simulation.society;
        Economy eco = simulation.economy;
        Government gov = simulation.government;

        if (instance != null)
            throw new RuntimeException("Interpreter already initialized");
        else
        {
            return new Interpreter(soc, eco, gov);
        }
    }
}
