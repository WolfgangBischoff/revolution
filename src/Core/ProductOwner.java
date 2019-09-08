package Core;

public interface ProductOwner
{
    String getName();
    void addProduct(Product product);
    void removeProduct(Product product);
    void getPaid(Integer amount);
}
