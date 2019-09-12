package Core;

import Core.Enums.ProductType;
import java.util.HashMap;
import java.util.Map;
import static Core.Enums.ProductType.*;
import static Core.Util.*;

public class BudgetPlan
{
    Integer foodBudget;
    Integer clothsBudget;
    Integer housingBudget;
    Integer energyBudget;
    Integer electronicsBudget;
    Integer healthBudget;
    Integer trafficBudget;
    Integer educationBudget;
  //  Integer nightlifeBudget;
    Integer sparetimeBudget;
    Integer savingsBudget;
    Integer otherAndServices;

    Person person;

    public BudgetPlan(Person person)
    {
        this.person = person;
        calcBudget();
    }

    public void calcBudget()
    {
        Map<ProductType, Integer> weighting = new HashMap<>();
        Map<ProductType, Double> percentageWeighted;
        weighting.put(FOOD, BUDGET_DEFAULT_WEIGHT_FOOD);
        weighting.put(CLOTHS, BUDGET_DEFAULT_WEIGHT_CLOTHS);
        weighting.put(HOUSING, BUDGET_DEFAULT_WEIGHT_HOUSING);
        weighting.put(ENERGY, BUDGET_DEFAULT_WEIGHT_ENERGY);
        weighting.put(ELECTRONICS, BUDGET_DEFAULT_WEIGHT_ELECTRONICS);
        weighting.put(HEALTH, BUDGET_DEFAULT_WEIGHT_HEALTH);
        weighting.put(TRAFFIC, BUDGET_DEFAULT_WEIGHT_TRAFFIC);
        weighting.put(EDUCATION, BUDGET_DEFAULT_WEIGHT_EDUCATION);
        //weighting.put(NIGHTLIFE, BUDGET_DEFAULT_WEIGHT_NIGHTLIFE);
        weighting.put(SPARETIME, BUDGET_DEFAULT_WEIGHT_SPARETIME);
        weighting.put(SAVING, BUDGET_DEFAULT_WEIGHT_SAVING);
        weighting.put(OTHER_AND_SERVICES, BUDGET_DEFAULT_WEIGHT_OTHER_AND_SERVICES);

        percentageWeighted = Statistics.calcPercFromEnumCount(weighting);
        foodBudget = (int) (percentageWeighted.get(FOOD) * person.getNettIncome());
        clothsBudget = (int) (percentageWeighted.get(CLOTHS) * person.getNettIncome());
        housingBudget = (int) (percentageWeighted.get(HOUSING) * person.getNettIncome());
        energyBudget = (int) (percentageWeighted.get(ENERGY) * person.getNettIncome());
        electronicsBudget = (int) (percentageWeighted.get(ELECTRONICS) * person.getNettIncome());
        healthBudget = (int) (percentageWeighted.get(HEALTH) * person.getNettIncome());
        trafficBudget = (int) (percentageWeighted.get(TRAFFIC) * person.getNettIncome());
        educationBudget = (int) (percentageWeighted.get(EDUCATION) * person.getNettIncome());
        //nightlifeBudget = (int) (percentageWeighted.get(NIGHTLIFE) * person.getNettIncome());
        sparetimeBudget = (int) (percentageWeighted.get(SPARETIME) * person.getNettIncome());
        otherAndServices = (int) (percentageWeighted.get(OTHER_AND_SERVICES) * person.getNettIncome());
        savingsBudget = person.getNettIncome() - sumBudgetPostsWithoutSaving();

        System.out.println("Calc Budget " + person.getName());
    }

    @Override
    public String toString()
    {
        return "BudgetPlan{" +
                "foodBudget=" + foodBudget +
                ", clothsBudget=" + clothsBudget +
                ", housingBudget=" + housingBudget +
                ", energyBudget=" + energyBudget +
                ", electronicsBudget=" + electronicsBudget +
                ", healthBudget=" + healthBudget +
                ", trafficBudget=" + trafficBudget +
                ", educationBudget=" + educationBudget +
      //          ", nightlifeBudget=" + nightlifeBudget +
                ", sparetimeBudget=" + sparetimeBudget +
                ", savingsBudget=" + savingsBudget +
                ", otherAndServices=" + otherAndServices +
                " SUM: }" + sumBudgetPosts();
    }

    private int sumBudgetPosts()
    {
        return foodBudget + clothsBudget + housingBudget
                + energyBudget +
                electronicsBudget +
                healthBudget +
                trafficBudget +
                educationBudget +
      //          nightlifeBudget +
                sparetimeBudget +
                savingsBudget +
                otherAndServices;
    }

    private int sumBudgetPostsWithoutSaving()
    {
        return foodBudget + clothsBudget + housingBudget
                + energyBudget +
                electronicsBudget +
                healthBudget +
                trafficBudget +
                educationBudget +
       //         nightlifeBudget +
                sparetimeBudget +
                otherAndServices;
    }
}
