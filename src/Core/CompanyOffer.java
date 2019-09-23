package Core;

public class CompanyOffer implements Comparable<CompanyOffer>
{
    Integer price, luxury;
    Company manufacturer;

    public CompanyOffer(Company company, Integer price, Integer luxury)
    {
        manufacturer= company;
        this.price = price;
        this.luxury = luxury;
    }

    @Override
    public int compareTo(CompanyOffer o)
    {
        if(luxury < o.luxury)
            return 1;
        else if(luxury.equals(o.luxury) && price > o.price)
            return 1;
        else if (luxury.equals(o.luxury) && price == o.price)
            return 0;
        else
            return -1;
    }

    public Integer getPrice()
    {
        return price;
    }

    public Integer getLuxury()
    {
        return luxury;
    }

    public Company getManufacturer()
    {
        return manufacturer;
    }
}
