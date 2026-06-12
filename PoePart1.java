/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.myproject.poepart1;
import java.util.*;
import java.io.*;
import java.util.regex.Pattern;
/**
 *
 * @author LENOVO
 */
public class PoePart1 {
 
    private static final HashMap<String, User> users = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "users.txt";

    static class User {
        String username, password, phoneNumber, firstName, surname;

        User(String username, String password, String phoneNumber, String firstName, String surname) {
            this.username = username;
            this.password = password;
            this.phoneNumber = phoneNumber;
            this.firstName = firstName;
            this.surname = surname;
        }

        public String toFileString() {
            return username + "," + password + "," + phoneNumber + "," + firstName + "," + surname;
        }
    }

    // LOAD USERS
    public static void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    users.put(data[0], new User(data[0], data[1], data[2], data[3], data[4]));
                }
            }
        } catch (IOException e) {
            
        }
    }

    // SAVE USERS
    public static void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (User user : users.values()) {
                writer.write(user.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    // INPUT WITH 3 ATTEMPTS 
    public static String getInput(String message) {

        int attempts = 3;

        while (attempts > 0) {

            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            attempts--;
            System.out.println("Invalid input. Attempts left: " + attempts);
        }

        System.out.println("Too many invalid attempts.");
        return null;
    }

    // NAME VALIDATION
    public static String getName(String field) {

        while (true) {

            String name = getInput("Enter " + field + ": ");
            if (name == null) return null;

            if (!name.matches("^[A-Za-z]+$")) {
                System.out.println("Letters only.");
                continue;
            }

            for (User user : users.values()) {
                if (field.equals("first name") && user.firstName.equalsIgnoreCase(name)) {
                    System.out.println("First name already exists.");
                    name = null;
                    break;
                }
                if (field.equals("surname") && user.surname.equalsIgnoreCase(name)) {
                    System.out.println("Surname already exists.");
                    name = null;
                    break;
                }
            }

            if (name != null) return name;
        }
    }

    // USERNAME
    public static String getUsername() {

        while (true) {

            String username = getInput("Enter username (_ and <=5 chars): ");
            if (username == null) return null;

            if (!username.contains("_") || username.length() > 5) {
                System.out.println("Invalid username.");
                continue;
            }

            if (users.containsKey(username)) {
                System.out.println("Username already exists.");
                continue;
            }

            return username;
        }
    }

    // PASSWORD
    public static String getPassword() {

        while (true) {

            String password = getInput("Enter password: ");
            if (password == null) return null;

            boolean hasCapital = false, hasNumber = false, hasSpecial = false;

            for (char c : password.toCharArray()) {
                if (Character.isUpperCase(c)) hasCapital = true;
                if (Character.isDigit(c)) hasNumber = true;
                if (!Character.isLetterOrDigit(c)) hasSpecial = true;
            }

            if (password.length() >= 8 && hasCapital && hasNumber && hasSpecial) {
                return password;
            }

            System.out.println("Weak password.");
        }
    }

    // PHONE NUMBER
    public static String getPhoneNumber() {

        Pattern pattern = Pattern.compile("^0[6-8][0-9]{8}$");

        while (true) {

            String phoneNumber = getInput("Enter phone number: ");
            if (phoneNumber == null) return null;

            if (!pattern.matcher(phoneNumber).matches()) {
                System.out.println("Invalid phone number.");
                continue;
            }

            for (User user : users.values()) {
                if (user.phoneNumber.equals(phoneNumber)) {
                    System.out.println("Phone number already exists.");
                    phoneNumber = null;
                    break;
                }
            }

            if (phoneNumber != null) return phoneNumber;
        }
    }

    // REGISTER
    public static void register() {

        System.out.println("\n--- REGISTER ---");

        String firstName = getName("first name");
        if (firstName == null) return;

        String surname = getName("surname");
        if (surname == null) return;

        String username = getUsername();
        if (username == null) return;

        String password = getPassword();
        if (password == null) return;

        String phoneNumber = getPhoneNumber();
        if (phoneNumber == null) return;

        users.put(username, new User(username, password, phoneNumber, firstName, surname));
        saveUsers();

        System.out.println("Registration successful.");

        login();
    }

    // LOGIN
    public static void login() {

        System.out.println("\n--- LOGIN ---");

        int attempts = 3;

        while (attempts > 0) {

            String username = getInput("Username: ");
            String password = getInput("Password: ");

            if (username == null || password == null) {
                System.out.println("Login cancelled due to invalid input.");
                return;
            }

            if (users.containsKey(username) &&
                users.get(username).password.equals(password)) {

                System.out.println("Welcome " + users.get(username).firstName);
                return;

            } else {
                attempts--;
                System.out.println("Invalid login. Attempts left: " + attempts);
            }
        }

        System.out.println("Too many failed attempts.");
    }

    // MAIN MENU 
     
    public static void main(String[] args) {

        loadUsers();
        
        System.out.println("WELCOME TO THE REGISTRATION SYSTEM");
        System.out.println("\nINSTRUCTIONS:");
        System.out.println("1. First Name: Letters only");
        System.out.println("2. Surname: Letters only");
        System.out.println("3. Username: Must contain '_' and be <= 5 characters");
        System.out.println("4. Password: At least 8 characters with capital, number, special character");
        System.out.println("5. Phone Number: SA format (06/07/08 + 8 [0-9]digits), unique");
       
        while (true) {

            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            String choice = getInput("Enter choice (1-3): ");

            if (choice == null) {
                System.out.println("Returning to menu...");
                continue;
            }

            switch (choice) {

                case "1" -> register();

                case "2" -> login();

                case "3" -> {
                    try (scanner) {
                        System.out.println("Goodbye.");
                    }
                    return;
                }


                default -> System.out.println("Invalid choice.");
            }
        }
    }
} 