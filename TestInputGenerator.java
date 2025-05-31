import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.security.SecureRandom;



public class TestInputGenerator {

    public static void main(String[] args) {
        int n = 100; // Full range 1 to nFsec
        SecureRandom rand = new SecureRandom();

        // Step 1: Create a list from 1 to n
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            numbers.add(i);
        }

        // Step 2: Randomly remove one number
        int missingIndex = rand.nextInt(n);
        int missingNumber = numbers.remove(missingIndex);

        // Step 3: Shuffle for realism (optional)
        Collections.shuffle(numbers);

        // Step 4: Write to file
        try (FileWriter writer = new FileWriter("input.txt")) {
            writer.write(n + "\n"); // First line is 'n'
            for (int num : numbers) {
                writer.write(num + " ");
            }
            System.out.println("Generated input.txt with missing number: " + missingNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
