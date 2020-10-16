package Controller;

import entity.Item;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ItemController {
    public static void main(String[] args) {
        ItemController itemController = new ItemController();

    }

    public ItemController(){
        readItem();
    }

    public ArrayList<Item> itemList = new ArrayList();



    public void readItem(){
        File file = new File("File/Item");
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()){
                String textLine = scan.nextLine();
                String[] str = textLine.split(",");

                Item item = new Item();
                item.setItemName(str[0]);
                item.setPrice(Float.parseFloat(str[1]));
                item.setSort(str[2]);
                item.setItemId(Integer.parseInt(str[3]));

                itemList.add(item);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Item searchItemByName(String itemName){
        for (Item i: itemList) {
            if (i.getItemName().equals(itemName)){
                return i;
            }
        }
        return null;
    }


}
