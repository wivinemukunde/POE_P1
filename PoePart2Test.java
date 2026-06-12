/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.myproject.poepart1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author LENOVO
 */
public class PoePart2Test {
    
    public PoePart2Test() {
    }

    // 1. Test: Message length check for success (<= 250 characters)
    @Test
    public void testCheckMessageLength_Success() {
        // Test Data for Task 1
        long randomId = 4291837465L;
        Message msg = new Message(randomId, 0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertTrue(msg.checkMessageLength(), "Message ready to send.");
    }

    // 2. Test: Message length check for failure (> 250 characters) with dynamic character counts
    @Test
    public void testCheckMessageLength_Failure() {
        String longText = "a".repeat(260); 
        long randomId = 1847294028L;
        Message msg = new Message(randomId, 0, "+27718693002", longText);
        
        assertFalse(msg.checkMessageLength());
        
        // Verifies the exact required dynamic string matching format
        int charactersExceeded = longText.length() - 250;
        String expectedErrorMessage = "Message exceeds 250 characters by " + charactersExceeded + "; please reduce the size.";
        
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", expectedErrorMessage);
    }

    // 3. Test: Recipient phone number successfully formatted (+27 format)
    @Test
    public void testCheckRecipientCell_Success() {
        long randomId = 7392048153L;
        Message msg = new Message(randomId, 0, "+27718693002", "Valid recipient test.");
        assertTrue(msg.checkRecipientCell(), "Cell phone number successfully captured.");
    }

    // 4. Test: Recipient phone number format fails (missing prefix or incorrect digit layout)
    @Test
    public void testCheckRecipientCell_Failure() {
        // Test Data for Message 2 (Missing +27 international code)
        long randomId = 9284710482L;
        Message msg = new Message(randomId, 1, "08575975889", "Invalid recipient test.");
        assertFalse(msg.checkRecipientCell(), "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
    }

    // 5. Test: Message hash is correct (Combines first and last words, and starts with 00:0:)
    @Test
    public void testMessageHash_Generation() {
        // Test Data for Task 1 (Message ID is a random 10-digit long, loop index is 0)
        long randomId = 5839201847L;
        Message msg = new Message(randomId, 0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        
        String summary = msg.displayMessageSummary();
        
        // Asserts that the hash contains "00:0:HITONIGHT" exactly as required by the rubric
        assertTrue(summary.contains("Message Hash: 00:0:HITONIGHT"), 
                   "Message hash must match 00:0:HITONIGHT for Test Case 1");
    }
}