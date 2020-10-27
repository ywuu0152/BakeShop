package entity;

public class Item {
    private int itemId;
    private String itemName;
    private int itemNumber;
    private float price;
    private String sort;

    public Item() {

    }

    public Item(String itemName, int itemNumber, float price, String sort, int itemId) {

        this.itemName = itemName;
        this.itemNumber = itemNumber;
        this.price = price;
        this.sort = sort;
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "Item{" +
                  itemName +
                "}";

//        return "Item{" +
//                "itemId='" + itemId + '\'' +
//                "itemName='" + itemName + '\'' +
//                ", sort='" + sort + '\'' +
//                ", price=" + price + '\'' +
//                '}';
    }
}
