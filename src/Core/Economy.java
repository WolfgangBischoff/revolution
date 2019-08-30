package Core;

import java.util.ArrayList;

import static Core.Util.*;

public class Economy
{
    ArrayList<Company> companies = new ArrayList<>();
    private EconomyStatistics economyStatistics;


    public Economy()
    {
        economyStatistics = new EconomyStatistics(this);
    }

    //Calculations
    public String createUniqueCompanyName(String nameBase)
    {
        String name = nameBase;
        Integer counter = 2;
        while(!isCompanyNameUnique(name))
        {
            name = name + counter;
            counter++;
        }
        return name;
    }

    public Company addCompanyByName(String name)
    {
        String UniqueName = createUniqueCompanyName(name);
        Company newCompany = new Company(UniqueName, NUM_BASE_EDU_WORK, NUM_APPR_EDU_WORK, NUM_HIGH_EDU_WORK, NUM_UNIV_EDU_WORK);
        companies.add(newCompany);
        return newCompany;
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

    public void populateEconomy(Integer numberComp)
    {
        companies = new ArrayList<>();
        for(int i=0; i<numberComp; i++)
        {
            addCompanyByName(Company.getRandomCompanyName());
        }
    }

    public void fillWorkplaces(Company company)
    {
        ArrayList<Person> worker = Society.getSociety().getPeople();
        for(Workposition workposition : company.getWorkpositions())
        {
            for(Person person : worker)
            {
                if(person.worksAt == null && company.hireWorker(workposition, person))
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
        return "#Companies: " + companies.size() + " #FreeWorkplaces: " + calcNumberFreeWorkpositions() + " CompanyDeposits: " + economyStatistics.calcSumCompanyDeposits();
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

    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public EconomyStatistics getEconomyStatistics() {
        return economyStatistics;
    }
}
