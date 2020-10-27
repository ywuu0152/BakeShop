package entity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Order {

    private String orderId;
    private Map<Item, Integer> itemAndItsQuantity = new HashMap<>();
    private String createBy;
    private LocalDateTime createTime;
    private String status;
    private String customerName;
    private double totalPrice;
    private String storeId;
    private String customerPhoneNumber;

    public Order() {

    }

    public Order(String orderId, Map<Item, Integer> itemAndItsQuantity, String createBy, LocalDateTime createTime,
                 String customerName, double totalPrice, String storeId, String status, String customerPhoneNumber) {
        this.orderId = orderId;
        this.itemAndItsQuantity = itemAndItsQuantity;
        this.createBy = createBy;
        this.createTime = createTime;
        this.status = status;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
        this.storeId = storeId;
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Map<Item, Integer> getItemAndItsQuantity() {
        return itemAndItsQuantity;
    }

    public void setItemAndItsQuantity(Map<Item, Integer> itemAndItsQuantity) {
        this.itemAndItsQuantity = itemAndItsQuantity;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", itemAndItsQuantity=" +itemAndItsQuantity + '\'' +
                ", createBy= " + createBy + '\'' +
                ", createTime=" + createTime + '\'' +
                ", status=" + status + '\'' +
                ", customerName= " + customerName + '\'' +
                ", totalPrice=" + totalPrice + '\'' +
                ", storeId='" + storeId + '\'' +
                ", customer phone number='" + customerPhoneNumber + '\'' +
                '}';
    }
}



//for(Item i:itemAndItsQuantity.keySet()) {
//        System.out.println(i.getItemName() + "number: " + itemAndItsQuantity.get(i));
//        }