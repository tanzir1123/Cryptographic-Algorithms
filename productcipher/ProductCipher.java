package productcipher;
import java.util.Scanner;

public class ProductCipher {
    // Affine Key values of a and b
    static int a = 17;
    static int b = 20;
    // Columnar Transposition table column
    static int tableCol = 4;
    static int tableRow;
    // input scanner
    static Scanner input = new Scanner(System.in);

    static void encrypt(String plainTxt){
        // Affine Cipher encryption: 
        plainTxt = plainTxt.toUpperCase();
        String affineCipher = "";
        System.out.println("Applying Affine Cipher.......\n\n");
        for (int i = 0; i < plainTxt.length(); i++) {
            affineCipher = affineCipher + (char) ((((a * (plainTxt.charAt(i) - 'A')) + b) % 26) + 'A');
        }
        System.out.println("Affine Cipher Text: " + affineCipher);

        System.out.println("Applying Columnar Transposition Cipher.......\n\n");
        // Columnar Transposition Cipher encryption:

        int[] key = new int[4];
        System.out.println("Enter 4 digit key with spaces for Columnar Cipher: ");
        for(int i=0; i<4; i++){
            key[i] = input.nextInt();
        }

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
        System.out.println("Table: ");
        for(int i=0; i<tableRow; i++){
            for(int j=0; j<tableCol; j++){
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
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
        System.out.println("Shuffled Table: ");
        for(int i=0; i<tableRow; i++){
            for(int j=0; j<tableCol; j++){
                System.out.print(shuffledTable[i][j] + " ");
            }
            System.out.println();
        }

        // print cipher text from Shuffled table Column by Column.
        String cipher = "";
        for(int i=0; i<tableCol; i++){
            for(int j=0; j<tableRow; j++){
                cipher += shuffledTable[j][i];
            }
        }

        System.out.println("Cipher Text: " + cipher);
        

    }

    static void decrypt(String cipher){
        
        // Columnar Transposition Cipher decryption:
        System.out.println("Applying Columnar Transposition Cipher....... \n\n");
        

        int[] key = new int[4];
        System.out.println("Enter 4 digit key with spaces: ");
        for(int i=0; i<4; i++){
            key[i] = input.nextInt();
        }

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
        System.out.println("Shuffled Table: ");
        for(int i=0; i<tableRow; i++){
            for(int j=0; j<tableCol; j++){
                System.out.print(shuffledTable[i][j] + " ");
            }
            System.out.println();
        }

        // print plain text from shuffled table row by row.
        String plainTxt = "";
        for(int i=0; i<tableRow; i++){
            for(int j=0; j<tableCol; j++){
                plainTxt += shuffledTable[i][j];
            }
        }

        // remove the padding.
        int padding = 0;
        for(int i=plainTxt.length()-1; i>=0; i--){
            if(plainTxt.charAt(i) == 'X')
                padding++;
            else
                break;
        }
        plainTxt = plainTxt.substring(0, plainTxt.length()-padding);

        System.out.println("Columnar Decrypted Text: " + plainTxt);
        String affineCipher = plainTxt;

        // Affine Cipher decryption:
        System.out.println("Applying Affine Cipher Decryption.......\n\n");
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

        System.out.println("Final Plain Text: " + msg);

    }

    private static void operationChoice(String text) {
        System.out.println("Press 1: Encrypt");
        System.out.println("Press 2: Decrypt");
        short sc = input.nextShort();
        
        // avoid spaces
        String newText = "";
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != ' ') {
                newText += text.charAt(i);
            }
        }
        text = newText;

        if (sc == 1) {
            encrypt(text);
        } else if (sc == 2) {
            decrypt(text);
        } else {
            System.out.println("Try again. Press 1 or 2.");
        }
    }
    public static void main(String[] args) {
        System.out.println("--------------PRODUCT CIPHER (AFFINE + COLUMNAR TRANSPOSITION)--------------\n\n");
        System.out.println("Enter the text: ");
        String text = input.nextLine();
        
        operationChoice(text);
    }

}
