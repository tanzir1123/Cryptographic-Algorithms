package hillcipher;
import java.util.ArrayList;
import java.util.Scanner;

public class HillCipher {

    static Scanner input = new Scanner(System.in);

    public static long[][] makeAdjMat(int[][] keyMat) {
        long[][] adjMat = new long[5][5];
        adjMat[1][1] = (keyMat[2][2] * keyMat[3][3] * keyMat[4][4])
                + (keyMat[2][3] * keyMat[3][4] * keyMat[4][2])
                + (keyMat[2][4] * keyMat[3][2] * keyMat[4][3]) - (keyMat[2][4] * keyMat[3][3] * keyMat[4][2])
                - (keyMat[2][3] * keyMat[3][2] * keyMat[4][4]) - (keyMat[2][2] * keyMat[3][4] * keyMat[4][3]);

        adjMat[1][2] = -(keyMat[1][2] * keyMat[3][3] * keyMat[4][4])
                - (keyMat[1][3] * keyMat[3][4] * keyMat[4][2])
                - (keyMat[1][4] * keyMat[3][2] * keyMat[4][3]) + (keyMat[1][4] * keyMat[3][3] * keyMat[4][2])
                + (keyMat[1][3] * keyMat[3][2] * keyMat[4][4]) + (keyMat[1][2] * keyMat[3][4] * keyMat[4][3]);

        adjMat[1][3] = (keyMat[1][2] * keyMat[2][3] * keyMat[4][4])
                + (keyMat[1][3] * keyMat[2][4] * keyMat[4][2])
                + (keyMat[1][4] * keyMat[2][2] * keyMat[4][3]) - (keyMat[1][4] * keyMat[2][3] * keyMat[4][2])
                - (keyMat[1][3] * keyMat[2][2] * keyMat[4][4]) - (keyMat[1][2] * keyMat[2][4] * keyMat[4][3]);

        adjMat[1][4] = -(keyMat[1][2] * keyMat[2][3] * keyMat[3][4])
                - (keyMat[1][3] * keyMat[2][4] * keyMat[3][2])
                - (keyMat[1][4] * keyMat[2][2] * keyMat[3][3]) + (keyMat[1][4] * keyMat[2][3] * keyMat[3][2])
                + (keyMat[1][3] * keyMat[2][2] * keyMat[3][4]) + (keyMat[1][2] * keyMat[2][4] * keyMat[3][3]);

        adjMat[2][1] = -(keyMat[2][1] * keyMat[3][3] * keyMat[4][4])
                - (keyMat[2][3] * keyMat[3][4] * keyMat[4][1])
                - (keyMat[2][4] * keyMat[3][1] * keyMat[4][3]) + (keyMat[2][4] * keyMat[3][3] * keyMat[4][1])
                + (keyMat[2][3] * keyMat[3][1] * keyMat[4][4]) + (keyMat[2][1] * keyMat[3][4] * keyMat[4][3]);

        adjMat[2][2] = (keyMat[1][1] * keyMat[3][3] * keyMat[4][4])
                + (keyMat[1][3] * keyMat[3][4] * keyMat[4][1])
                + (keyMat[1][4] * keyMat[3][1] * keyMat[4][3]) - (keyMat[1][4] * keyMat[3][3] * keyMat[4][1])
                - (keyMat[1][3] * keyMat[3][1] * keyMat[4][4]) - (keyMat[1][1] * keyMat[3][4] * keyMat[4][3]);

        adjMat[2][3] = -(keyMat[1][1] * keyMat[2][3] * keyMat[4][4])
                - (keyMat[1][3] * keyMat[2][4] * keyMat[4][1])
                - (keyMat[1][4] * keyMat[2][1] * keyMat[4][3]) + (keyMat[1][4] * keyMat[2][3] * keyMat[4][1])
                + (keyMat[1][3] * keyMat[2][1] * keyMat[4][4]) + (keyMat[1][1] * keyMat[2][4] * keyMat[4][3]);

        adjMat[2][4] = (keyMat[1][1] * keyMat[2][3] * keyMat[3][4])
                + (keyMat[1][3] * keyMat[2][4] * keyMat[3][1])
                + (keyMat[1][4] * keyMat[2][1] * keyMat[3][3]) - (keyMat[1][4] * keyMat[2][3] * keyMat[3][1])
                - (keyMat[1][3] * keyMat[2][1] * keyMat[3][4]) - (keyMat[1][1] * keyMat[2][4] * keyMat[3][3]);

        adjMat[3][1] = (keyMat[2][1] * keyMat[3][2] * keyMat[4][4])
                + (keyMat[2][2] * keyMat[3][4] * keyMat[4][1])
                + (keyMat[2][4] * keyMat[3][1] * keyMat[4][2]) - (keyMat[2][4] * keyMat[3][2] * keyMat[4][1])
                - (keyMat[2][2] * keyMat[3][1] * keyMat[4][4]) - (keyMat[2][1] * keyMat[3][4] * keyMat[4][2]);

        adjMat[3][2] = -(keyMat[1][1] * keyMat[3][2] * keyMat[4][4])
                - (keyMat[1][2] * keyMat[3][4] * keyMat[4][1])
                - (keyMat[1][4] * keyMat[3][1] * keyMat[4][2]) + (keyMat[1][4] * keyMat[3][2] * keyMat[4][1])
                + (keyMat[1][2] * keyMat[3][1] * keyMat[4][4]) + (keyMat[1][1] * keyMat[3][4] * keyMat[4][2]);

        adjMat[3][3] = (keyMat[1][1] * keyMat[2][2] * keyMat[4][4])
                + (keyMat[1][2] * keyMat[2][4] * keyMat[4][1])
                + (keyMat[1][4] * keyMat[2][1] * keyMat[4][2]) - (keyMat[1][4] * keyMat[2][2] * keyMat[4][1])
                - (keyMat[1][2] * keyMat[2][1] * keyMat[4][4]) - (keyMat[1][1] * keyMat[2][4] * keyMat[4][2]);

        adjMat[3][4] = -(keyMat[1][1] * keyMat[2][2] * keyMat[3][4])
                - (keyMat[1][2] * keyMat[2][4] * keyMat[3][1])
                - (keyMat[1][4] * keyMat[2][1] * keyMat[3][2]) + (keyMat[1][4] * keyMat[2][2] * keyMat[3][1])
                + (keyMat[1][2] * keyMat[2][1] * keyMat[3][4]) + (keyMat[1][1] * keyMat[2][4] * keyMat[3][2]);

        adjMat[4][1] = (keyMat[2][3] * keyMat[3][2] * keyMat[4][1])
                + (keyMat[2][2] * keyMat[3][1] * keyMat[4][3])
                + (keyMat[2][1] * keyMat[3][3] * keyMat[4][2]) - (keyMat[2][1] * keyMat[3][2] * keyMat[4][3])
                - (keyMat[2][2] * keyMat[3][3] * keyMat[4][1]) - (keyMat[2][3] * keyMat[3][1] * keyMat[4][2]);
        adjMat[4][2] = (keyMat[1][1] * keyMat[3][2] * keyMat[4][3])
                + (keyMat[1][2] * keyMat[3][3] * keyMat[4][1])
                + (keyMat[1][3] * keyMat[3][1] * keyMat[4][2]) - (keyMat[1][3] * keyMat[3][2] * keyMat[4][1])
                - (keyMat[1][2] * keyMat[3][1] * keyMat[4][3]) - (keyMat[1][1] * keyMat[3][3] * keyMat[4][2]);
        adjMat[4][3] = (keyMat[1][3] * keyMat[2][2] * keyMat[4][1])
                + (keyMat[1][2] * keyMat[2][1] * keyMat[4][3])
                + (keyMat[1][1] * keyMat[2][3] * keyMat[4][2]) - (keyMat[1][1] * keyMat[2][2] * keyMat[4][3])
                - (keyMat[1][2] * keyMat[2][3] * keyMat[4][1]) - (keyMat[1][3] * keyMat[2][1] * keyMat[4][2]);
        adjMat[4][4] = (keyMat[1][1] * keyMat[2][2] * keyMat[3][3])
                + (keyMat[1][2] * keyMat[2][3] * keyMat[3][1])
                + (keyMat[1][3] * keyMat[2][1] * keyMat[3][2]) - (keyMat[1][3] * keyMat[2][2] * keyMat[3][1])
                - (keyMat[1][2] * keyMat[2][1] * keyMat[3][3]) - (keyMat[1][1] * keyMat[2][3] * keyMat[3][2]);

        return adjMat;
    }
    static int modInverse(int A, int M)
    {
 
        for (int X = 1; X < M; X++)
            if (((A % M) * (X % M)) % M == 1)
                return X;
        return 1;
    }

    private static void decrypt(String cipherText) {
        System.out.println("-----------Decryption-----------");
        // Get the key matrix from user.
        System.out.println("Enter a 4x4 Matrix as key: ");
        int[][] keyMat = new int[5][5];
        for (int i = 1; i < 5; i++){
            for (int j = 1; j < 5; j++) {
                keyMat[i][j] = input.nextInt();
            }
        }

        long detValue = detMat(keyMat);
        if (detValue%26 == 0) {
            System.out.println("You must have inserted wrong Key. Try again.");
            return;
        }

        // Find the Adjoint Matrix.
        long[][] adjMat = makeAdjMat(keyMat);

        int x;
        long each_sum;

        ArrayList<Integer> plainText = new ArrayList<>();
        

        for (int i = 0; i < cipherText.length(); i += 4) {
            for (x = 1; x < 5; x++) {
                each_sum = ((((cipherText.charAt(i) - 'A')) * (adjMat[1][x])
                + ((cipherText.charAt(i + 1) - 'A')) * (adjMat[2][x])
                + ((cipherText.charAt(i + 2) - 'A')) * (adjMat[3][x])
                + ((cipherText.charAt(i + 3) - 'A')) * (adjMat[4][x]))) / detValue;

                each_sum = ((26*200000) + each_sum) % 26;
                plainText.add((int) (each_sum));
            }    
        }


        System.out.println("Plain Text: ");
        for (int i = 0; i < plainText.size(); i++) {
            System.out.print((char) (plainText.get(i) + 'A'));
        }

    }



    private static long ensureNonZeroDetMat(int[][] keyMat) {
        long detValue = detMat(keyMat);
        if (detValue%26== 0) {
            return 0;
        }
        return 1;
    }

    private static void encrypt(String plainText) {
        System.out.println("-----------Encryption-----------");
        // Get the key matrix from user.
        System.out.println("Enter a 4x4 Matrix as key: ");
        int[][] keyMat = new int[5][5];
        for (int i = 1; i < 5; i++){
            for (int j = 1; j < 5; j++) {
                keyMat[i][j] = input.nextInt();
            }
        }

        
        plainText = plainText.toUpperCase();

        long isNonZero = ensureNonZeroDetMat(keyMat);
        while(isNonZero == 0){
            System.out.println("The Matrix has no Multiplicative Inverse.\n"
                    + "Please Enter Another 4x4 Matrix as key: ");
            for (int i = 1; i < 5; i++){
                for (int j = 1; j < 5; j++) {
                    keyMat[i][j] = input.nextInt();
                }
            }
            isNonZero = ensureNonZeroDetMat(keyMat);
        }
        
       

        // Add padding if necessary. 
        if (plainText.length() % 4 != 0) {
            int pad = 4 - (plainText.length() % 4);
            for (int i = 0; i < pad; i++) {
                plainText += "X";
            }
        }

        System.out.println("Padded Plain Text: " + plainText);
        ArrayList <Integer> cipherText = new ArrayList<>();
        for(int i = 0; i < plainText.length(); i += 4){
            int eachSum = 0;
            for (int x = 1; x < 5; x++){
                eachSum = (((plainText.charAt(i)-'A') * keyMat[1][x])
                            + ((plainText.charAt(i+1)-'A') * keyMat[2][x])
                            + ((plainText.charAt(i+2)-'A') * keyMat[3][x])
                            + ((plainText.charAt(i+3)-'A') * keyMat[4][x])) ; 
                eachSum %= 26;
                cipherText.add(eachSum % 26);
                System.out.println("value of eachSum: " + eachSum);
            }
        }

        System.out.println("Cipher Text: ");
        for (int i = 0; i < cipherText.size(); i++){
            System.out.print((char)(cipherText.get(i) + 'A'));
        }

    }

    public static long detMat(int[][] mat){ 

        long det_val1 = mat[1][1] * mat[2][2] * mat[3][3] * mat[4][4] + 
                mat[1][1] * mat[2][3] * mat[3][4] * mat[4][2] + 
                mat[1][1] * mat[2][4] * mat[3][2] * mat[4][3] - 
                mat[1][1] * mat[2][4] * mat[3][3] * mat[4][2] - 
                mat[1][1] * mat[2][3] * mat[3][2] * mat[4][4] - 
                mat[1][1] * mat[2][2] * mat[3][4] * mat[4][3] - 
                mat[1][2] * mat[2][1] * mat[3][3] * mat[4][4] - 
                mat[1][3] * mat[2][1] * mat[3][4] * mat[4][2] - 
                mat[1][4] * mat[2][1] * mat[3][2] * mat[4][3] + 
                mat[1][4] * mat[2][1] * mat[3][3] * mat[4][2] + 
                mat[1][3] * mat[2][1] * mat[3][2] * mat[4][4] + 
                mat[1][2] * mat[2][1] * mat[3][4] * mat[4][3] + 
                mat[1][2] * mat[2][3] * mat[3][1] * mat[4][4] + 
                mat[1][3] * mat[2][4] * mat[3][1] * mat[4][2] + 
                mat[1][4] * mat[2][2] * mat[3][1] * mat[4][3] - 
                mat[1][4] * mat[2][3] * mat[3][1] * mat[4][2] - 
                mat[1][3] * mat[2][2] * mat[3][1] * mat[4][4] - 
                mat[1][2] * mat[2][4] * mat[3][1] * mat[4][3] - 
                mat[1][2] * mat[2][3] * mat[3][4] * mat[4][1] - 
                mat[1][3] * mat[2][4] * mat[3][2] * mat[4][1] - 
                mat[1][4] * mat[2][2] * mat[3][3] * mat[4][1] + 
                mat[1][4] * mat[2][3] * mat[3][2] * mat[4][1] +  
                mat[1][3] * mat[2][2] * mat[3][4] * mat[4][1] + 
                mat[1][2] * mat[2][4] * mat[3][3] * mat[4][1]; 
        return det_val1;
    }
    
    public static void operationChoice(String text){
        System.out.println("Press 1: Encrypt");
        System.out.println("Press 2: Decrypt");
        short sc = input.nextShort();
        if (sc == 1) {
            encrypt(text);
        } else if (sc == 2) {
            decrypt(text);
        } else {
            System.out.println("Try again. Press 1 or 2.");
        }
    }
    
    public static void main(String[] args){
        System.out.println("-----------Hill Cipher-----------");

        System.out.println("Enter the plain text or cipher text: ");
        String text = input.nextLine();
        operationChoice(text);
        
    }
}
