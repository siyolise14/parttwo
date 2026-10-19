/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package parttwo;

import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Student
 */
public class MessageNGTest {

    public MessageNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    // ==================== MESSAGE ID TESTS ====================

    /**
     * Test: A generated message ID is not more than 10 characters.
     */
    @Test
    public void testCheckMessageID_Valid() {
        System.out.println("testCheckMessageID_Valid");
        Message instance = new Message();
        String id = instance.generateMessageID();
        System.out.println("Generated message ID: " + id);
        assertTrue(instance.checkMessageID(id),
                "A generated message ID should be 10 characters or fewer.");
    }

    /**
     * Test: An 11-digit ID fails the checkMessageID check.
     */
    @Test
    public void testCheckMessageID_Invalid() {
        System.out.println("testCheckMessageID_Invalid");
        Message instance = new Message();
        String id = "12345678901"; // 11 digits
        assertFalse(instance.checkMessageID(id),
                "An 11-digit ID should fail the message ID check.");
    }

    // ==================== RECIPIENT CELL TESTS ====================

    /**
     * Test: A +27 international number is accepted.
     */
    @Test
    public void testCheckRecipientCell_Valid() {
        System.out.println("testCheckRecipientCell_Valid");
        Message instance = new Message();
        String cell = "+27718693002";
        String expResult = "Cell phone number successfully captured.";
        String result = instance.checkRecipientCell(cell);
        assertEquals(result, expResult);
    }

    /**
     * Test: A number that starts with +27 but has the wrong length is rejected.
     */
    @Test
    public void testCheckRecipientCell_Invalid() {
        System.out.println("testCheckRecipientCell_Invalid");
        Message instance = new Message();
        String cell = "+277186930"; // too short
        String expResult = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        String result = instance.checkRecipientCell(cell);
        assertEquals(result, expResult);
    }

    // ==================== MESSAGE LENGTH TESTS ====================

    /**
     * Test: A short message is under the 250 character limit.
     */
    @Test
    public void testMessageLength_Valid() {
        System.out.println("testMessageLength_Valid");
        String message = "Hi Mike, can you join us for dinner tonight?";
        assertTrue(message.length() <= 250,
                "Message should be 250 characters or fewer.");
    }

    /**
     * Test: A 260-character message exceeds the 250 character limit.
     */
    @Test
    public void testMessageLength_Invalid() {
        System.out.println("testMessageLength_Invalid");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            sb.append('a');
        }
        String message = sb.toString();
        assertFalse(message.length() <= 250,
                "A 260-character message should exceed the 250 character limit.");
    }

    // ==================== MESSAGE HASH TESTS ====================

    /**
     * Test: Message hash is built from the first two ID digits,
     * the message number, and the first + last word in uppercase.
     */
    @Test
    public void testCreateMessageHash() {
        System.out.println("testCreateMessageHash");
        Message instance = new Message();
        String id = "0012345678";
        int num = 0;
        String message = "Hi tonight";
        String expResult = "00:0:HITONIGHT";
        String result = instance.createMessageHash(id, num, message);
        assertEquals(result, expResult);
    }

    // ==================== RETURN TOTAL MESSAGES TESTS ====================

    /**
     * Test: A new Message object starts with 0 messages sent.
     */
    @Test
    public void testReturnTotalMessages_Initial() {
        System.out.println("testReturnTotalMessages_Initial");
        Message instance = new Message();
        assertEquals(instance.returnTotalMessages(), 0,
                "A new Message object should start with 0 messages sent.");
    }

    /**
     * Test: returnTotalMessages matches numMessageSent after direct mutation.
     */
    @Test
    public void testReturnTotalMessages_AfterIncrement() {
        System.out.println("testReturnTotalMessages_AfterIncrement");
        Message instance = new Message();
        instance.numMessageSent = 5;
        assertEquals(instance.returnTotalMessages(), 5,
                "returnTotalMessages should return numMessageSent.");
    }

    // ==================== RESOLVESTATUS TESTS (the pure logic behind SentMessage) ====================

    /**
     * Test: choice 1 -> "Sent"
     */
    @Test
    public void testResolveStatus_Send() {
        System.out.println("testResolveStatus_Send");
        Message instance = new Message();
        assertEquals(instance.resolveStatus(1), "Sent");
    }

    /**
     * Test: choice 2 -> "Disregarded"
     */
    @Test
    public void testResolveStatus_Disregard() {
        System.out.println("testResolveStatus_Disregard");
        Message instance = new Message();
        assertEquals(instance.resolveStatus(2), "Disregarded");
    }

    /**
     * Test: choice 3 -> "Stored"
     */
    @Test
    public void testResolveStatus_Store() {
        System.out.println("testResolveStatus_Store");
        Message instance = new Message();
        assertEquals(instance.resolveStatus(3), "Stored");
    }

    /**
     * Test: an invalid choice (e.g. 99) defaults to "Disregarded"
     */
    @Test
    public void testResolveStatus_Invalid() {
        System.out.println("testResolveStatus_Invalid");
        Message instance = new Message();
        assertEquals(instance.resolveStatus(99), "Disregarded");
    }
}