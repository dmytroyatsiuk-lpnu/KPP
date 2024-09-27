import java.util.*;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    public static void main(String[] args) {
        List<Order> orders = new ArrayList<>();
        String[] names = {"Oleksandr", "Dmytro", "Oleksiy", "Davide", "Oleg", "Stanislav", "Denys"};
        String[] paymentMethods = {"Card", "Cash"};
        for (int i = 0; i < names.length; i++) {
            orders.add(formOrder(String.valueOf(i + 1),
                    (int) (Math.random() * 5 + 1),
                    names[i],
                    paymentMethods[(int) (Math.random() * 2)]));
        }

        //1. Розділити замовлення на дві колекції: картка та готівка---------------------------------
        //Stream API
        List<Order> cardOrders = orders.stream()
                .filter(order -> "Card".equals(order.paymentMethod()))
                .toList();
        System.out.println(ANSI_RED + "Card (stream API):\n" + ANSI_RESET + cardOrders);

        List<Order> cashOrders = orders.stream()
                .filter(order -> "Cash".equals(order.paymentMethod()))
                .toList();
        System.out.println(ANSI_RED + "Cash (stream API):\n" + ANSI_RESET + cashOrders);

        //Without stream API
        cardOrders = new ArrayList<>();
        cashOrders = new ArrayList<>();

        for (Order order : orders) {
            if ("Card".equals(order.paymentMethod())) {
                cardOrders.add(order);
            } else if ("Cash".equals(order.paymentMethod())) {
                cashOrders.add(order);
            }
        }
        System.out.println(ANSI_RED + "Card (without stream API):\n" + ANSI_RESET + cardOrders);
        System.out.println(ANSI_RED + "Cash (without stream API):\n" + ANSI_RESET + cashOrders);

        //2. Згрупувати замовлення за покупцями------------------------------------------------------
        //Stream API
        Map<String, List<Order>> ordersByCustomer = orders.stream()
                .collect(Collectors.groupingBy(Order::customerName));
        System.out.println(ANSI_RED + "Orders by customer (stream API):\n" + ANSI_RESET + ordersByCustomer);
        //Without stream API
        ordersByCustomer = new HashMap<>();

        for (Order order : orders) {
            String customerName = order.customerName();
            ordersByCustomer.computeIfAbsent(customerName, _ -> new ArrayList<>()).add(order);
        }
        System.out.println(ANSI_RED + "Orders by customer (without stream API):\n" + ANSI_RESET + ordersByCustomer);

        //3. Порахувати загальну кількість проданих одиниць товарів----------------------------------
        //Stream API
        Map<String, Integer> productSales = orders.stream()
                .flatMap(order -> order.products().stream())
                .collect(Collectors.groupingBy(Product::productName, Collectors.summingInt(Product::quantity)));
        System.out.println(ANSI_RED + "Product sales (stream API):\n" + ANSI_RESET + productSales);

        //Without stream API
        productSales = new HashMap<>();

        for (Order order : orders) {
            for (Product product : order.products()) {
                productSales.merge(product.productName(), product.quantity(), Integer::sum);
            }
        }
        System.out.println(ANSI_RED + "Product sales (without stream API):\n" + ANSI_RESET + productSales);

        //4. Відсортувати замовлення за загальною вартістю товарів
        //Stream API
        List<Order> sortedOrders = orders.stream()
                .sorted(Comparator.comparingDouble(order -> order.products().stream()
                        .mapToDouble(product -> product.price() * product.quantity()).sum()))
                .toList();
        System.out.println(ANSI_RED + "Sorted by price (stream API):\n" + ANSI_RESET + sortedOrders);

        //Without stream API
        sortedOrders = new ArrayList<>(orders);

        sortedOrders.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                double total1 = 0;
                for (Product product : o1.products()) {
                    total1 += product.price() * product.quantity();
                }

                double total2 = 0;
                for (Product product : o2.products()) {
                    total2 += product.price() * product.quantity();
                }

                return Double.compare(total1, total2);
            }
        });

        System.out.println(ANSI_RED + "Sorted by price (without stream API):\n" + ANSI_RESET + sortedOrders);
        //5. Вивести список унікальних товарів
        //Stream API
        Set<String> uniqueProducts = orders.stream()
                .flatMap(order -> order.products().stream())
                .map(Product::productName)
                .collect(Collectors.toSet());
        System.out.println(ANSI_RED + "Unique products (stream API):\n" + ANSI_RESET + uniqueProducts);

        //Without stream API
        uniqueProducts = new HashSet<>();

        for (Order order : orders) {
            for (Product product : order.products()) {
                uniqueProducts.add(product.productName());
            }
        }
        System.out.println(ANSI_RED + "Unique products (without stream API):\n" + ANSI_RESET + uniqueProducts);

        //6. Знайти замовлення з найвищою вартістю і безпечно обробити ситуацію
        //Stream API
        Optional<Order> maxOrder = orders.stream()
                .max(Comparator.comparingDouble(order -> order.products().stream()
                        .mapToDouble(product -> product.price() * product.quantity()).sum()));

        maxOrder.ifPresentOrElse(
                order -> System.out.println(ANSI_RED + "Максимальне замовлення (stream API):\n" + ANSI_RESET + order),
                () -> System.out.println(ANSI_RED + "Немає замовлень." + ANSI_RESET)
        );

        //Without stream API
        Order maxOrderr = null;
        double maxTotal = 0;

        for (Order order : orders) {
            double total = 0;
            for (Product product : order.products()) {
                total += product.price() * product.quantity();
            }

            if (total > maxTotal) {
                maxTotal = total;
                maxOrderr = order;
            }
        }

        Optional<Order> optionalMaxOrder = Optional.ofNullable(maxOrderr);

        optionalMaxOrder.ifPresentOrElse(
                order -> System.out.println(ANSI_RED + "Максимальне замовлення (without stream API):\n" + ANSI_RESET + order),
                () -> System.out.println(ANSI_RED + "Немає замовлень." + ANSI_RESET)
        );

    }







    public static Order formOrder(String orderNumber, int productsAmount, String customerName, String paymentMethod) {
        String[] productNames = {
                "TV",
                "Smartphone",
                "Laptop",
                "Headphones",
                "Computer mouse",
        };
        double[] productPrices = {500, 300, 800, 100, 20};
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < productsAmount; i++) {
            products.add(new Product(productNames[i], (int) (Math.random() * 5 + 1), productPrices[i]));
        }
        return new Order(orderNumber, customerName, products, paymentMethod);
    }
}