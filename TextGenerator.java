import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class TextGenerator {
    public static void main(String[] args) {
        int[] inputSizes = { 5, 100, 1000, 5000 };
        String fileName = "plainTextInput.txt";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

            for (int size : inputSizes) {
                String text = generateRandomText(size);
                writer.write(text);
                writer.newLine();
            }

            writer.close();
            System.out.println("Texts have been written to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateRandomText(int size) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {
            char randomChar = (char) ('A' + random.nextInt(26));
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
