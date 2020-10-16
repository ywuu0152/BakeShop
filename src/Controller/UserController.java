package Controller;

import entity.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class UserController {

    public static void main(String[] args) {
        UserController userController = new UserController();
    }
    ArrayList<User> userList=  new ArrayList();
    User currentUser = new User();
    public UserController() {
        readUser();
    }

    public void readUser() {
        try {
            File file = new File("File/User");
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()){
                String textLine = scan.nextLine();
                String[] str = textLine.split(",");
                User user = new User();
                user.setId(str[0]);
                user.setUsername(str[1]);
                user.setEmail(str[2]);
                user.setPassword(str[3]);
                user.setRole(str[4]);
                user.setTfn(str[5]);
                user.setAddress(str[6]);
                user.setPostal(str[7]);
                user.setPhoneNumber(str[8]);
                user.setStoreId(str[9]);
                userList.add(user);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
         }
    }

    public boolean login(String userName, String passWord){

        boolean a = false;
        for (User i: userList) {
            if (i.getUsername().equals(userName) && i.getPassword().equals(passWord)){
                a = true;
                currentUser = i;
        }
        }
        return a;
    }

    public String searchUserStoreIdByName(String userName){
        for (User u: userList) {
            if (u.getUsername().equals(userName)) {
                return u.getStoreId();
            }
        }
        return null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}

