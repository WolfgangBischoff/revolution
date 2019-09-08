package Core;

public class Product
{
    private static Integer nextProductID = 1;
    Integer productID;
    String name;
    Company producent;
    ProductOwner owner;
    ProductOwner previousOwner;

    public Product(String name, Company producer, ProductOwner owner)
    {
        this.productID = nextProductID++;
        this.name = name;
        this.producent = producer;
        this.owner = owner;
    }

    //Calculation
    public static void transfer(ProductOwner sender, ProductOwner receipient, Product product)
    {
        product.previousOwner = product.owner;
        receipient.addProduct(product);
        sender.removeProduct(product);
    }

    //Prints
    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", name='" + name + '\'' +
                ", producent=" + producent.getName() +
                ", owner=" + owner.getName() +
                '}';
    }

    public String baseData()
    {
        return "ID: " + productID + " " + name;
    }
}
