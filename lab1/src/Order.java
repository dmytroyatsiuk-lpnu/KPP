import java.util.List;

public record Order(String orderNumber,
                    String customerName,
                    List<Product> products,
                    String paymentMethod) {
    @Override
    public String toString()    {
        return orderNumber + " " + customerName + ": " + products + " " + "\n" + paymentMethod + "\n";
    }
}
