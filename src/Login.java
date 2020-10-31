import boundary.Boundary;

import java.util.Scanner;

public class Login {
//    private Boundary boundary;

    public Login() {
    }

    public Login(Boundary boundary) {
        this.boundary = boundary;
    }

    public static void main(String[] args) {
//        Boundary boundary = new Boundary();
        Login login = new Login();
        login.loginPage();
        //login.enter();
        //login.showMenu();
        //login.chooseOption();

    }

    Boundary boundary = new Boundary();


    public void enter() {
        Scanner userInput = new Scanner(System.in);
        boolean a = true;
        while (a){
            System.out.println("==================================================");
            System.out.println("==================================================");
            System.out.println("Dear User, To Login");
            System.out.println("Please enter your username: ");
            String username = userInput.nextLine();

            System.out.println("Please enter your password: ");
            String userPassWord = userInput.nextLine();

            if (boundary.login(username, userPassWord)) {
                System.out.println("Login successfully");
                a = false;
            }else{
                System.out.println("Login unsuccessfully, Something incorrect of your username or password");
                System.out.println("Please re-enter your username and password");
            }
        }
    }

    public void loginPage() {
        Scanner userInput = new Scanner(System.in);
        boolean a = true;
        while (a) {
            System.out.println("Dear user");
            System.out.println("Welcome to the Bake Shop Software System");
            System.out.println("---------------------------------------------------");
            System.out.println("To exit please press Q or any other keys to login");
            chooseLoginOption();
        }
    }

    public void chooseLoginOption(){
        while (true) {
            Scanner sc = new Scanner(System.in);
            String ifContinue = sc.nextLine();
            if (ifContinue.toUpperCase().equals("Q"))
                System.exit(0);
            else
                enter();
                showMenu();
                chooseOption();
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
