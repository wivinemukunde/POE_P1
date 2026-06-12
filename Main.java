/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject;

/**
 *
 * @author LENOVO
 */

import java.util.Scanner;
public class Main {
     public static void main(String[] args) {
         try (Scanner scanner = new Scanner(System.in)) {
             User registeredUser ;
             
             System.out.println("==================================================");
             System.out.println("     REGISTRATION AND LOGIN SYSTEM");
             System.out.println("==================================================");
             
             // Ask if user wants to run tests first
             System.out.print("\nRun tests first? (yes/no): ");
             String runTests = scanner.nextLine();
             if (runTests.equalsIgnoreCase("yes")) {
                 runAllTests();
                 System.out.println("\n" + "=".repeat(50));
                 System.out.println("Tests complete! Now starting main program...");
                 System.out.println("=".repeat(50));
             }
             
             // Registration Phase
             System.out.println("\n--- REGISTRATION ---");
             
             // Get username
             String username = "";
             boolean validUsername = false;
             while (!validUsername) {
                 System.out.print("Enter username (must contain '_' and be <= 5 chars): ");
                 username = scanner.nextLine();
                 System.out.println(User.getUsernameMessage(username));
                 if (User.isUsernameValid(username)) {
                     validUsername = true;
                 }
             }
             
             // Get password
             String password = "";
             boolean validPassword = false;
             while (!validPassword) {
                 System.out.print("Enter password (8+ chars, capital, number, special char): ");
                 password = scanner.nextLine();
                 System.out.println(User.getPasswordMessage(password));
                 if (User.isPasswordValid(password)) {
                     validPassword = true;
                 }
             }
             
             // Get cell phone number
             String cellNumber = "";
             boolean validCell = false;
             while (!validCell) {
                 System.out.print("Enter South African cell number (format: +27XXXXXXXXX): ");
                 cellNumber = scanner.nextLine();
                 System.out.println(User.getCellNumberMessage(cellNumber));
                 if (User.isCellNumberValid(cellNumber)) {
                     validCell = true;
                 }
             }
             
             // Get first and last name
             System.out.print("Enter your first name: ");
             String firstName = scanner.nextLine();
             System.out.print("Enter your last name: ");
             String lastName = scanner.nextLine();
             
             // Register user
             registeredUser = User.registerUser(username, password, cellNumber, firstName, lastName);
             
             if (registeredUser != null) {
                 System.out.println("\n✓ Registration successful!");
             } else {
                 System.out.println("\n✗ Registration failed due to validation errors.");
                 scanner.close();
                 return;
             }
             
             // Login Phase
             System.out.println("\n--- LOGIN ---");
             boolean loggedIn = false;
             int attempts = 0;
             
             while (!loggedIn && attempts < 3) {
                 System.out.print("Enter username: ");
                 String loginUsername = scanner.nextLine();
                 System.out.print("Enter password: ");
                 String loginPassword = scanner.nextLine();
                 
                 String msg = User.getLoginMessage(registeredUser, loginUsername, loginPassword);
                 System.out.println(msg);
                 
                 if (User.authenticate(registeredUser, loginUsername, loginPassword)) {
                     loggedIn = true;
                     System.out.println("\n✓ You have successfully logged in!");
                 } else {
                     attempts++;
                     if (attempts < 3) {
                         System.out.println("Attempts remaining: " + (3 - attempts));
                     }
                 }
             }
             
             if (!loggedIn) {
                 System.out.println("\n✗ Too many failed attempts. Please try again later.");
             }}
    }
    
    // ============ BUILT-IN TESTS ============
    
    public static void runAllTests() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("     RUNNING BUILT-IN TESTS");
        System.out.println("=".repeat(50));
        
        int passed = 0;
        int failed = 0;
        
        // Test 1: Username validation
        System.out.println("\n--- Username Validation Tests ---");
        
        // Valid username test
        if (User.isUsernameValid("kyl_1")) {
            System.out.println("  ✓ Valid username 'kyl_1' - PASSED");
            passed++;
        } else {
            System.out.println("  ✗ Valid username 'kyl_1' - FAILED");
            failed++;
        }
        
        // Invalid username test
        if (!User.isUsernameValid("kyle!!!!!!")) {
            System.out.println("  ✓ Invalid username 'kyle!!!!!!' - PASSED");
            passed++;
        } else {
            System.out.println("  ✗ Invalid username 'kyle!!!!!!' - FAILED");
            failed++;
        }
        
        // Test 2: Password validation
        System.out.println("\n--- Password Validation Tests ---");
        
        // Valid password test
        if (User.isPasswordValid("Ch&sec@ke99!")) {
            System.out.println("  ✓ Valid password 'Ch&sec@ke99!' - PASSED");
            passed++;
        } else {
            System.out.println("  ✗ Valid password 'Ch&sec@ke99!' - FAILED");
            failed++;
        }
        
        // Invalid password test
        if (!User.isPasswordValid("password")) {
            System.out.println("  ✓ Invalid password 'password' - PASSED");
            passed++;
        } else {
            System.out.println("  ✗ Invalid password 'password' - FAILED");
            failed++;
        }
        
        // Test 3: Cell number validation
        System.out.println("\n--- Cell Number Validation Tests ---");
        
        // Valid cell test
        if (User.isCellNumberValid("+27838968976")) {
            System.out.println("  ✓ Valid cell '+27838968976' - PASSED");
            passed++;
        } else {
            System.out.println("  ✗ Valid cell '+27838968976' - FAILED");
            failed++;
        }
        
        // Invalid cell test
        if (!User.isCellNumberValid("08966553")) {
            System.out.println("  ✓ Invalid cell '08966553' - PASSED");
            passed++;
        } else {
            System.out.println("  ✗ Invalid cell '08966553' - FAILED");
            failed++;
        }
        
        // Test 4: Registration and login integration
        System.out.println("\n--- Integration Tests ---");
        
        User testUser = User.registerUser("test_1", "Test123!", "+27831234567", "Test", "User");
        if (testUser != null) {
            System.out.println("  ✓ User registration - PASSED");
            passed++;
            
            if (User.authenticate(testUser, "test_1", "Test123!")) {
                System.out.println("  ✓ Login with correct credentials - PASSED");
                passed++;
            } else {
                System.out.println("  ✗ Login with correct credentials - FAILED");
                failed++;
            }
            
            if (!User.authenticate(testUser, "test_1", "WrongPass")) {
                System.out.println("  ✓ Login with wrong password - PASSED");
                passed++;
            } else {
                System.out.println("  ✗ Login with wrong password - FAILED");
                failed++;
            }
        } else {
            System.out.println("  ✗ User registration - FAILED");
            failed++;
        }
        
        // Test 5: Message tests
        System.out.println("\n--- Message Tests ---");
        
        String usernameMsg = User.getUsernameMessage("kyl_1");
        if (usernameMsg.equals("Username successfully captured.")) {
            System.out.println("  ✓ Username success message - PASSED");
            passed++;
        } else {
            System.out.println("  ✗ Username success message - FAILED");
            failed++;
        }
        
        String passwordMsg = User.getPasswordMessage("weak");
        if (passwordMsg.contains("not correctly formatted")) {
            System.out.println("  ✓ Password error message - PASSED");
            passed++;
        } else {
            System.out.println("  ✗ Password error message - FAILED");
            failed++;
        }
        
        // Final results
        System.out.println("\n" + "=".repeat(50));
        System.out.println("     TEST RESULTS");
        System.out.println("=".repeat(50));
        System.out.println("  Passed: " + passed);
        System.out.println("  Failed: " + failed);
        System.out.println("=".repeat(50));
        
        if (failed == 0) {
            System.out.println("\n✓ ALL TESTS PASSED!");
        } else {
            System.out.println("\n✗ SOME TESTS FAILED. Please check your code.");
        }
    }
}

