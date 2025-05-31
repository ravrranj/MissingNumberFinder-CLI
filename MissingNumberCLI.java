import java.io.*;
import java.util.*;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;


@Command(name = "MissingNumberCLI", mixinStandardHelpOptions = true, version = "1.0",
        description = "Finds the missing number from a file of numbers.")
public class MissingNumberCLI implements Callable<Integer> {

    @Option(names = {"--file"}, description = "Input filename")
    private String filename;

    @Option(names = {"--simulate"}, description = "Simulate missing number (format: n:missingNumber)")
    private String simulate;

    @Option(names = {"--method"}, description = "Method to find missing number (sum, xor)", defaultValue = "sum")
    private String method;

    @Option(names = {"--debug"}, description = "Enable debug output")
    private boolean debug;

    @Override
    public Integer call() throws Exception {
        int n;
        int[] nums;

        if (simulate != null) {
            String[] parts = simulate.split(":");
            if (parts.length != 2) {
                System.err.println("Invalid --simulate format. Use --simulate=n:missingNumber");
                return 1;
            }
            n = Integer.parseInt(parts[0]);
            int missingSim = Integer.parseInt(parts[1]);
            nums = generateArrayWithMissing(n, missingSim);
            System.out.println("Simulated n = " + n + ", missing = " + missingSim);

        } else if (filename != null) {
            InputData inputData = readInputFromFile(filename, debug);
            if (inputData == null) return 1;

            n = inputData.n;
            nums = inputData.array;
            System.out.println("Read from file: n = " + n + ", array length = " + nums.length);

        } else {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter the total number of elements (including missing one): ");
            if (!sc.hasNextInt()) {
                System.err.println("Error: Please enter a valid integer.");
                return 1;
            }
            n = sc.nextInt();
            System.out.println("Enter the array elements (space-separated, missing one number):");

            nums = new int[n - 1];
            for (int i = 0; i < n - 1; i++) {
                if (!sc.hasNextInt()) {
                    System.err.println("Error: Invalid integer input.");
                    return 1;
                }
                nums[i] = sc.nextInt();
            }
            sc.close();
        }

        if (!validateArray(nums, n)) return 1;

        int result = method.equalsIgnoreCase("xor") ? findMissingUsingXOR(nums, n) : findMissingUsingSum(nums, n);
        System.out.println("The missing number is: " + result);
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new MissingNumberCLI()).execute(args);
        System.exit(exitCode);
    }

    private static class InputData {
        int n;
        int[] array;

        InputData(int n, int[] array) {
            this.n = n;
            this.array = array;
        }
    }

private static InputData readInputFromFile(String filename, boolean debug) throws IOException {
    File file = new File(filename);
    if (!file.exists()) {
        System.err.println("Error: File '" + filename + "' does not exist.");
        return null;
    }

    try (Scanner sc = new Scanner(file)) {
        if (!sc.hasNextInt()) {
            System.err.println("Error: First line must be total number of elements.");
            return null;
        }
        int n = sc.nextInt();
        int[] arr = new int[n - 1];
        int count = 0;

        while (count < n - 1) {
            if (!sc.hasNextInt()) {
                System.err.println("Error: File ended prematurely. Expected " + (n - 1) + " elements.");
                return null;
            }
            int val = sc.nextInt();
            if (debug) System.out.println("Read value: " + val);
            arr[count++] = val;
        }
        return new InputData(n, arr);
    }
}

    private static boolean validateArray(int[] nums, int n) {
        if (nums.length != n - 1) {
            System.err.println("Error: Array length mismatch, expected " + (n - 1) + " elements.");
            return false;
        }
        return true;
    }

    private static int findMissingUsingSum(int[] nums, int n) {
        int expectedSum = (n * (n + 1)) / 2;
        return expectedSum - Arrays.stream(nums).sum();
    }

    private static int findMissingUsingXOR(int[] nums, int n) {
        int xorFull = 0, xorArray = 0;
        for (int i = 1; i <= n; i++) xorFull ^= i;
        for (int num : nums) xorArray ^= num;
        return xorFull ^ xorArray;
    }

    private static int[] generateArrayWithMissing(int n, int missing) {
        int[] arr = new int[n - 1];
        int idx = 0;
        for (int i = 1; i <= n; i++) {
            if (i == missing) continue;
            arr[idx++] = i;
        }
        return arr;
    }
}