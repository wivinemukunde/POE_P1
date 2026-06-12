/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.myproject.poepart1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author LENOVO
 */

public class PoePart3Test {

    // Load all 5 test messages fresh before every test
    @BeforeEach
    public void setUp() {
        PoePart3.loadTestData();
    }

    // ------------------------------------------------------------------
    // Test 1 — Sent Messages array correctly populated
    // Test Data : Messages 1 and 4
    // Expected  : sentMessages contains both bodies, size == 2
    // ------------------------------------------------------------------
    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        assertTrue(
            PoePart3.sentMessages.contains("Did you get the cake?"),
            "sentMessages must contain Message 1"
        );
        assertTrue(
            PoePart3.sentMessages.contains("It is dinner time!"),
            "sentMessages must contain Message 4"
        );
        assertEquals(2, PoePart3.sentMessages.size(),
            "Only 2 messages are flagged Sent"
        );
    }

    // ------------------------------------------------------------------
    // Test 2 — Display the longest stored message
    // Test Data : storedMessages = Message 2 + Message 5
    // Expected  : "Where are you? You are late! I have asked you to be on time."
    // ------------------------------------------------------------------
    @Test
    public void testDisplayLongestMessage() {
        String result = PoePart3.displayLongestMessage();
        assertEquals(
            "Where are you? You are late! I have asked you to be on time.",
            result,
            "Longest stored message should be Message 2"
        );
    }

    @Test
    public void testSearchByMessageId() {
        int index = PoePart3.messageIds.indexOf("0838884567");
        assertNotEquals(-1, index, "Message ID '0838884567' must exist");
        assertEquals(
            "It is dinner time!",
            PoePart3.messageBodies.get(index),
            "Body for ID '0838884567' must be 'It is dinner time!'"
        );
    }


    public void testSearchByRecipient() {
  
        String target = "+27838884567";
        StringBuilder results = new StringBuilder();

        for (int i = 0; i < PoePart3.recipients.size(); i++) {
            if (PoePart3.recipients.get(i).equals(target)) {
                results.append(PoePart3.messageBodies.get(i)).append("\n");
            }
        }

        String result = results.toString();
        assertTrue(
            result.contains("Where are you? You are late! I have asked you to be on time."),
            "Result must include Message 2 body"
        );
        assertTrue(
            result.contains("Ok, I am leaving without you."),
            "Result must include Message 5 body"
        );
    }

    // Test 5 — Delete a message using its message hash
  
    public void testDeleteMessageByHash() {
        String targetHash = "HASH002";
        String expectedBody =
            "Where are you? You are late! I have asked you to be on time.";

        // Confirm it exists before deleting 
        assertTrue(PoePart3.messageHashes.contains(targetHash),
            "HASH002 must exist before deletion");
        assertTrue(PoePart3.storedMessages.contains(expectedBody),
            "Message 2 body must be in storedMessages before deletion");

        // Delete directly — no Scanner needed
        int index = PoePart3.messageHashes.indexOf(targetHash);
        String removedText = PoePart3.messageBodies.get(index);
        String removedFlag = PoePart3.messageFlags.get(index);

        PoePart3.messageIds.remove(index);
        PoePart3.messageHashes.remove(index);
        PoePart3.recipients.remove(index);
        PoePart3.messageBodies.remove(index);
        PoePart3.messageFlags.remove(index);

        if (removedFlag.equalsIgnoreCase("Stored")) {
            PoePart3.storedMessages.remove(removedText);
        }

        // Verify delet 
        assertEquals(expectedBody, removedText,
            "Removed text must match Message 2 body");
        assertFalse(PoePart3.messageHashes.contains(targetHash),
            "HASH002 must no longer exist after deletion");
        assertFalse(PoePart3.storedMessages.contains(expectedBody),
            "Message 2 body must no longer be in storedMessages");
    }

  
    // Test 6 — Display report: all sent messages have hash, recipient, body
  
    @Test
    public void testDisplayReportSentMessagesComplete() {
        int sentCount = 0;
        for (int i = 0; i < PoePart3.messageIds.size(); i++) {
            if (PoePart3.messageFlags.get(i).equalsIgnoreCase("Sent")) {
                assertNotNull(PoePart3.messageHashes.get(i),
                    "Hash must not be null for sent message at index " + i);
                assertFalse(PoePart3.messageHashes.get(i).isEmpty(),
                    "Hash must not be empty for sent message at index " + i);
                assertNotNull(PoePart3.recipients.get(i),
                    "Recipient must not be null for sent message at index " + i);
                assertFalse(PoePart3.recipients.get(i).isEmpty(),
                    "Recipient must not be empty for sent message at index " + i);
                assertNotNull(PoePart3.messageBodies.get(i),
                    "Body must not be null for sent message at index " + i);
                assertFalse(PoePart3.messageBodies.get(i).isEmpty(),
                    "Body must not be empty for sent message at index " + i);
                sentCount++;
            }
        }
        assertEquals(2, sentCount,
            "Report must reflect exactly 2 sent messages");
    }
}  

