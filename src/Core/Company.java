package Core;

import Core.Enums.EducationalLayer;

import java.util.ArrayList;

import static Core.Util.*;

public class Company {
    private String name;
    private Integer deposit;
    private ArrayList<Workposition> workpositions = new ArrayList<>();

    //Constructors
    public Company(String name)
    {
        this.name = name;
        deposit = COMP_DEFAULT_DEPOSIT;
    }

    public Company(String name, Integer base, Integer app, Integer high, Integer univ)
    {
        this(name);
        for (int i = 0; i < base; i++)
            workpositions.add(new Workposition(this, EducationalLayer.EDU_BASE));
        for (int i = 0; i < app; i++)
            workpositions.add(new Workposition(this, EducationalLayer.EDU_APPRENTICESHIP));
        for (int i = 0; i < high; i++)
            workpositions.add(new Workposition(this, EducationalLayer.EDU_HIGHER));
        for (int i = 0; i < univ; i++)
            workpositions.add(new Workposition(this, EducationalLayer.EDU_UNIVERSITY));
    }

    //Calculations
    void paySalaries()
    {
        for (Workposition workposition : workpositions)
            if(workposition.worker != null)
                paySalary(workposition);
    }

    private void paySalary(Workposition workposition)
    {
        workposition.worker.receiveSalary(workposition.netIncomeWork);
        Government.getGoverment().raiseIncomeTax(workposition.incomeTaxWork);
        deposit -= workposition.grossIncomeWork;
    }

    public Integer calcNumberFreeWorkpositions()
    {
        Integer returnSum = 0;
        for (Workposition workposition : workpositions)
            if (workposition.worker == null)
                returnSum++;
        return returnSum;
    }

    boolean employWorker(Workposition workposition, Person p)
    {
        if (workposition.isWorkerAppropriate(p))
        {
            workposition.worker = p;
            p.startAtWorkposition(workposition);
            //p.setWorksAt(workposition);
            //p.calcState();
            return true;
        }
        else
            return false;
    }

    void unemployAllWorkers()
    {
        for(Workposition workpositions : workpositions)
            unemployWorker(workpositions);
    }

    boolean unemployWorker(Workposition workposition)
    {
        if (workposition.worker != null)
        {
            workposition.worker.endAtWorkposition();
            workposition.worker = null;
            return true;
        }
        else
            return false;
    }

    static String getRandomCompanyName()
    {
        String[] names = {"HOFER", "Capgemini", "Allianz", "Löwenherz", "SwingKitchen", "PWC", "Kiss Bar", "Segafredo", "Merkur", "Maran Vegan", "Lenovo", "Bayer", "Young Living", "Samsung", "Wiener Linien", "Dachser"};
        return names[Util.getRandom().nextInt(names.length)];
    }

    private Integer calcNumberWorkers()
    {
        Integer sum = 0;
        for (Workposition workposition : workpositions)
            if (workposition.worker != null)
                sum++;
        return sum;
    }

    //Prints
    @Override
    public String toString()
    {
        return "\nCompany{" +
                "name='" + name + '\'' +
                ", workpositions=" + workpositions +
                '}';
    }

    public String baseData()
    {
        return "Name: " + name + " Workers: " + calcNumberWorkers() + " Deposit: " + deposit;
    }

    //Getter and Setter
    public String getName() {
        return name;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public ArrayList<Workposition> getWorkpositions() {
        return workpositions;
    }
}
