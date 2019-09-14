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

    //Constructor
    public BudgetPlan(Person person)
    {
        this.person = person;
        calcBudget();
    }

    private Map<IndustryType, Integer> findUnbuyableProducts(Map<IndustryType, Integer> demands)
    {
        Map<IndustryType, Integer> productsNotBuyable = new TreeMap<>();
        Integer deposit = person.getDeposit();

        for (Map.Entry<IndustryType, Integer> entry : demands.entrySet())
        {
            IndustryType type = entry.getKey();
            Integer demand = entry.getValue();
            Integer supply = Market.getMarket().getNumberProducts(type);
            Integer demandReduction = 0;
            if(demand == 0)
                continue;

            //System.out.println("TYPE: " + type);
            //Not enough products
            if (demand > supply)
            {
                //System.out.println("Not enough Stock " + demand + " " + supply);
                demandReduction += demand - supply;
                demand -= demand - supply;
            }
            //System.out.println("Reduction1: " + demandReduction);
            //Not enough money
            if (Market.getMarket().getProductPrice() * demand > deposit)
            {
                //System.out.println("Not enough Money " + Market.getMarket().getProductPrice() * demand + " " + deposit);
                demandReduction += demand - (deposit / Market.getMarket().getProductPrice());
                demand -= demand - (deposit / Market.getMarket().getProductPrice());
            }
            //System.out.println("Reduction2: " + demandReduction);
            //reduceBudget
            deposit -= demand * Market.getMarket().getProductPrice();

            if (demandReduction > 0)
                productsNotBuyable.put(type, demandReduction);
        }
        return productsNotBuyable;
    }

    private Map<IndustryType, Integer> getShoppingCartUnchecked()
    {
        Map<IndustryType, Integer> shoppingCart = new TreeMap<>();
        //time logic => random choose what to buy today

        //for testing just a subset
        shoppingCart.put(IndustryType.FOOD, 5);
        shoppingCart.put(IndustryType.CLOTHS, 1);
        shoppingCart.put(IndustryType.HOUSING, 1);
        shoppingCart.put(IndustryType.ENERGY, 3);
        shoppingCart.put(IndustryType.ELECTRONICS, 1);
        shoppingCart.put(IndustryType.SPARETIME, 6);

        return shoppingCart;
    }

    private void reduceShoppingCart(Map<IndustryType, Integer> shoppingCart, Map<IndustryType, Integer> notBuyable)
    {
        for (Map.Entry<IndustryType, Integer> notAvailable : notBuyable.entrySet())
        {
            if (shoppingCart.containsKey(notAvailable.getKey()))
                shoppingCart.put(notAvailable.getKey(), shoppingCart.get(notAvailable.getKey()) - notAvailable.getValue());
            else
                throw new RuntimeException("reduceShoppingCart(): " + notAvailable.getKey() + " not found in shopping cart");

        }
    }

    public Map<IndustryType, Integer> getShoppingCartChecked()
    {
        Map<IndustryType, Integer> shoppingCart = getShoppingCartUnchecked();
        Map<IndustryType, Integer> notBuyable = findUnbuyableProducts(shoppingCart);
        System.out.println(person.getName() + " Shopping Cart: " + shoppingCart.toString());
        System.out.println("Not Buyable: " + notBuyable.toString());
        reduceShoppingCart(shoppingCart, notBuyable);
        System.out.println("Residual: " + shoppingCart.toString() + " \n");
        return shoppingCart;
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

        //System.out.println("Calc Budget " + person.getName());
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
