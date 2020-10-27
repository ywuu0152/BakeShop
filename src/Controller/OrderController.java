package Controller;

import entity.Inventory;
import entity.Item;
import entity.Order;

import java.io.*;
import java.text.SimpleDateFormat;
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

            while (scan.hasNext()){
                Order order = new Order();
                String textLine = scan.nextLine();
                String[] str = textLine.split(",");
                order.setStoreId(str[0]);
                order.setOrderId(str[1]);
                order.setCreateBy(str[2]);
                String[] orderItem = str[3].split("/");
                String[] orderQuantity = str[4].split("/");
                order.setTotalPrice(Float.parseFloat(str[5]));
                for (int i = 0; i < orderItem.length; i++) {
                    order.getItemAndItsQuantity().put(itemController.searchItemByName(orderItem[i]), Integer.parseInt(orderQuantity[i]));
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy H:mm");
                LocalDateTime date = LocalDateTime.parse(str[6], formatter);
                order.setCustomerName(str[7]);
                order.setStatus(str[8]);
                order.setCustomerPhoneNumber(str[9]);

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
        writeOrder(orderList);
    }

    public void writeOrder(ArrayList<Order> orderList){
        try {

            String pattern = "MM/dd/yyyy H:mm";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            File file = new File("File/Order");
            PrintStream ps = new PrintStream(new FileOutputStream(file));

            for (Order o: orderList) {
                String orderItem = "";
                String orderQtt = "";

                for (Map.Entry<Item, Integer> entry: o.getItemAndItsQuantity().entrySet()){
                    orderItem = orderItem + entry.getKey().getItemName() + "/";
                    orderQtt = orderQtt + entry.getValue() + "/";
                }
                String format = formatter.format(o.getCreateTime());
                String output = o.getStoreId() + "," + o.getOrderId() + "," +
                        o.getCreateBy() + "," + orderItem + "," + orderQtt + "," + o.getTotalPrice() + "," + format + "," + o.getCustomerName()
                        + "," + o.getStatus()+ "," + o.getCustomerPhoneNumber();
                System.out.println(output);
                ps.println(output);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}

