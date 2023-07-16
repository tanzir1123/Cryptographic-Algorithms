package productcipher;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductCipherAllTestCases {
    // Affine Key values of a and b
    static int a = 17;
    static int b = 20;
    // Columnar Transposition table column
    static int tableCol = 4;
    static int tableRow;
    // input scanner
    static Scanner input = new Scanner(System.in);

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
        //System.out.println("Shuffled Table After 2nd Turn: \n\n");
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

    static String encrypt(String plainTxt){
        // Affine Cipher encryption: 
        plainTxt = plainTxt.toUpperCase();
        String affineCipher = "";
        //System.out.println("Applying Affine Cipher.......\n\n");
        for (int i = 0; i < plainTxt.length(); i++) {
            affineCipher = affineCipher + (char) ((((a * (plainTxt.charAt(i) - 'A')) + b) % 26) + 'A');
        }
        //System.out.println("Affine Cipher Text: " + affineCipher + "\n\n");

        //System.out.println("Applying Columnar Transposition Cipher.......\n\n");
        // Columnar Transposition Cipher encryption:

        // int[] key = new int[4];
        // //System.out.println("Enter 4 digit key with spaces for Columnar Cipher: ");
        // for(int i=0; i<4; i++){
        //     key[i] = input.nextInt();
        // }
        
        int[] key = {2, 4, 1, 3};
        
        tableRow = affineCipher.length()/tableCol;
        if(affineCipher.length() % tableCol != 0)
            tableRow++;

        int totalSlots = tableRow * tableCol;
        // padding
        int padding = totalSlots - affineCipher.length();
        for(int i=0; i<padding; i++){
            affineCipher += "X";
        }

        char[][] table = new char[tableRow][tableCol];
        int k=0;
        for(int i=0; i<tableRow; i++){
            for(int j=0; j<tableCol; j++){
                table[i][j] = affineCipher.charAt(k);
                k++;
            }
        }
        //print the table
        //System.out.println("Table: ");
        for(int i=0; i<tableRow; i++){
            for(int j=0; j<tableCol; j++){
                System.out.print(table[i][j] + " ");
            }
            //System.out.println();
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
        //System.out.println("Shuffled Table: ");
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

        //System.out.println("Cipher Text: " + cipher);
        String finalCipher = secondTurnEncryption(cipher, key);
        //System.out.println("Cipher Text after 2nd Round of Columnar (Final Cipher):  " + finalCipher);
        return finalCipher;
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

    static void decrypt(String cipher){
        
        // Columnar Transposition Cipher decryption:
        //System.out.println("Applying Columnar Transposition Cipher....... \n\n");
        

        // int[] key = new int[4];
        // //System.out.println("Enter 4 digit key with spaces: ");
        // for(int i=0; i<4; i++){
        //     key[i] = input.nextInt();
        // }

        int[] key = {2, 4, 1, 3};

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
        //System.out.println("Shuffled Table: ");
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

        //System.out.println("Columnar Decrypted Text: " + plainTxt + "\n\n");
        String affineCipher = plainTxt;

        // Affine Cipher decryption:
        //System.out.println("Applying Affine Cipher Decryption.......\n\n");
        String msg = "";
        int a_inv = 0;
        int flag = 0;

        // Find a^-1 (the multiplicative inverse of a
        // in the group of integers modulo m.)
        for (int i = 0; i < 26; i++) {
            flag = (a * i) % 26;

            if (flag == 1) {
                a_inv = i;
            }
        }

        for (int i = 0; i < affineCipher.length(); i++)
            msg = msg + (char) (((a_inv * ((affineCipher.charAt(i) + 'A' - b)) % 26)) + 'A');

        //System.out.println("Final Plain Text: " + msg + "\n\n");

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
        List<String> plaininputCases = readInputCases("productcipher\\plainTextInput.txt");

        long startTime, endTime, elapsedTime;
        ArrayList<Integer> inputSizes = new ArrayList<Integer>();
        ArrayList<Long> encryptionTimes = new ArrayList<Long>();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("productcipher\\cipherTextInput.txt"))) {
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

        List<String> cipherinputCases = readInputCases("productcipher\\cipherTextInput.txt");
        
        ArrayList<Long> decryptionTimes = new ArrayList<Long>();

        for (String inputCase : cipherinputCases) {
            startTime = System.nanoTime();
            System.out.println("TC Input: " + inputCase + "\n");
            decrypt(inputCase);
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
            decryptionTimes.add(elapsedTime/ 1000);
        }

        System.out.println("--------------Product Cipher ALL TEST CASES--------------\n\n");
        printTable(inputSizes, encryptionTimes, decryptionTimes);

    }
    
    public static void main(String[] args) {
        System.out.println("--------------PRODUCT CIPHER (AFFINE + COLUMNAR TRANSPOSITION)--------------\n\n");
        System.out.println("Running test cases... \n");

        runAllTestCases();
    }

}
