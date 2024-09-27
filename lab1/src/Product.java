public record Product(String productName,
                      int quantity,
                      double price) {
    @Override
    public String toString() {
        return "\nname:'" + productName + "', quantity:'" + quantity + "', price:'" + price + "'";
    }
}
