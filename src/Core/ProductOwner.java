package Core;

import java.util.List;

public interface ProductOwner
{
    String getName();
    void addProduct(Product product);
    void removeProduct(Product product);
    void getPaid(Integer amount);
    void pay(Product product);
    void pay(List<Product> product);
}
