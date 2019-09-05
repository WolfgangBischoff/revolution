package Core;

public class Product
{
    private static Integer nextProductID = 0;
    Integer productID;
    String name;
    Company producent;
    ProductOwner owner;

    public Product(String name, Company producent, ProductOwner owner)
    {
        this.productID = nextProductID++;
        this.name = name;
        this.producent = producent;
        this.owner = owner;
    }
}
