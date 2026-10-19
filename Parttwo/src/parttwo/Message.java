/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parttwo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Message {//start of class
    
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<String> messageIdsList = new ArrayList<>();
    HashSet<String> messageIds = new HashSet<>();
    ArrayList<String> msgHash = new ArrayList<>();
    ArrayList<String> recipientNumber = new ArrayList<>();
    ArrayList<String> messageStatus = new ArrayList<>();

    Random random = new Random();
    Scanner input = new Scanner(System.in);

    int numMessageSent = 0;

    public void sendingMessages() {
        System.out.print("How many messages do you want to send? ");
        int numberOfMessages = input.nextInt();
        input.nextLine();

        for (int i = 1; i <= numberOfMessages; i++) {
            System.out.println("\n==============================");
            System.out.println("MESSAGE " + i);
            System.out.println("==============================");

            String messageID = generateMessageID();
            System.out.println("Message ID generated: " + messageID);

            // Recipient Input and Validation
            System.out.print("Enter recipient cell number: ");
            String recipient = input.nextLine();
            System.out.println(checkRecipientCell(recipient));

            // Message Input and Validation 
            String messageText = "";
            while (true) {
                System.out.print("Enter your message (max 250 chars): ");
                messageText = input.nextLine();
                
                if (messageText.length() <= 250 && !messageText.trim().isEmpty()) {
                    System.out.println("Message ready to send.");
                    break;
                } else if (messageText.length() > 250) {
                    int excess = messageText.length() - 250;
                    System.out.println("Message exceeds 250 characters by " + excess + "; please reduce size.");
                } else {
                    System.out.println("Message cannot be empty.");
                }
            }

            // Generate Hash 
            int messageNumber = messages.size() + 1;
            String messageHash = createMessageHash(messageID, messageNumber, messageText);
            
            // Send, Disregard, Store 
            String status = SentMessage(messageID, recipient, messageText, messageHash);

            // Only store if not disregarded
            if (status.equals("Sent") || status.equals("Stored")) {
                msgHash.add(messageHash);
                messages.add(messageText);
                messageIdsList.add(messageID);
                messageIds.add(messageID);
                recipientNumber.add(recipient);
                messageStatus.add(status);
                
                if (status.equals("Sent")) {
                    numMessageSent++;
                }
            }
        }

        // Display details after sending
        printMessages();
        
        // Accumulated total
        System.out.println("\nTotal messages accumulated: " + returnTotalMessages());
    }

    public int returnTotalMessages() {
        return numMessageSent;
    }

    // Added checkMessageID method as requested in the brief
    public boolean checkMessageID(String id) {
        return id.length() <= 10;
    }

    // Message Hash generation
    public String createMessageHash(String messageID, int messageNumber, String messageText) {
        String firstTwoNumbers = messageID.substring(0, 2);
        String cleanMessage = messageText.trim();
        String[] words = cleanMessage.split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        
        String hash = firstTwoNumbers + ":" + messageNumber + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    public String generateMessageID() {
        String messageID;
        do {
            long number = 1000000000L + (long)(random.nextDouble() * 9000000000L);
            messageID = String.valueOf(number);
        } while (messageIds.contains(messageID));
        return messageID;
    }

    // Recipient cell check
    public String checkRecipientCell(String cellphone) {
        if (cellphone.matches("^\\+27[0-9]{9}$") || cellphone.matches("^0[0-9]{9}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // ==================== NEW: Pure logic method (testable) ====================
    // Takes only the choice and returns the status string.
    // No Scanner, no printing — so unit tests can call it directly.
    public String resolveStatus(int choice) {
        return switch (choice) {
            case 1 -> "Sent";
            case 2 -> "Disregarded";
            case 3 -> "Stored";
            default -> "Disregarded";
        };
    }

    // ==================== Interactive wrapper (same behaviour as before) ====================
    public String SentMessage(String messageID, String recipient, String messageText, String hash) {
        System.out.println("\nChoose what you want to do with this message:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");

        System.out.print("Enter your choice: ");
        int choice = input.nextInt();
        input.nextLine();

        // Use the pure logic method to decide what to do
        String status = resolveStatus(choice);

        switch (status) {
            case "Sent" -> System.out.println("Message successfully sent");
            case "Disregarded" -> {
                System.out.println("Press 0 to delete the message");
                int deleteChoice = input.nextInt();
                input.nextLine();
                if (deleteChoice == 0) {
                    System.out.println("Message deleted");
                }
            }
            case "Stored" -> {
                storeMessageToJSON(messageID, recipient, messageText, hash);
                System.out.println("Message successfully stored");
            }
            default -> System.out.println("Invalid option");
        }

        return status;
    }

    // JSON Storage Method
    public void storeMessageToJSON(String messageId, String recipient, String msgText, String hash) {
        try {
            FileWriter fileWriter = new FileWriter("messages.json", true);
            try (PrintWriter printWriter = new PrintWriter(fileWriter)) {
                String jsonString = String.format("""
                                                  {
                                                    "MessageID": "%s",
                                                    "Recipient": "%s",
                                                    "Message": "%s",
                                                    "MessageHash": "%s"
                                                  },
                                                  """,
                        messageId, recipient, msgText.replace("\"", "\\\""), hash
                );
                
                printWriter.print(jsonString);
            }
            
        } catch (IOException e) {
            System.out.println("An error occurred while storing the message to JSON.");
        }
    }

    // Print messages method 
    public void printMessages() {
        System.out.println("\n===== All Messages =====");
        for (int i = 0; i < messages.size(); i++) {
            System.out.println("\n==============================");
            System.out.println("Message ID: " + messageIdsList.get(i));
            System.out.println("Message Hash: " + msgHash.get(i));
            System.out.println("Recipient: " + recipientNumber.get(i));
            System.out.println("Message: " + messages.get(i));
            System.out.println("Status: " + messageStatus.get(i));
        }
    }
}//end of class