package boundary;

import Controller.InventoryController;
import Controller.ItemController;
import Controller.OrderController;
import Controller.UserController;
import entity.Inventory;
import entity.Item;
import entity.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Double.parseDouble;

public class Boundary {

    private UserController userController = new UserController();
    private ItemController itemController = new ItemController();
    private OrderController orderController = new OrderController();
    private InventoryController inventoryController;
    Inventory inventory = new Inventory();

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
        String storeId = userController.getCurrentUser().getStoreId();
        String createBy = "";
        String customerPhoneNumber = "null";
        LocalDateTime createTime = LocalDateTime.now();
        String customerName = "";
        double totalPrice = 0;
        String status = "Ready";
        ItemController itemController = new ItemController();
        if (storeId.equals("0")){
            Scanner scan = new Scanner(System.in);
            System.out.println("Welcome, Oliver, Please enter the storeID of your current store.(1-10)");
            storeId = scan.nextLine();
        }
            InventoryController inventoryController = new InventoryController(storeId);

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

                            inventory.getItemAndItsQuantity().put(itemController.searchItemByName(itemName), number - itemNumber);

                            System.out.println("Do you want to continue add item? y/n");
                            a = confirm();
                        } else {
                            System.out.println("Item does not exit or the inventory of this item is not enough");
                        }
                    }
                }
            }

            orderId = Integer.toString(orderController.getOrderList().size() + 1);
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
            System.out.println("Do you want to confirm this order? Press y to confirm or n to exit");
            b = confirm1();

            if (b) {
                itemAndItsQuantity.clear();
                a = true;
            }
        }
        orderController.createOrder(orderId, itemAndItsQuantity, createBy, createTime, customerName, totalPrice, storeId, status, customerPhoneNumber);
        System.out.println("Create order successfully!");
        pressToContinue();
//        System.out.println(orderController.getOrderList().toString());
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
                System.out.println("7. Show the days made the most sale in the last month");
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
                System.out.println("7. Show the days made the most sale in the last month");
                break;

            case "Staff":
                System.out.println("Welcome to the Bask shop system!");
                System.out.println("Please choose the options below:");
                System.out.println("1. Create order");
//              System.out.println("2. Show the total number of coffee beans sold in last month in each store");
//              System.out.println("3. Show the total number of food items sold in last month in each store");
//              System.out.println("4. Show the total number of coffee items sold in last month in each store");
//              System.out.println("5. Show the total sale made in dollars last month in each store");
//              System.out.println("6. Show the type of coffee sold the most per store in the last month");
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
        pressToContinue();

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
        pressToContinue();
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
        pressToContinue();
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
        pressToContinue();

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

        pressToContinue();


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
        ArrayList<Order> orders = orderController.getOrderList();
        Map<LocalDate, Double> DateTimeAndSale = new HashMap<>();
        LocalDate currentDate = LocalDate.now();

        for (Order order : orders) {
            for (Map.Entry<Item, Integer> entry : order.getItemAndItsQuantity().entrySet()) {
                double quantity = entry.getValue();
                if (order.getCreateTime().toLocalDate().isAfter(currentDate.minusMonths(1))) {
                   DateTimeAndSale.put(order.getCreateTime().toLocalDate(), quantity);
               }

            }
        }


        for (Map.Entry<LocalDate, Double> entry : DateTimeAndSale.entrySet()) {
            LocalDate dateTime = entry.getKey();
            double sale = entry.getValue();
            if (DateTimeAndSale.containsKey(dateTime)) {
                double sum = parseDouble(DateTimeAndSale.get(dateTime).toString());
                sale += sum;
                DateTimeAndSale.put(dateTime, sale);
            }

        }

        for (int i = 1; i<11; i++) {
            for (Order order : orders) {
                int storeId = Integer.parseInt(order.getStoreId());
                if (storeId == i) {
                    double max = 0.0;
                    double value = 0.0;
                    String temp = " ";
                    for (LocalDate key : DateTimeAndSale.keySet()) {
                        value = DateTimeAndSale.get(key);
                        if (max < value) {
                            max = value;
                            temp = key.toString();
                        }
                    }
                    System.out.println("The most sale is" + " "+ max + ", " + "the day made the most sale is" +" " + temp );
                }
            }
        }
        pressToContinue();


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
                case "7":
                    showDaysOfWeekMadeMostSale();
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
        if (choice.toUpperCase().equals("N")){
            return false;
        }else {
            return true;
        }
    }

    public Boolean confirm1(){
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        if (choice.toUpperCase().equals("Y")){
            return false;
        }else {
            return true;
        }
    }

    public void pressToContinue() {
        while (true) {
            System.out.println("Press N to exit, or any other keys to back to main menu");
            Scanner sc = new Scanner(System.in);
            String ifContinue = sc.nextLine();
            if (ifContinue.toUpperCase().equals("N"))
                break;
            else
                showMenu();
                chooseOption();
        }
    }
    }


