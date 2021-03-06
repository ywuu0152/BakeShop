package boundary;

import Controller.InventoryController;
import Controller.ItemController;
import Controller.OrderController;
import Controller.UserController;
import entity.Inventory;
import entity.Item;
import entity.Order;


import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
            System.out.println("Welcome, Oliver, Please enter the storeID of your current store.(1-10)");
            boolean c = true;
            while (c) {
                Scanner scan = new Scanner(System.in);
                if (scan.hasNextInt()) {
                    storeId = scan.next();
                    if (Integer.parseInt(storeId) >= 1 && Integer.parseInt(storeId) <= 10){
                        c = false;
                    }else {
                        System.out.println("Please input a number (1-10)");
                    }
                } else {
                    System.out.println("Please input a number (1-10)");
                }
            }
        }
        InventoryController inventoryController = new InventoryController(storeId);

        Map<Item,Integer> itemAndItsQuantity = new HashMap<>();

        while (b) {
            while (a) {
                Scanner scan = new Scanner(System.in);
                System.out.println("=============================================");
                System.out.println("Today we offer: ");
                System.out.println("Coffee: ice coffee, Cappuccino");
                System.out.println("Food: toast skagen, chocolate chip pancake");
                System.out.println("Coffee beans");
                System.out.println("=============================================");
                System.out.println("Please enter your item name from above");
                System.out.println("=============================================");
                String itemName = scan.nextLine();
                String num;
                int itemNumber = 0;
                for (Item i : itemController.itemList) {
                    if (i.getItemName().equals(itemName)) {
                        System.out.println("Please enter your item quantity");
                        if (itemName.equals("coffee beans")){
                            System.out.println("Please enter the phone number of customer");
                            Scanner scanner = new Scanner(System.in);
                            customerPhoneNumber = scanner.nextLine();
                        }

                        if(scan.hasNextInt()){
                            int number = Integer.parseInt(scan.next());
                            if (number > 0 && number <= 10){
                                itemNumber = number;
                            }else {
                                System.out.println("Invalid number! Please input a number between 1 to 10");
                                break;
                            }
                        }else {
                            System.out.println("Invalid number! Please input a number between 1 to 10");
                            break;
                        }

                        int number = inventoryController.searchItemQuantity(itemName);
                        if (number >= itemNumber) {

                            itemAndItsQuantity.put(i, itemNumber);

                            inventory.getItemAndItsQuantity().put(itemController.searchItemByName(itemName), number - itemNumber);

                            System.out.println("Do you want to continue add item? y/n");
                            a = confirm();
                            break;
                        } else {
                            System.out.println("The inventory of this item is not enough");
                        }
                    }else {
                        System.out.println("Item does not exit");
                        break;
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

    public void addInventory(){
        String storeId = userController.getCurrentUser().getStoreId();
        int quantity;
        boolean a = true;
        Scanner scan = new Scanner(System.in);

        if (storeId.equals("0")){
            System.out.println("Welcome, Oliver, Please enter the storeID of your current store.(1-10)");

            boolean c = true;
            while (c) {
                Scanner scanner = new Scanner(System.in);
                if (scan.hasNextInt()) {
                    storeId = scanner.next();
                    if (Integer.parseInt(storeId) >= 1 && Integer.parseInt(storeId) <= 10){
                        c = false;
                    }else {
                        System.out.println("Please input a number (1-10)");
                    }
                } else {
                    System.out.println("Please input a number (1-10)");
                }
            }
        }

        InventoryController inventoryController = new InventoryController(storeId);

        System.out.println(inventoryController.getInventoryList().toString());

        System.out.println("Please enter the item name you want to increase inventory");
        String itemName = scan.nextLine();

        while (a) {
            for (Item i : itemController.itemList) {
                if (i.getItemName().equals(itemName)) {
                    System.out.println("Please enter the quantity");
                    if (scan.hasNextInt()) {
                        int number = Integer.parseInt(scan.next());
                        if (number > 0 && number <= 999) {
                            quantity = number;
                        } else {
                            System.out.println("Invalid number! Please re-input");
                            break;
                        }
                    } else {
                        System.out.println("Invalid number! Please re-input");
                        break;
                    }
                    int number = inventoryController.searchItemQuantity(itemName);

                    inventory.getItemAndItsQuantity().put(itemController.searchItemByName(itemName), quantity + number);

                    inventoryController.getInventoryList().add(inventory);

                    System.out.println("Inventory increase successfully");

                    System.out.println("The inventory of " + itemName + "now is: " + (quantity + number));
                    a = false;
                    break;
                } else {
                    System.out.println("Item does not exit");
                    break;
                }
            }
        }

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
                System.out.println("8. Show items with low quantity");
                System.out.println("9. Increase inventory ");
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
                System.out.println("8. Show items with low quantity");
                System.out.println("9. Increase inventory ");
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

    public void showLowInInventory() {
        System.out.println("Please enter the store id to view the report:");
        boolean c = true;
        String storeId = "0";
        while (c) {
            Scanner scan = new Scanner(System.in);
            if (scan.hasNextInt()) {
                storeId = scan.next();
                if (Integer.parseInt(storeId) >= 1 && Integer.parseInt(storeId) <= 10){
                    c = false;
                }else {
                    System.out.println("Please input a number (1-10)");
                }
            } else {
                System.out.println("Please input a number (1-10)");
            }
        }
        var controller = new InventoryController(storeId);
        var inventory = controller.getInventoryList().get(0);
        var lowItems = new HashMap<Item,Integer>();
        for(var entry : inventory.getItemAndItsQuantity().entrySet()){
            if(entry.getValue() < 3){
                lowItems.put(entry.getKey(), entry.getValue());
            }
        }
        if(lowItems.size() == 0){
            System.out.println("There is no item with low quantity in store " + storeId);
            pressToContinue();
            return;
        }
        System.out.println("The following items are in low quantity:");
        for(var entry : lowItems.entrySet()){
            System.out.println(entry.getKey().getItemName() + ": " + entry.getValue());
        }
        pressToContinue();
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

            if (quantity == 0){
                System.out.println("--------------------------------------");
                System.out.println("No sales in store " + i + " last month");
            }else {
                System.out.println("--------------------------------------");
                System.out.println("Store " + i + " sales: " + quantity);
            }
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
        System.out.println("--------------------------------------");
        System.out.println("Time" + currentTime);
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
            if (quantity == 0){
                System.out.println("--------------------------------------");
                System.out.println("No sales in store " + i + " last month");
            }else {
                System.out.println("--------------------------------------");
                System.out.println("Store " + i + " sales: " + quantity);
            }
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
        System.out.println("--------------------------------------");
        System.out.println("Time: " + currentTime);
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

            if (quantity == 0){
                System.out.println("--------------------------------------");
                System.out.println("No sales in store " + i + " last month");

            }else {
                System.out.println("--------------------------------------");
                System.out.println("Store " + i + " sales: " + quantity);
            }
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
        System.out.println("--------------------------------------");
        System.out.println("Time" + currentTime);
        for (int i = 1; i < 11; i++) {
            double dollars = 0;
            for (Order o: newOrderList) {
                int storeId = Integer.parseInt(o.getStoreId());
                if (storeId == i) {
                    dollars += o.getTotalPrice();
                }
            }
            System.out.println("--------------------------------------");
            System.out.println("The total sales of store " + i + ":" + " $ " + dollars);
        }
        pressToContinue();

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
        //ArrayList<Order> orders = orderController.getOrderList();
        Map<LocalDate, Double> DateTimeAndSale = new HashMap<>();
        LocalDate currentDate = LocalDate.now();

        ArrayList<Order> newOrderList= new ArrayList();


        for (Order order: orderController.getOrderList()) {
            //String date = "";
            //double price = DateTimeAndSale.containsKey(date) ? DateTimeAndSale.get(date) : 0 ;/double price = 0;
            //String date = "";
            //double price = 0;
            if (order.getCreateTime().toLocalDate().isAfter(currentDate.minusDays(30))) {
                LocalDate date = order.getCreateTime().toLocalDate();
                double price = order.getTotalPrice();
                DateTimeAndSale.merge(date, price, Double::sum);
                newOrderList.add(order);
            }

        }


//        for (Map.Entry<String, Double> entry : DateTimeAndSale.entrySet()) {
//            String dateTime = entry.getKey();
//            double sale = entry.getValue();
//            if (DateTimeAndSale.containsKey(dateTime)) {
//                double sum = DateTimeAndSale.get(dateTime);
//                sale += sum;
//                DateTimeAndSale.put(dateTime, sale);
//            }
//       }

        for (int i = 1; i<11; i++) {
            for (Order order: newOrderList) {
                int storeId = Integer.parseInt(order.getStoreId());
                if (storeId == i) {
                    double max = 0.0;
                    double value = 0.0;
                    LocalDate date;
                    for (LocalDate key : DateTimeAndSale.keySet()) {
                        value = DateTimeAndSale.get(key);
                        if (max < value) {
                            max = value;
                            date = key;
                            System.out.println("The most sale is" + " "+ max + ", " + "the day made the most sale is" +" " + date.getDayOfWeek() );
                        }

                    }

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
                case "8":
                    showLowInInventory();
                    a = false;
                    break;
                case "9":
                    addInventory();
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
            System.out.println("=============================================================");
            System.out.println("=============================================================");
            System.out.println("Press N to exit, or any other keys to back to main menu");
            Scanner sc = new Scanner(System.in);
            String ifContinue = sc.nextLine();
            if (ifContinue.toUpperCase().equals("N"))
                System.exit(0);
            else
                showMenu();
            chooseOption();
        }
    }


}



