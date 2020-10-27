package entity;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<Item, Integer> itemAndItsQuantity = new HashMap<>();


    public Map<Item, Integer> getItemAndItsQuantity() {
        return itemAndItsQuantity;
    }

    public void setItemAndItsQuantity(Map<Item, Integer> itemAndItsQuantity) {
        this.itemAndItsQuantity = itemAndItsQuantity;
    }


    @Override
    public String toString(){
        return "Inventory{" +
                "Item and Its quantity = "+
                itemAndItsQuantity
                + '\'' +
                '}';
    }

}
