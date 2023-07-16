package columnartransposition;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ColumnarAllTestCases {
    // input scanner
    static Scanner input = new Scanner(System.in);
    static int tableCol = 4;
    static int tableRow;

    static String encrypt(String plainTxt){
        
        int[] key = {2, 1, 3, 4};
        // System.out.println("Enter 4 digit key with spaces: ");
        // for(int i=0; i<4; i++){
        //     key[i] = input.nextInt();
        // }

        plainTxt = plainTxt.toUpperCase();
        tableRow = plainTxt.length()/tableCol;
        if(plainTxt.length() % tableCol != 0)
            tableRow++;

        int totalSlots = tableRow * tableCol;
        // padding
        int padding = totalSlots - plainTxt.length();
        for(int i=0; i<padding; i++){
            plainTxt += "X";
        }

        char[][] table = new char[tableRow][tableCol];
        int k=0;
        for(int i=0; i<tableRow; i++){
            for(int j=0; j<tableCol; j++){
                table[i][j] = plainTxt.charAt(k);
                k++;
            }
        }
        
        // shuffle the columns according to the key.
        char[][] shuffledTable = new char[tableRow][tableCol];
        for(int i=0; i<4; i++){
            int col = key[i]-1;
            for(int j=0; j<tableRow; j++){
                shuffledTable[j][i] = table[j][col];
            }
        }

        // print shuffled table. 
        //System.out.println("Shuffled Table After 1st Round: ");
        for(int i=0; i<tableRow; i++){
            for(int j=0; j<tableCol; j++){
                System.out.print(shuffledTable[i][j] + " ");
            }
            //System.out.println();
        }

        // print cipher text from Shuffled table Column by Column.
        String cipher = "";
        for(int i=0; i<tableCol; i++){
            for(int j=0; j<tableRow; j++){
                cipher += shuffledTable[j][i];
            }
        }

        //System.out.println("Cipher Text after 1st Round: " + cipher);

        String finalCipher = secondTurnEncryption(cipher, key);
        //System.out.println("Cipher Text after 2nd Round: " + finalCipher);
        return finalCipher;

    }

    private static String secondTurnEncryption(String cipher, int[] key) {
        
        char[][] table = new char[tableRow][tableCol];
        int k=0;
        for(int i=0; i<tableRow; i++){
            for(int j=0; j<tableCol; j++){
                table[i][j] = cipher.charAt(k);
                k++;
            }
        }
        // shuffle the columns according to the key.
        char[][] shuffledTable = new char[tableRow][tableCol];
        for(int i=0; i<4; i++){
            int col = key[i]-1;
            for(int j=0; j<tableRow; j++){
                shuffledTable[j][i] = table[j][col];
            }
        }

        // print shuffled table. 
        //System.out.println("Shuffled Table After 2nd Turn: ");
        for(int i=0; i<tableRow; i++){
            for(int j=0; j<tableCol; j++){
                System.out.print(shuffledTable[i][j] + " ");
            }
            //System.out.println();
        }

        // print cipher text from Shuffled table Column by Column.
        String secondCipher = "";
        for(int i=0; i<tableCol; i++){
            for(int j=0; j<tableRow; j++){
                secondCipher += shuffledTable[j][i];
            }
        }
        
        return secondCipher;
    }

    static void decrypt(String cipher){
        int[] key = {2, 1, 3, 4};
        // int[] key = new int[4];
        // System.out.println("Enter 4 digit key with spaces: ");
        // for(int i=0; i<4; i++){
        //     key[i] = input.nextInt();
        // }

        cipher = cipher.toUpperCase();
        tableRow = cipher.length()/tableCol;

        // read the cipher text column by column.
        char[][] table = new char[tableRow][tableCol];
        int k=0;
        for(int i=0; i<tableCol; i++){
            for(int j=0; j<tableRow; j++){
                table[j][i] = cipher.charAt(k);
                k++;
            }
        }

        // shuffle the table following the key.
        char[][] shuffledTable = new char[tableRow][tableCol];
        for(int i=0; i<4; i++){
            int col = key[i]-1;
            for(int j=0; j<tableRow; j++){
                shuffledTable[j][col] = table[j][i];
            }
        }

        // print shuffled table.
        //System.out.println("Shuffled Table after 1st Round: ");
        for(int i=0; i<tableRow; i++){
            for(int j=0; j<tableCol; j++){
                System.out.print(shuffledTable[i][j] + " ");
            }
            //System.out.println();
        }

        // print plain text from shuffled table row by row.
        String plainTxt = "";
        for(int i=0; i<tableRow; i++){
            for(int j=0; j<tableCol; j++){
                plainTxt += shuffledTable[i][j];
            }
        }

        plainTxt = secondTurnDecryption(plainTxt, key);

        // remove the padding.
        int padding = 0;
        for(int i=plainTxt.length()-1; i>=0; i--){
            if(plainTxt.charAt(i) == 'X')
                padding++;
            else
                break;
        }
        plainTxt = plainTxt.substring(0, plainTxt.length()-padding);

        //System.out.println("Plain Text: " + plainTxt);
    }

    private static String secondTurnDecryption(String midCipher, int[] key) {
        // read the cipher text column by column.
        char[][] table = new char[tableRow][tableCol];
        int k=0;
        for(int i=0; i<tableCol; i++){
            for(int j=0; j<tableRow; j++){
                table[j][i] = midCipher.charAt(k);
                k++;
            }
        }

        // shuffle the table following the key.
        char[][] shuffledTable = new char[tableRow][tableCol];
        for(int i=0; i<4; i++){
            int col = key[i]-1;
            for(int j=0; j<tableRow; j++){
                shuffledTable[j][col] = table[j][i];
            }
        }

        // print shuffled table.
        //System.out.println("Shuffled Table after 2nd Round: ");
        for(int i=0; i<tableRow; i++){
            for(int j=0; j<tableCol; j++){
                System.out.print(shuffledTable[i][j] + " ");
            }
            //System.out.println();
        }

        // print plain text from shuffled table row by row.
        String plainTxt = "";
        for(int i=0; i<tableRow; i++){
            for(int j=0; j<tableCol; j++){
                plainTxt += shuffledTable[i][j];
            }
        }
        return plainTxt;
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

    static void runAllTestCases(){
        // Read input cases from input.txt
        List<String> plaininputCases = readInputCases("columnartransposition\\plainTextInput.txt");

        long startTime, endTime, elapsedTime;
        ArrayList<Integer> inputSizes = new ArrayList<Integer>();
        ArrayList<Long> encryptionTimes = new ArrayList<Long>();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("columnartransposition\\cipherTextInput.txt"))) {
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

        List<String> cipherinputCases = readInputCases("columnartransposition\\cipherTextInput.txt");
        
        ArrayList<Long> decryptionTimes = new ArrayList<Long>();

        for (String inputCase : cipherinputCases) {
            startTime = System.nanoTime();
            System.out.println("TC Input: " + inputCase + "\n");
            decrypt(inputCase);
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
            decryptionTimes.add(elapsedTime/ 1000);
        }

        System.out.println("--------------Columnar Transposition Cipher ALL TEST CASES--------------\n\n");
        printTable(inputSizes, encryptionTimes, decryptionTimes);

    }
    public static void main(String[] args) {
        System.out.println("--------------Columnar Transposition Cipher--------------");
        System.out.println("Running test cases... \n");

        runAllTestCases();
    }
}
