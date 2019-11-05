package Core;

public class LuxuryPriceGroup implements Comparable<LuxuryPriceGroup>
{
    Integer luxury, price;
    Company company;

    public LuxuryPriceGroup(Company company, Integer luxury, Integer price)
    {
        this.company = company;
        this.luxury = luxury;
        this.price = price;
    }

    @Override
    public String toString()
    {
        return "Luxury: " + luxury +
                " Price: " + price;
    }

    @Override
    public int compareTo(LuxuryPriceGroup other)
    {
        if (luxury > other.luxury)
            return -1;
        if (luxury < other.luxury)
            return 1;
        if (price < other.price)
            return -1;
        if (price > other.price)
            return 1;
        if (company.getName().hashCode() < other.company.getName().hashCode())
            return -1;
        if (company.getName().hashCode() > other.company.getName().hashCode())
            return 1;
        return 0;
    }

    public Integer getLuxury()
    {
        return luxury;
    }

    public Integer getPrice()
    {
        return price;
    }

    public Company getCompany()
    {
        return company;
    }
}

