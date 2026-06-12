/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.poepart1;
/**
 *
 * @author LENOVO
 */

public class Main {
  
    // Global counters hosted in Main that Part 2 calls to update
    static int totalMessagesOfficiallySent = 0;
    static int totalLogSessionMessagesSaved = 0;

    public static void main(String[] args) {
        // Automatically load users from Part 1 text database
        PoePart1.loadUsers();
        System.out.println("======================================");
        System.out.println("WELCOME TO THE REGISTRATION SYSTEM");
        System.out.println("======================================");

        System.out.println("\nINSTRUCTIONS:");
        System.out.println("1. First Name: Letters only");
        System.out.println("2. Surname: Letters only");
        System.out.println("3. Username: Must contain '_' and be <= 5 characters");
        System.out.println("4. Password: At least 8 characters with capital, number, special character");
        System.out.println("5. Phone Number: SA format ('+27' followed by exactly 9 digits (e.g., +27821234567)), unique");

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Stored Messages");
            System.out.println("4. Exit");
            String choice = PoePart1.getInput("Enter choice (1-4): ");

            if (choice == null) {
                System.out.println("Returning to menu...");
                continue;
            }

            switch (choice) {
                case "1" -> PoePart1.register();

                case "2" -> {
                    // Only check login state after a login attempt
                    PoePart1.login();
                    if (PoePart1.isLoggedIn()) {
                        PoePart2.runMessagingSystem();
                        
                       
                        PoePart1.setLoggedIn(false);
                        
                        
                        PoePart3.runStoredMessagesMenu();
                    }
                }

                case "3" -> {
                   
                    PoePart3.runStoredMessagesMenu(); 
                }

                case "4" -> {
                    
                    printFinalSessionTotals();
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // Methods called by Part 2 to increment counters
    public static void incrementSentCount() {
        totalMessagesOfficiallySent++;
    }

    public static void incrementSavedCount() {
        totalLogSessionMessagesSaved++;
    }

    // Displays the final summary 
    public static void printFinalSessionTotals() {
        System.out.println("\n=================================");
        System.out.println("Total log session messages saved: " + totalLogSessionMessagesSaved);
        System.out.println("Total messages officially sent: " + totalMessagesOfficiallySent);
        System.out.println("=================================");
        System.out.println("Goodbye.");
    }
}
