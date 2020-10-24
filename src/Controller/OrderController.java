package Controller;

import entity.Inventory;
import entity.Item;
import entity.Order;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderController {

    public static void main(String[] args) {
        OrderController orderController = new OrderController();
        orderController.readOrder();
    }

    private ArrayList<Order> orderList= new ArrayList();

    ItemController itemController = new ItemController();

    public OrderController(){
        readOrder();
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }

    public void readOrder(){
        File file = new File("File/Order");
        try {
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()){
                Order order = new Order();
                String textLine = scan.nextLine();
                String[] str = textLine.split(",");
                order.setStoreId(str[0]);
                order.setOrderId(str[1]);
                order.setCreateBy(str[2]);
                String[] orderItem = str[3].split("/");
                String[] orderQuantity = str[4].split("/");
                String[] orderUnitPrice = str[5].split("/");
                for (int i = 0; i < orderItem.length; i++) {
                    order.getItemAndItsQuantity().put(itemController.searchItemByName(orderItem[i]), Integer.parseInt(orderQuantity[i]));
                }

                float total = 0;

                for (int i = 0; i < orderQuantity.length; i++) {
                    total += Integer.parseInt(orderQuantity[i]) * Float.parseFloat(orderUnitPrice[i]);
                }
                order.setTotalPrice(total);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy H:mm");
                LocalDateTime date = LocalDateTime.parse(str[7] + " " + str[8], formatter);
                order.setCustomerName(str[9]);
                order.setStatus(str[10]);
                order.setCustomerPhoneNumber(str[11]);

                order.setCreateTime(date);

                orderList.add(order);
            }
//            System.out.println(orderList.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createOrder(String orderId, Map<Item, Integer> itemAndItsQuantity, String createBy, LocalDateTime createTime,
                            String customerName, double totalPrice, String storeId, String status, String customerPhoneNumber) {
        Order order = new Order(orderId, itemAndItsQuantity, createBy, createTime, customerName, totalPrice, storeId, status, customerPhoneNumber);
        orderList.add(order);
    }


}

