package boundary;

import Controller.InventoryController;
import Controller.ItemController;
import Controller.OrderController;
import Controller.UserController;
import entity.Inventory;
import entity.Item;
import entity.Order;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
        LocalDateTime createTime = LocalDateTime.now();
        String customerName = "";
        float totalPrice = 0;
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
                System.out.println(itemAndItsQuantity.get(key));
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
        orderController.createOrder(orderId, itemAndItsQuantity, createBy, createTime, customerName, totalPrice, storeId, status);
        System.out.println("Create order successfully!");

    }

    public void showMenu(){
        String currentUser = userController.getCurrentUser().getRole();

        switch (currentUser){
            case "Manager":
                System.out.println("Welcome to the Bask shop system! Manager");
                System.out.println("Please choose the options below:");
                System.out.println("1. Create order");
                break;

            case "Owner":
                System.out.println("Welcome to the Bask shop system! Oliver");
                System.out.println("Please choose the options below:");
                System.out.println("1. Create order");
                break;

            case "Staff":
                System.out.println("Welcome to the Bask shop system!");
                System.out.println("Please choose the options below:");
                System.out.println("1. Create order");
                break;
        }

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