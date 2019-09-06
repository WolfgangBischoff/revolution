package Core;

import Core.Exceptions.InterpreterInvalidOptionCombination;
import Core.Exceptions.InterpreterUndefindedOptionException;

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

    public void setConsole(Console console)
    {
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
                return "cash";
            case "pay":
                return "pay";
            case "hire":
                return "hire";
            case "calc":
                return "calc";
            case "prod":
            case "produce":
                return "produce";
            default:
                throw new InterpreterUndefindedOptionException(methodName, rawInput);
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
                exit();
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
            case "add":
                societyAdd(newParam);
                break;
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
            print(methodName + " Further arguments needed");
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
            case "produce":
                companyProduce(optionPara);
                break;
            default:
                throw new InterpreterUndefindedOptionException(methodName, inputParameters[0].toLowerCase());
        }
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

    private void processSecondParamAfterGovernment(String[] inputParameters)
    {
        String methodName = "processSecondParamAfterGovernment";
        String[] optionPara = cutFirstIndexPositions(inputParameters, 1);
        if (inputParameters.length == 0)
        {
            print(methodName + " Further arguments needed");
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
            print(methodName + " Further arguments needed");
            return;
        }
        String parameter = normalizeSecondParameter(inputParameters[0].toLowerCase());
        switch (parameter)
        {
            case "add":
                companyAdd(optionPara);
                break;
            case "print":
                economyPrint(optionPara);
                break;
            case "populate":
                economyPopulate();
                break;
            case "hire":
                economyHire(optionPara);
                break;
            case "pay":
                economyPaySalary(optionPara);
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
            print(methodName + " Further arguments needed");
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
        //Case no options
        if (inputOptions.length == 0)
        {
            Person newPerson = new Person();
            society.addPerson(newPerson);
            print("Added Person: " + newPerson);
            return;
        }

        if(inputOptions[0].toLowerCase().equals("rnd"))
        {
            societyRandomAdd(cutFirstIndexPositions(inputOptions, 1));
            return;
        }
        else if (inputOptions.length >= 2)
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

        if (inputOptions.length == 0)
        {
            print("No Person specified. Example: person print Hans Hubertus");
            return;
        }
        else if (inputOptions.length >= 2)
        {
            boolean foundPerson = false;
            for (Person person : society.getPeople())
            {
                if (person.name.equals(new PersonName(inputOptions[0], inputOptions[1])))
                {
                    print(person);
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
        String methodname = "personPrint()";
        if (inputOptions.length == 0)
        {
            print(society.getSocietyStatistics().printBase());
            return;
        }
        if (inputOptions[0].equals("income"))
        {
            print(society.getSocietyStatistics().printIncomeStat());
            return;
        }
        if (inputOptions[0].equals("persons"))
        {
            print(society.printSocPeople());
            return;
        }
        if(inputOptions[0].equals("education"))
        {
            print(society.getSocietyStatistics().printEduStat());
            return;
        }

        throw new InterpreterInvalidOptionCombination(methodname, inputOptions);
    }

    private void societyCalc()
    {
        society.calcSociety();
        print("Calculated Societey");
    }

    private void societyPopulate()
    {
        society.populateSociety(DEFAULT_NUM_EDU_BASE, DEFAULT_NUM_EDU_APPR, DEFAULT_NUM_EDU_HIGH, DEFAULT_NUM_EDU_UNIV);
        society.calcSociety();
        print("Populated Societey");
    }

    private void economyPrint(String[] inputOptions)
    {
        String methodname = "economyPrint()";

        //Case no options
        if (inputOptions.length == 0)
        {
            print(economy.economyBaseData());
        }
        else if (inputOptions[0].equals("companies"))
            print(economy.getCompanies());

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
            print("No Company specified. Did you forget to specify a name?");//System.out.println("No Company specified. Did you forget -name?");
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
            print(economy.addCompanyByName(inputOptions[0]));
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
