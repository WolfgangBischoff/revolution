package Core;

import Core.Enums.EducationalLayer;
import Core.Enums.Paygrade;

import java.time.LocalDate;

import static Core.Util.*;

public class Workposition
{
    EducationalLayer neededEducation;
    Paygrade paygrade;
    Integer grossIncomeWork = 0;
    Integer netIncomeWork = 0;
    Integer incomeTaxWork = 0;
    Person worker = null;
    Company company;
    //Integer daysToPay = 0;
    LocalDate hasWorkerSince;


    //Constructor
    public Workposition(Company company, EducationalLayer edu)
    {
        this.company = company;
        this.neededEducation = edu;
        setPaygrade();
        calcIncome();
    }

    //Calculations
    void calcIncome()
    {
        calcGrossIncomeWorkOnPaygrade();
        calcNetIncome();
    }

    void calcNetIncome()
    {
        incomeTaxWork = Government.CalcIncomeTax(grossIncomeWork);
        netIncomeWork = grossIncomeWork - incomeTaxWork;
    }

    void calcGrossIncomeWorkOnPaygrade()
    {
        switch (paygrade)
        {
            case A:
                grossIncomeWork = PAYGRADE_GROSS_INCOME_A; break;
            case B:
                grossIncomeWork = PAYGRADE_GROSS_INCOME_B; break;
            case C:
                grossIncomeWork = PAYGRADE_GROSS_INCOME_C; break;
            case D:
                grossIncomeWork = PAYGRADE_GROSS_INCOME_D; break;
            case E:
                grossIncomeWork = PAYGRADE_GROSS_INCOME_E; break;
            case F:
                grossIncomeWork = PAYGRADE_GROSS_INCOME_F; break;
            default:
                grossIncomeWork = 0; break;
        }
    }

    public boolean isWorkerAppropriate(Person worker)
    {
        return neededEducation.getInt() <= worker.educationalLayer.getInt() && worker.worksAt == null;
    }

    //Prints
    @Override
    public String toString()
    {
        return "\nWorkposition{" +
                "neededEducation=" + neededEducation +
                ", worker=" + worker +
                '}';
    }

    public void setPaygrade()
    {
        //At them moment just A-D are used
        this.paygrade = Paygrade.fromInt(neededEducation.getInt());
    }

    public void setWorker(Person worker)
    {
        hasWorkerSince = Simulation.getSingleton().getDate();
        if(worker == null)
        {
            hasWorkerSince = null;
        }
        this.worker = worker;
    }

    public EducationalLayer getNeededEducation()
    {
        return neededEducation;
    }

    public Integer getGrossIncomeWork()
    {
        return grossIncomeWork;
    }

    public Company getCompany()
    {
        return company;
    }
}
