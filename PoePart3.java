package com.myproject.poepart1;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author LENOVO
 */
public class PoePart3 {
    public static final List<String> messageIds      = new ArrayList<>();
    public static final List<String> messageHashes   = new ArrayList<>();
    public static final List<String> recipients      = new ArrayList<>();
    public static final List<String> messageBodies   = new ArrayList<>();
    public static final List<String> messageFlags    = new ArrayList<>();

    public static final List<String> sentMessages        = new ArrayList<>();
    public static final List<String> disregardedMessages = new ArrayList<>();
    public static final List<String> storedMessages      = new ArrayList<>();

   
    public static void populateArraysFromJSON() {
        messageIds.clear();
        messageHashes.clear();
        recipients.clear();
        messageBodies.clear();
        messageFlags.clear();
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();

        try (FileReader reader = new FileReader("messages.json");
             Scanner fileScanner = new Scanner(reader)) {

            StringBuilder jsonContent = new StringBuilder();
            while (fileScanner.hasNextLine()) {
                jsonContent.append(fileScanner.nextLine());
            }

            String content = jsonContent.toString();
            String[] objects = content.split("\\}\\,?\\s*\\{");

            for (String obj : objects) {
                String id   = extractJSONField(obj, "messageId");
                String cell = extractJSONField(obj, "recipientCell");
                String text = extractJSONField(obj, "messageText");
                String hash = extractJSONField(obj, "messageHash");
                String flag = extractJSONField(obj, "messageFlag");

                if (flag.isEmpty()) {
                    flag = "Stored";
                }

                if (id.isEmpty() || text.isEmpty()) continue;

                
                messageIds.add(id);
                recipients.add(cell);
                messageHashes.add(hash);
                messageBodies.add(text);  
                messageFlags.add(flag);   

                
                if (flag.equalsIgnoreCase("Sent")) {
                    sentMessages.add(text);
                } else if (flag.equalsIgnoreCase("Disregard") || flag.equalsIgnoreCase("Disregarded")) {
                    disregardedMessages.add(text);
                } else {
                    storedMessages.add(text);
                }
            }
            System.out.println(" Total records loaded: " + messageIds.size());
        } catch (IOException e) {
            System.out.println("Could not parse file data: " + e.getMessage());
        }
    }

    
    public static void saveMessagesToJSON() {
        try (FileWriter writer = new FileWriter("messages.json")) {
            writer.write("[\n");
            for (int i = 0; i < messageIds.size(); i++) {
                writer.write("  {\n");
                writer.write("    \"messageId\": \"" + messageIds.get(i) + "\",\n");
                writer.write("    \"recipientCell\": \"" + recipients.get(i) + "\",\n");
                writer.write("    \"messageText\": \"" + messageBodies.get(i) + "\",\n");
                writer.write("    \"messageHash\": \"" + messageHashes.get(i) + "\",\n");
                writer.write("    \"messageFlag\": \"" + messageFlags.get(i) + "\"\n");
                writer.write("  }");
                if (i < messageIds.size() - 1) writer.write(",");
                writer.write("\n");
            }
            writer.write("]\n");
            System.out.println("Messages saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Could not save messages to file: " + e.getMessage());
        }
    }


  
    public static void loadTestData() {
        messageIds.clear();
        messageHashes.clear();
        recipients.clear();
        messageBodies.clear();
        messageFlags.clear();
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();

        addMessage("MSG001", "+27834557896",  "Did you get the cake?",
                   "HASH001", "Sent");
        addMessage("MSG002", "+27838884567",
                   "Where are you? You are late! I have asked you to be on time.",
                   "HASH002", "Stored");
        addMessage("MSG003", "+27834484567",  "Yohoooo, I am at your gate.",
                   "HASH003", "Disregard");
        addMessage("0838884567", "0838884567", "It is dinner time!",
                   "HASH004", "Sent");
        addMessage("MSG005", "+27838884567",  "Ok, I am leaving without you.",
                   "HASH005", "Stored");
    }

    public static void addMessage(String id, String recipient,
                                   String text, String hash, String flag) {
        messageIds.add(id);
        recipients.add(recipient);
        messageBodies.add(text);
        messageHashes.add(hash);
        messageFlags.add(flag);

        if (flag.equalsIgnoreCase("Sent")) {
            sentMessages.add(text);
        } else if (flag.equalsIgnoreCase("Disregard") || flag.equalsIgnoreCase("Disregarded")) {
            disregardedMessages.add(text);
        } else {
            storedMessages.add(text);
        }
    }

    private static String extractJSONField(String chunk, String fieldName) {
        String key = "\"" + fieldName + "\": \"";
        int start = chunk.indexOf(key);
        if (start == -1) return "";
        start += key.length();
        int end = chunk.indexOf("\"", start);
        if (end == -1) return "";
        return chunk.substring(start, end);
    }

    
    // Sub-Menu
   
    public static void runStoredMessagesMenu() {
        populateArraysFromJSON();
        Scanner menuScanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- STORED MESSAGES SUB-MENU ---");
            System.out.println("1 - Display sender and recipient of all stored messages");
            System.out.println("2 - Display the longest stored message");
            System.out.println("3 - Search for a message ID");
            System.out.println("4 - Search for all messages sent to a recipient");
            System.out.println("5 - Delete a message using its Message Hash");
            System.out.println("6 - Display Full Task Report");
            System.out.println("0 - Return to Main Menu");
            System.out.print("Selection: ");

            if (!menuScanner.hasNextInt()) {
                menuScanner.next();
                continue;
            }
            int choice = menuScanner.nextInt();
            menuScanner.nextLine();

            switch (choice) {
                case 1 -> displaySendersAndRecipients();
                case 2 -> displayLongestMessage();
                case 3 -> searchByMessageId(menuScanner);
                case 4 -> searchByRecipient(menuScanner);
                case 5 -> deleteMessageByHash(menuScanner);
                case 6 -> displayReport();
                case 0 -> { return; }
                default -> System.out.println("Invalid selection.");
            }
        }
    }

   
    //   Display sender/recipient stored messages only
   
    private static void displaySendersAndRecipients() {
        System.out.println("\n--- Stored Message Routing Links ---");
        boolean any = false;
        for (int i = 0; i < messageIds.size(); i++) {
            
            if (messageFlags.get(i).equalsIgnoreCase("Stored")) {
                System.out.println("Sender: System User -> Recipient: " + recipients.get(i));
                any = true;
            }
        }
        if (!any) System.out.println("No stored messages found.");
    }

    
    //  Longest stored message
   
    public static String displayLongestMessage() {
        if (storedMessages.isEmpty()) {
            System.out.println("No messages currently categorised as 'Stored'.");
            return "";
        }
        String longest = storedMessages.get(0);
        for (String msg : storedMessages) {
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }
        System.out.println("\nLongest Stored Message:\n\"" + longest + "\"");
        return longest;
    }

    
    //   Search by message ID
   
    public static String searchByMessageId(Scanner scanner) {
        System.out.print("Enter Message ID to locate: ");
        String searchId = scanner.nextLine().trim();

        for (int i = 0; i < messageIds.size(); i++) {
            if (messageIds.get(i).equals(searchId)) {
                
                String content = messageBodies.get(i);
                System.out.println("\nMatch Identified!");
                System.out.println("Recipient Cell: " + recipients.get(i));
                System.out.println("Content: \"" + content + "\"");
                return content;
            }
        }
        System.out.println("Message ID not found.");
        return null;
    }

    
    //   Search by recipient
   
    public static String searchByRecipient(Scanner scanner) {
        System.out.print("Enter Recipient Cell Number: ");
        String targetRecipient = scanner.nextLine().trim();
        StringBuilder results = new StringBuilder();
        boolean found = false;

        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).equals(targetRecipient)) {
                
                String content = messageBodies.get(i);
                System.out.println("- Matched message content: \"" + content + "\"");
                results.append(content).append("\n");
                found = true;
            }
        }
        if (!found) System.out.println("No messages found for that recipient.");
        return results.toString().trim();
    }

   
    public static boolean deleteMessageByHash(Scanner scanner) {
        System.out.print("Enter Message Hash to delete: ");
        String targetHash = scanner.nextLine().trim();

        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equals(targetHash)) {
                String removedText = messageBodies.get(i);   
                String removedFlag = messageFlags.get(i);

               
                messageIds.remove(i);
                messageHashes.remove(i);
                recipients.remove(i);
                messageBodies.remove(i);
                messageFlags.remove(i);

              
                if (removedFlag.equalsIgnoreCase("Sent")) {
                    sentMessages.remove(removedText);
                } else if (removedFlag.equalsIgnoreCase("Disregard") || removedFlag.equalsIgnoreCase("Disregarded")) {
                    disregardedMessages.remove(removedText);
                } else {
                    storedMessages.remove(removedText);
                }

                System.out.println("Message: \"" + removedText + "\" successfully deleted.");
                saveMessagesToJSON();
                return true;
            }
        }
        System.out.println("Hash not found.");
        return false;
    }

    
    //   Full report
    private static void displayReport() {
        System.out.println("\n============================================================");
        System.out.println("           ACTIVE SYSTEM WORKFLOW LOG OPERATIONS            ");
        System.out.println("============================================================");
        if (messageIds.isEmpty()) {
            System.out.println("No messages in active lists.");
        } else {
            for (int i = 0; i < messageIds.size(); i++) {
                System.out.println("RECORD INDEX [" + i + "]");
                System.out.println("Message ID:   " + messageIds.get(i));
                System.out.println("Message Hash: " + messageHashes.get(i));
                System.out.println("Recipient:    " + recipients.get(i));
                System.out.println("Flag:         " + messageFlags.get(i));
               
                System.out.println("Text:         " + messageBodies.get(i));
                System.out.println("------------------------------------------------------------");
            }
        }

        // Summary counts
        System.out.println("\nSent: " + sentMessages.size()
                + "  |  Stored: " + storedMessages.size()
                + "  |  Disregarded: " + disregardedMessages.size());
    }
}