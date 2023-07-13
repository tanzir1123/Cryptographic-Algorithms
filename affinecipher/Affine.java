package affinecipher;

import java.util.Scanner;

class Affine {
    static Scanner input = new Scanner(System.in);

    // Key values of a and b
    static int a = 17;
    static int b = 20;

    static void encrypt(String plainTxt) {
        /// Cipher Text initially empty
        
        plainTxt = plainTxt.toUpperCase();
        String cipher = "";
        for (int i = 0; i < plainTxt.length(); i++) {
            // Avoid space to be encrypted
            /* applying encryption formula ( a x + b ) mod m
            {here x is msg[i] and m is 26} and added 'A' to
            bring it in range of ascii alphabet[ 65-90 | A-Z ] */
            if (plainTxt.charAt(i) != ' ') {
                cipher = cipher + (char) ((((a * (plainTxt.charAt(i) - 'A')) + b) % 26) + 'A');
            } else { // else simply append space character
                cipher += plainTxt.charAt(i);
            }
        }
        
        //print cipher
        System.out.println("Cipher Text: " + cipher);
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

        // print the plain text
        System.out.println("Plain Text: " + msg);
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
    // Driver code
    public static void main(String[] args) {
        System.out.println("--------------Affine Cipher--------------");
        
        System.out.println("Enter the plain text or cipher text: ");
        String text = input.nextLine();
        operationChoice(text);
    }
}