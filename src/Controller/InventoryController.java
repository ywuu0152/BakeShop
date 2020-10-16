package Controller;

import entity.Inventory;
import entity.Item;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class InventoryController {
    public static void main(String[] args) {
        InventoryController inventoryController = new InventoryController("1");
    }

    public InventoryController(String storeId){
        readInventory(storeId);
    }

    public ArrayList<Inventory> inventoryList = new ArrayList();
    ItemController itemController = new ItemController();

    public Inventory inventory = new Inventory();

    public void readInventory(String storeId){

        String fileName = "File/Inventory" + storeId;
        File file = new File(fileName);

        try {
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()){
                String textLine = scan.nextLine();
                String[] str = textLine.split(",");
                inventory.getItemAndItsQuantity().put(itemController.searchItemByName(str[0]), Integer.parseInt(str[1]));
                }
            inventoryList.add(inventory);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int searchItemQuantity (String itemName){
        return inventory.getItemAndItsQuantity().get(itemController.searchItemByName(itemName));

    }




}
