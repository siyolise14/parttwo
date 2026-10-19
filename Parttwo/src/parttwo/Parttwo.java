/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package parttwo;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class Parttwo {

    public static void main(String[] args) {//start of class
       
        Login register = new Login();
        Scanner scan = new Scanner(System.in);
        
      
        Message msg = new Message();

      
        System.out.println("\n===== Register =====");
        System.out.println("Please enter the username: ");
        String name = scan.nextLine();
        System.out.println("Enter password");
        String password = scan.nextLine();
        System.out.println("Enter cellphone");
        String cellphone = scan.nextLine();

        System.out.println(register.registerUser(name, password, cellphone));

    
        if (!register.registerStatus(name, password, cellphone)) {
            return; 
        }

        System.out.println("\n===== Login =====");
        System.out.print("Enter username: ");
        String loginUsername = scan.nextLine();
        System.out.print("Enter password: ");
        String loginPassword = scan.nextLine();

        boolean status = register.loginUser(loginUsername, loginPassword);
        System.out.println(register.returnLoginStatus(status));

     
        if (status == true) {
            
            
            System.out.println("\nWelcome to QuickChat.");

            int option;

           
            do {
                System.out.println("\n===== Select any of the options below =====");
                System.out.println("1. Send Messages");
                System.out.println("2. Show recently sent messages");
                System.out.println("3. Quit");

                System.out.print("Choice: ");
                option = scan.nextInt();
                scan.nextLine();

                switch (option) {
                    case 1 ->
                        msg.sendingMessages();
                    case 2 ->
                        System.out.println("Coming Soon.");
                    case 3 ->
                        System.out.println("Good bye!!");
                    default ->
                        System.out.println("Invalid option!!");
                }

            } while (option != 3);
        }
    }
}//end of class