import Controller.InventoryController;
import Controller.ItemController;
import Controller.OrderController;
import Controller.UserController;
import boundary.Boundary;
import entity.Item;
import entity.Order;
import entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Login {
    public static void main(String[] args) {
        Login login = new Login();
        login.enter();
        login.showMenu();

        login.chooseOption();

    }

    Boundary boundary = new Boundary();

    public void enter() {
        Scanner userInput = new Scanner(System.in);
        boolean a = true;
        while (a){
            System.out.println("Please enter your username ");
            String username = userInput.nextLine();

            System.out.println("Please enter your password: ");
            String userPassWord = userInput.nextLine();

            if (boundary.login(username, userPassWord)) {
                System.out.println("Login successfully");
                a = false;
            }else{
                System.out.println("Login unsuccessfully, please re-enter your username and password");
            }
        }
    }


    public void createItemAndQuantity() {
        boundary.showCreateOrderPage();
    }

    public void showMenu(){
        boundary.showMenu();
    }

    public void chooseOption(){
        boundary.chooseOption();
    }



}
