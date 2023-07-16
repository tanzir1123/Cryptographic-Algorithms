package affinecipher;

import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class AffineAllTestCases {
    static Scanner input = new Scanner(System.in);

    // Affine Key values
    static int a = 17;
    static int b = 20;

    static String encrypt(String plainTxt) {
     
        plainTxt = plainTxt.toUpperCase();
        String cipher = "";
        for (int i = 0; i < plainTxt.length(); i++) {
            
            /* applying encryption formula ( a x + b ) mod m
            {here x is msg[i] and m is 26} and added 'A' to
            bring it in range of ascii alphabet[ 65-90 | A-Z ] */
            if (plainTxt.charAt(i) != ' ') {
                cipher = cipher + (char) ((((a * (plainTxt.charAt(i) - 'A')) + b) % 26) + 'A');
            } else { // else simply append space character
                cipher += plainTxt.charAt(i);
            }
        }
        

        return cipher;
    }

    static void decrypt(String cipher) {
        String msg = "";
        int a_inv = 0;
        int flag = 0;

        // Find a^-1 (the multiplicative inverse of a
        // in the group of integers modulo m.)
        for (int i = 0; i < 26; i++) {
            flag = (a * i) % 26;
            // Check if (a*i)%26 == 1,
            // then i will be the multiplicative inverse of a
            if (flag == 1) {
                a_inv = i;
            }
        }
        for (int i = 0; i < cipher.length(); i++) {
            /*Applying decryption formula a^-1 ( x - b ) mod m
            {here x is cipher[i] and m is 26} and added 'A'
            to bring it in range of ASCII alphabet[ 65-90 | A-Z ] */
            if (cipher.charAt(i) != ' ') {
                msg = msg + (char) (((a_inv * ((cipher.charAt(i) + 'A' - b)) % 26)) + 'A');
            } else { // else simply append space character
                msg += cipher.charAt(i);
            }
        }

    }

    
    static List<String> readInputCases(String filePath) {
        List<String> inputCases = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                inputCases.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return inputCases;
    }

    static void printTable(List<Integer> inputSizes, List<Long> encryptionTimes, List<Long> decryptionTimes) {
        // ANSI escape codes for colors
        String reset = "\u001B[0m";
        String cyan = "\u001B[36m";
        String yellow = "\u001B[33m";
    
        // Print the table header with colors
        System.out.print(cyan + "Input Size (N)\t\t" + yellow + "Encryption Time (us)\t" + yellow + "Decryption Time (us)" + reset);
        System.out.println();
    
        // Print the table rows with colors
        for (int i = 0; i < inputSizes.size(); i++) {
            int inputSize = inputSizes.get(i);
            long encryptionTime = encryptionTimes.get(i);
            long decryptionTime = decryptionTimes.get(i);
            System.out.printf("%-25d %-25d %-20d%n", inputSize, encryptionTime, decryptionTime);
        }
    }
    
    

    static void runAllTestCases(){
        // Read input cases from input.txt
        List<String> plaininputCases = readInputCases("affinecipher\\plainTextInput.txt");

        long startTime, endTime, elapsedTime;
        ArrayList<Integer> inputSizes = new ArrayList<Integer>();
        ArrayList<Long> encryptionTimes = new ArrayList<Long>();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("affinecipher\\cipherTextInput.txt"))) {
            for (String inputCase : plaininputCases) {
                startTime = System.nanoTime();
                System.out.println("TC Input: " + inputCase + "\n");
                String ciphertext = encrypt(inputCase);
                writer.write(ciphertext);
                writer.newLine();

                inputSizes.add(inputCase.length());
                endTime = System.nanoTime();
                elapsedTime = endTime - startTime;
                encryptionTimes.add(elapsedTime / 1000); // Convert nanoseconds to microseconds
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> cipherinputCases = readInputCases("affinecipher\\cipherTextInput.txt");
        
        ArrayList<Long> decryptionTimes = new ArrayList<Long>();

        for (String inputCase : cipherinputCases) {
            startTime = System.nanoTime();
            System.out.println("TC Input: " + inputCase + "\n");
            decrypt(inputCase);
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
            decryptionTimes.add(elapsedTime/ 1000);
        }

        System.out.println("--------------Affine Cipher ALL TEST CASES--------------\n\n");
        printTable(inputSizes, encryptionTimes, decryptionTimes);

    }
    public static void main(String[] args) {
        System.out.println("--------------Affine Cipher--------------\n\n");
        System.out.println("Running test cases... \n");

        runAllTestCases();
    }
}