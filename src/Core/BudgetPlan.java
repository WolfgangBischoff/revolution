package Core;

import Core.Enums.BudgetPost;
import Core.Enums.IndustryType;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static Core.Enums.BudgetPost.*;
import static Core.Util.*;

public class BudgetPlan
{
    private Integer product_need_FOOD = 30;
    private Integer product_need_CLOTHS = 2;
    private Integer product_need_HOUSING = 1;
    private Integer product_need_ENERGY = 30;
    private Integer product_need_ELECTRONICS = 1;
    private Integer product_need_HEALTH = 1;
    private Integer product_need_TRAFFIC = 15;
    private Integer product_need_EDUCATION = 1;
    private Integer product_need_SPARETIME = 10;
    private Integer foodBudget;
    private Integer clothsBudget;
    private Integer housingBudget;
    private Integer energyBudget;
    private Integer electronicsBudget;
    private Integer healthBudget;
    private Integer trafficBudget;
    private Integer educationBudget;
    private Integer sparetimeBudget;
    private Integer savingsBudget;
    private Integer otherAndServices;

    Person person;

    public BudgetPlan(Person person)
    {
        this.person = person;
        calcBudget();
    }

    public Map<IndustryType, Integer> getShoppingCart()
    {
        Map<IndustryType, Integer> shoppingCard = new TreeMap<>();
        //time logic

        //created basket for today
        //for testing just a subset
        shoppingCard.put(IndustryType.FOOD, 5);
        shoppingCard.put(IndustryType.CLOTHS, 1);
        shoppingCard.put(IndustryType.HOUSING, 1);
        shoppingCard.put(IndustryType.ENERGY, 3);
        shoppingCard.put(IndustryType.ELECTRONICS, 1);
        shoppingCard.put(IndustryType.SPARETIME, 6);

        return shoppingCard;
    }

    public void calcBudget()
    {
        Map<BudgetPost, Integer> weighting = new HashMap<>();
        Map<BudgetPost, Double> percentageWeighted;
        weighting.put(FOOD, BUDGET_DEFAULT_WEIGHT_FOOD);
        weighting.put(CLOTHS, BUDGET_DEFAULT_WEIGHT_CLOTHS);
        weighting.put(HOUSING, BUDGET_DEFAULT_WEIGHT_HOUSING);
        weighting.put(ENERGY, BUDGET_DEFAULT_WEIGHT_ENERGY);
        weighting.put(ELECTRONICS, BUDGET_DEFAULT_WEIGHT_ELECTRONICS);
        weighting.put(HEALTH, BUDGET_DEFAULT_WEIGHT_HEALTH);
        weighting.put(TRAFFIC, BUDGET_DEFAULT_WEIGHT_TRAFFIC);
        weighting.put(EDUCATION, BUDGET_DEFAULT_WEIGHT_EDUCATION);
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
                sparetimeBudget +
                otherAndServices;
    }
}
