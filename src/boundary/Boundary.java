package boundary;

import Controller.InventoryController;
import Controller.ItemController;
import Controller.OrderController;
import Controller.UserController;
import entity.Inventory;
import entity.Item;
import entity.Order;

import java.time.LocalDateTime;
import java.util.*;

public class Boundary {

    private UserController userController = new UserController();
    private ItemController itemController = new ItemController();
    private OrderController orderController = new OrderController();
    private InventoryController inventoryController;

    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public ItemController getItemController() {
        return itemController;
    }

    public void setItemController(ItemController itemController) {
        this.itemController = itemController;
    }

    public OrderController getOrderController() {
        return orderController;
    }

    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    public InventoryController getInventoryController() {
        return inventoryController;
    }

    public void setInventoryController(InventoryController inventoryController) {
        this.inventoryController = inventoryController;
    }

    public boolean login(String userName, String passWord){
       return userController.login(userName, passWord);
    }

    public void showCreateOrderPage(){
        boolean a = true;
        boolean b = true;
        String orderId = "";
        String storeId = "";
        String createBy = "";
        String customerPhoneNumber = "null";
        LocalDateTime createTime = LocalDateTime.now();
        String customerName = "";
        double totalPrice = 0;
        String status = "Ready";
        ItemController itemController = new ItemController();
        InventoryController inventoryController = new InventoryController(userController.getCurrentUser().getStoreId());
        Map<Item,Integer> itemAndItsQuantity = new HashMap<>();

        while (b) {
            while (a) {
                Scanner scan = new Scanner(System.in);
                System.out.println("Please enter your item name");
                String itemName = scan.nextLine();
                System.out.println("Please enter your item quantity");
                int itemNumber = scan.nextInt();
                for (Item i : itemController.itemList) {
                    if (i.getItemName().equals(itemName)) {
                        if (itemName.equals("coffee beans")){
                            System.out.println("Please enter the phone number of customer");
                            Scanner scanner = new Scanner(System.in);
                            customerPhoneNumber = scanner.nextLine();
                        }
                        int number = inventoryController.searchItemQuantity(itemName);
                        if (number >= itemNumber) {
                            itemAndItsQuantity.put(i, itemNumber);
                            System.out.println("Do you want to continue add item? y/n");
                            a = confirm();
                        } else {
                            System.out.println("Item does not exit or the inventory of this item is not enough");
                        }
                    }
                }
            }

            orderId = Integer.toString(orderController.getOrderList().size() + 1);
            storeId = userController.getCurrentUser().getStoreId();
            createBy = userController.getCurrentUser().getId();
            createTime = LocalDateTime.now();

            System.out.println("Please enter the customer name");
            Scanner scanner = new Scanner(System.in);
            customerName = scanner.nextLine();

            totalPrice = 0;
            for (Item key : itemAndItsQuantity.keySet()) {
                totalPrice += key.getPrice() * itemAndItsQuantity.get(key);
                System.out.println(key.getItemName() + "   " + itemAndItsQuantity.get(key));
            }

            System.out.println("Total price: $" + totalPrice);
            System.out.println("Do you want to confirm this order?");
            b = confirm1();

            if (b) {
                itemAndItsQuantity.clear();
                a = true;
            }
        }
        orderController.createOrder(orderId, itemAndItsQuantity, createBy, createTime, customerName, totalPrice, storeId, status, customerPhoneNumber);
        System.out.println("Create order successfully!");
        System.out.println(orderController.getOrderList().toString());
    }

    public void showCreateCoffeeBeansOrderPage(){

    }


    public void showMenu(){
        String currentUser = userController.getCurrentUser().getRole();

        switch (currentUser){
            case "Manager":
                System.out.println("Welcome to the Bask shop system! Manager");
                System.out.println("Please choose the options below:");
                System.out.println("1. Create order");
                System.out.println("2. Show the total number of coffee beans sold in last month in each store");
                System.out.println("3. Show the total number of food items sold in last month in each store");
                System.out.println("4. Show the total number of coffee items sold in last month in each store");
                System.out.println("5. Show the total sale made in dollars last month in each store");
                System.out.println("6. Show the type of coffee sold the most per store in the last month");
                break;

            case "Owner":
                System.out.println("Welcome to the Bask shop system! Oliver");
                System.out.println("Please choose the options below:");
                System.out.println("1. Create order");
                System.out.println("2. Show the total number of coffee beans sold in last month in each store");
                System.out.println("3. Show the total number of food items sold in last month in each store");
                System.out.println("4. Show the total number of coffee items sold in last month in each store");
                System.out.println("5. Show the total sale made in dollars last month in each store");
                System.out.println("6. Show the type of coffee sold the most per store in the last month");
                break;

            case "Staff":
                System.out.println("Welcome to the Bask shop system!");
                System.out.println("Please choose the options below:");
                System.out.println("1. Create order");
                System.out.println("2. Show the total number of coffee beans sold in last month in each store");
                System.out.println("3. Show the total number of food items sold in last month in each store");
                System.out.println("4. Show the total number of coffee items sold in last month in each store");
                System.out.println("5. Show the total sale made in dollars last month in each store");
                System.out.println("6. Show the type of coffee sold the most per store in the last month");
                break;
        }

    }

    public void showMonthlyCoffeeBeansSold(){
        ArrayList<Order> newOrderList= new ArrayList();
        LocalDateTime currentTime = LocalDateTime.now();

        for (Order o: orderController.getOrderList()) {
            if (!o.getCustomerPhoneNumber().equals("null") && o.getCreateTime().isAfter(currentTime.minusDays(30))) {
                    newOrderList.add(o);
            }
        }

//        System.out.println(newOrderList.toString());

        for (int i = 1; i < 11; i++) {
            int quantity = 0;
            for (Order o: newOrderList){
                int storeId = Integer.parseInt(o.getStoreId());
                if (storeId == i){
//                    Item item = itemController.searchItemByName("coffee beans");
//                    System.out.println(item);
//                    System.out.println(o.getItemAndItsQuantity().get(item));
                    for(Map.Entry<Item,Integer> entry : o.getItemAndItsQuantity().entrySet()){
                        if (entry.getKey().getItemName().equals("coffee beans")){
                            quantity += entry.getValue();
                        }
                    }
//                    quantity = o.getItemAndItsQuantity().get(item);
                }
            }
            System.out.println("The coffee bean sales of store(in quantity) " + i +" this month are " + quantity);
        }
    }

    public void showLastMonthFoodSold(){
        ArrayList<Order> newOrderList= new ArrayList();
        LocalDateTime currentTime = LocalDateTime.now();

        for (Order o: orderController.getOrderList()) {
            if (o.getCreateTime().isAfter(currentTime.minusDays(30))) {
                newOrderList.add(o);
            }
        }
        for (int i = 1; i < 11; i++) {
            int quantity = 0;
            for (Order o: newOrderList) {
                int storeId = Integer.parseInt(o.getStoreId());
                if (storeId == i) {
                    for (Map.Entry<Item, Integer> entry : o.getItemAndItsQuantity().entrySet()) {
                        if (entry.getKey().getSort().equals("food")) {
                            quantity += entry.getValue();
                        }
                    }
                }
            }
            System.out.println("The food sales of store " + i + "(in quantity) this month are " + quantity);
        }
    }

    public void showLastMonthCoffeeSold(){
        ArrayList<Order> newOrderList= new ArrayList();
        LocalDateTime currentTime = LocalDateTime.now();

        for (Order o: orderController.getOrderList()) {
            if (o.getCreateTime().isAfter(currentTime.minusDays(30))) {
                newOrderList.add(o);
            }
        }
        for (int i = 1; i < 11; i++) {
            int quantity = 0;
            for (Order o: newOrderList) {
                int storeId = Integer.parseInt(o.getStoreId());
                if (storeId == i) {
                    for (Map.Entry<Item, Integer> entry : o.getItemAndItsQuantity().entrySet()) {
                        if (entry.getKey().getSort().equals("coffee")) {
                            quantity += entry.getValue();
                        }
                    }
                }
            }
            System.out.println("The food sales of store " + i + "(in quantity) this month are " + quantity);
        }
    }


    public void showLastMonthSoldDollar(){
        ArrayList<Order> newOrderList= new ArrayList();
        LocalDateTime currentTime = LocalDateTime.now();

        for (Order o: orderController.getOrderList()) {
            if (o.getCreateTime().isAfter(currentTime.minusDays(30))) {
                newOrderList.add(o);
            }
        }
        for (int i = 1; i < 11; i++) {
            int dollars = 0;
            for (Order o: newOrderList) {
                int storeId = Integer.parseInt(o.getStoreId());
                if (storeId == i) {
                    dollars += o.getTotalPrice();
                }
            }
            System.out.println("The total sales of store " + i + "(in dollars) this month are $" + dollars);
        }

    }

    public void showLowInventoryItem() {

        
    }


    public void typeCoffeeSoldMostLastMonth(){
        ArrayList<Order> orders = new ArrayList<>();
        LocalDateTime currentDate = LocalDateTime.now();
        int lastMonth = currentDate.getMonthValue();
        for (Order order: orderController.getOrderList())
        {
            if (order.getCreateTime().getMonthValue() == lastMonth)
                orders.add(order);
        }

        Map<Item, Integer> cofferAndSale = new HashMap<>();

        for(Order order: orders)
        {
            for(Map.Entry<Item, Integer> entry: order.getItemAndItsQuantity().entrySet()){
                Item itemKey = entry.getKey();
                if (itemKey.getSort().equalsIgnoreCase("coffee"))
                {
                    cofferAndSale.put(itemKey, 0);
                }
            }
        }

        for(Order order: orders)
        {
            for(Map.Entry<Item, Integer> entry: order.getItemAndItsQuantity().entrySet()){
                Item itemKey = entry.getKey();
                if (itemKey.getSort().equalsIgnoreCase("coffee"))
                {
                    int sale = cofferAndSale.get(itemKey);
                    cofferAndSale.put(itemKey, entry.getValue() + sale);
                }
            }
            order.getItemAndItsQuantity();
        }

        for (Item i : cofferAndSale.keySet())
        {
            System.out.println("coffee name: " + i.getItemName() +"sale: " + cofferAndSale.get(i) );
        }

//        List<Map.Entry<Item, Integer>> infoIds = new ArrayList<Map.Entry<Item, Integer>>(cofferAndSale.entrySet());
//        ArrayList<Integer> sales = new ArrayList<>();
//        Collections.sort(infoIds, new Comparator<Map.Entry<Item, Integer>>() {
//            public int compare(Map.Entry<Item, Integer> o1, Map.Entry<Item, Integer> o2) {
//                return (o2.getValue() - o1.getValue());
//                //return (o1.getKey()).toString().compareTo(o2.getKey());
//            }
//        });
//
//        for (int i = 0; i < infoIds.size(); i++) {
//            String id = infoIds.get(i).getKey().getItemName();
//            System.out.println("coffee name: " + id +"sale: " + infoIds.get(i).getValue() );
//        }

    }

    public void showDaysOfWeekMadeMostSale() {

    }



    public void chooseOption(){
        boolean a = true;

        while (a) {
            Scanner scanner = new Scanner(System.in);
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    showCreateOrderPage();
                    a = false;
                    break;
                case "2":
                    showMonthlyCoffeeBeansSold();
                    a = false;
                    break;
                case "3":
                    showLastMonthFoodSold();
                    a = false;
                    break;
                case "4":
                    showLastMonthCoffeeSold();
                    a = false;
                    break;
                case "5":
                    showLastMonthSoldDollar();
                    a = false;
                    break;
                case "6":
                    typeCoffeeSoldMostLastMonth();
                    a = false;
                    break;

                default:
                    System.out.println("input error, please re-input");
            }
        }
    }



    public Boolean confirm(){
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        if (choice.equals("n")){
            return false;
        }else {
            return true;
        }
    }

    public Boolean confirm1(){
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        if (choice.equals("y")){
            return false;
        }else {
            return true;
        }
    }

}
