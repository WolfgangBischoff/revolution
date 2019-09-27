package Core;

import Core.Enums.BudgetPost;
import Core.Enums.IndustryType;

import java.util.ArrayList;
import java.util.List;

import static Core.Enums.IndustryType.FOOD;
import static Core.Util.*;

public class Economy
{
    private static Economy singleton;
    ArrayList<Company> companies = new ArrayList<>();
    private EconomyStatistics economyStatistics;
    private static final String TESTCASESPATH = "./res/txt/testcases/";


    private Economy() //change to private
    {
        economyStatistics = new EconomyStatistics(this);
    }

    public static Economy getEconomy()
    {
        if(singleton == null)
            singleton = new Economy();
        return singleton;
    }

    //Calculations
    public void calc()
    {
        economyStatistics.calcState();
        for(Company company : companies)
            company.initPeriod();
    }

    public String createUniqueCompanyName(String nameBase)
    {
        String name = nameBase;
        Integer counter = 2;
        while(!isCompanyNameUnique(name))
        {
            if(isCompanyNameUnique(name + counter))
            {
                name = name + counter;
                return name;
            }
            counter++;
        }
        return name;
    }


    public Company addCompany(String name)
    {
        String UniqueName = createUniqueCompanyName(name);
        Company newCompany = new Company(UniqueName, NUM_BASE_EDU_WORKPLACES, NUM_APPR_EDU_WORKPLACES, NUM_HIGH_EDU_WORKPLACES, NUM_UNIV_EDU_WORKPLACES);
        companies.add(newCompany);
        return newCompany;
    }

    public List<Company> createTest(String testcaseName)
    {
        List<String[]> compData;
        List<Company> newCompanies = new ArrayList<>();
        compData = Util.readAllLineFromTxt(TESTCASESPATH + testcaseName + ".csv", true);
        for(String[] strings : compData)
        {
            Company newCompany = new Company(IndustryType.fromInt(Integer.parseInt(strings[0])),Integer.parseInt(strings[1]), Integer.parseInt(strings[2]));
            companies.add(newCompany);
            newCompanies.add(newCompany);
        }
        return newCompanies;
    }

    public Company addCompany(IndustryType industry)
    {
        //String UniqueName = createUniqueCompanyName(Company.getRandomCompanyName(industry));
        //Company newCompany = new Company(UniqueName, industry, NUM_BASE_EDU_WORKPLACES, NUM_APPR_EDU_WORKPLACES, NUM_HIGH_EDU_WORKPLACES, NUM_UNIV_EDU_WORKPLACES);
        Company newCompany = new Company(industry, 5, 3);
        companies.add(newCompany);
        return newCompany;
    }

    public List<Company> addCompany(IndustryType industry, Integer number)
    {
        List<Company> createdCompanies = new ArrayList<>();
        for(int i=0; i<number; i++)
        {
            createdCompanies.add(addCompany(industry));
        }
        return createdCompanies;
    }

    public void companiesPaySalary()
    {
        for (Company company : companies)
            company.paySalaries();
    }

    public Company getCompanyByName(String name)
    {
        for(Company company : companies)
            if(company.getName().equals(name))
                return company;
        return null;
    }

    boolean isCompanyNameUnique(String name)
    {
        return getCompanyByName(name) == null;
    }

    public Integer calcNumberFreeWorkpositions()
    {
        Integer sum = 0;
        for(Company company : companies)
            sum += company.calcNumberFreeWorkpositions();
        return sum;
    }

    private void clearWorkplaces()
    {
        for(Company company : companies)
            company.unemployAllWorkers();
    }

    public void populateEconomy(Integer numberComp)
    {
        clearWorkplaces();
        companies.clear();
        //createTest("comp");
        for(int i=0; i<numberComp; i++)
        {

            addCompany(IndustryType.fromInt(i % IndustryType.getEnumSize()));
        }
        economyStatistics.calcState();
    }

    public void populateEconomy(String testcase)
    {
        clearWorkplaces();
        companies.clear();
        createTest(testcase);
        economyStatistics.calcState();
    }



    public void fillWorkplaces(Company company)
    {
        ArrayList<Person> worker = Society.getSociety().getPeople();
        for(Workposition workposition : company.getWorkpositions())
        {
            for(Person person : worker)
            {
                if(person.worksAt == null && company.employWorker(workposition, person))
                {break;}
            }
        }

    }

    public void fillWorkplaces()
    {
        for(Company company : companies)
        {
            fillWorkplaces(company);
        }
        economyStatistics.calcState();
    }


    @Override
    public String toString()
    {
        return economyBaseData();
    }

    public String economyBaseData()
    {
        if(companies.size()==0)
            return "Economy has no Companies";
        return "#Companies: " + companies.size() + " #FreeWorkplaces: " + calcNumberFreeWorkpositions() + " CompanyDeposits: " + economyStatistics.getSumCompanyDeposits();
    }

    public String economyBaseCompanyData()
    {
        if(companies.size()==0)
            return "Economy has no companies";
        StringBuilder tmp = new StringBuilder();
        for(Company company : companies)
        {
            tmp.append(company.baseData() + "\n");
        }
        return tmp.toString();
    }

    public String dataMarketAnalysis()
    {
        if(companies.size()==0)
            return "Economy has no companies";
        StringBuilder tmp = new StringBuilder();
        tmp.append("---- Analysis Data ----\n");
        for(Company company : companies)
        {
            tmp.append(company.baseData() + "\n");
            tmp.append(company.getMarketanalysisData().dataAnalysis() + "\n");
        }
        return tmp.toString();
    }

    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public EconomyStatistics getEconomyStatistics() {
        return economyStatistics;
    }
}
